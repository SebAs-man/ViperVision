package com.github.sebasman.presenter.strategies;

import com.github.sebasman.contracts.configuration.CheckBoxConfigParameter;
import com.github.sebasman.contracts.configuration.IConfigParameter;
import com.github.sebasman.contracts.configuration.SliderConfigParameter;
import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.AiPathUpdatedEvent;
import com.github.sebasman.contracts.events.types.ConfigurationChangedEvent;
import com.github.sebasman.contracts.events.types.GameSpeedChangedEvent;
import com.github.sebasman.contracts.model.entities.IFoodAPI;
import com.github.sebasman.contracts.model.entities.ISnakeAPI;
import com.github.sebasman.contracts.view.IGameContext;
import com.github.sebasman.contracts.presenter.IUiProvider;
import com.github.sebasman.contracts.vo.Direction;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.contracts.presenter.IControlStrategy;
import com.github.sebasman.model.config.ModelConfig;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

/**
 * An advanced AI strategy that uses the A* algorithm to find the optimal route
 * to food and prioritizes survival to avoid lock-in.
 */
public final class SearchAlgorithmStrategy implements IControlStrategy, IUiProvider {
    private static final SearchAlgorithmStrategy INSTANCE = new SearchAlgorithmStrategy();
    // --- Configurable AI Parameters ---
    private float aiSpeed = ModelConfig.STARTING_FRAME_RATE;
    private boolean showPath = false;
    // --- Map of Configuration Handlers ---
    private final Map<String, Consumer<Object>> configHandlers;

    /**
     * Private builder to prevent its creation.
     */
    private SearchAlgorithmStrategy() {
        this.configHandlers = Map.of(
                "AI_SPEED", value -> this.setAiSpeed((Float) value),
                "AI_SHOW_PATH", value -> this.setShowPath((Boolean) value)
        );
        EventManager.getInstance().subscribe(ConfigurationChangedEvent.class, this::handleConfigurationChange);
    }

    /**
     * The single instance of the strategy returns.
     * @return Its single instance
     */
    public static SearchAlgorithmStrategy getInstance() {
        return INSTANCE;
    }

    /**
     * The central configuration event handler.
     * Searches for the event key in the handler map and executes the corresponding action.
     * @param event The configuration change event published by a UI component.
     */
    private void handleConfigurationChange(ConfigurationChangedEvent event){
        Consumer<Object> handler = configHandlers.get(event.key());
        // If it exists, it executes it, passing it the new value.
        if(handler != null){
            handler.accept(event.value());
        }
    }

    @Override
    public void update(IGameContext game, ISnakeAPI snake) {
        // --- Logic for Finding the Best Target ---

        Position head = snake.getHead();
        Set<IFoodAPI> foods = game.getSession().getFoods();
        IFoodAPI targetFood = this.findClosestFood(head, foods);
        if(targetFood == null) return;

        // --- Speed logic ---

        // Environmental Data Collection
        Position tail = snake.getTail();
        Position foodPos = targetFood.getPosition();
        Set<Position> allObstacles = new HashSet<>(snake.getBodySet());
        allObstacles.addAll(game.getSession().getBoard().getObstacles());
        Direction currentDirection = snake.getDirection();

        // --- Calculation of Potential Pathways ---

        // Plan A: Find your way to the food.
        List<Position> pathToFood = this.findPath(head, foodPos, allObstacles, currentDirection);
        // Plan B: Find your way to the tail to survive.
        Set<Position> obstaclesForTailPath = new HashSet<>(allObstacles);
        obstaclesForTailPath.remove(tail);
        List<Position> pathToTail = this.findPath(head, tail, obstaclesForTailPath, currentDirection);

        // --- Intelligent and Hierarchical Decision Logic ---

        Direction chosenDirection = null;

        // Priority 1: If there is a path to food, and it is safe, it is the best option.
        if(this.isPathValid(pathToFood) && this.isPathSafe(snake, pathToFood)){
            chosenDirection = getDirectionFromPath(head, pathToFood);
            // Notify the route for display if enabled
            if(showPath){ EventManager.getInstance().notify(new AiPathUpdatedEvent(pathToFood)); }
        } else{
            // Priority 2: If the path to food is "unsafe", but we have NO other choice (no path to the queue),
            // we take the risk. It's better than standing still.
            if(this.isPathValid(pathToTail)){
                chosenDirection = getDirectionFromPath(head, pathToTail);
                if(showPath){ EventManager.getInstance().notify(new AiPathUpdatedEvent(pathToTail)); }
            }
        }
        // Priority 3 (Last Resort): If none of the previous plans worked,
        // means we are trapped or in a very difficult situation. We make a panic move
        // to the safest adjacent square to avoid standing still.
        if(chosenDirection == null){
            chosenDirection = this.findSafestPanicMove(head, allObstacles, currentDirection);
        }
        // Execution of the movement
        snake.bufferDirection(chosenDirection);
    }

    /**
     * Finds the closest food object to a given position.
     * Uses the Manhattan distance for efficient comparison.
     * @param position The origin position (the head of the snake).
     * @param foods The list of available foods.
     * @return The nearest IFoodAPI object.
     */
    private IFoodAPI findClosestFood(Position position, Set<IFoodAPI> foods){
        IFoodAPI closestFood = null;
        double minDistance = Double.MAX_VALUE;
        for(IFoodAPI food : foods){
            double distance = Math.abs(position.x() - food.getPosition().x()) +
                    Math.abs(position.y() - food.getPosition().y());
            if(distance < minDistance){
                minDistance = distance;
                closestFood = food;
            }
        }
        return closestFood;
    }

    /**
     * Implementation of the A* algorithm to find the shortest path.
     * @param start Initial location of the algorithm
     * @param target Target arrival of the algorithm
     * @param obstacles Set of obstacles to be avoided by the algorithm
     * @param initialDirection The current direction of the snake to avoid reversal.
     * @return A list of positions to be followed by the snake
     */
    private List<Position> findPath(Position start, Position target, Set<Position> obstacles, Direction initialDirection) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Map<Position, Node> allNodes = new HashMap<>();

        Node startNode = new Node(start, null, 0, this.calculateHeuristic(start, target));
        openSet.add(startNode);
        allNodes.put(start, startNode);
        while(!openSet.isEmpty()){
            Node current = openSet.poll();
            // Already arrived at its destination
            if(current.position().equals(target)){
                return this.reconstructPath(current);
            }
            // Verify each position direction
            for(Direction direction : Direction.values()){
                if(current.parent() != null){
                    Direction arrivalDirection = getDirectionFromPositions(current.parent().position(), current.position());
                    if (direction.equals(arrivalDirection.opposite())) {
                        continue;
                    }
                }else {
                    // Only for the initial node, we respect the current address of the snake.
                    if (direction.equals(initialDirection.opposite())) {
                        continue;
                    }
                }
                Position neighborPos = current.position().add(new Position(direction.getDx(), direction.getDy()));
                if(this.isInvalid(neighborPos, obstacles) && !neighborPos.equals(target)){
                    continue;
                }
                double gCost = current.gCost() + 1;
                Node neighborNode = allNodes.get(neighborPos);
                if(neighborNode == null || gCost < neighborNode.gCost()){
                    if(neighborNode != null){
                        openSet.remove(neighborNode);
                    }
                    neighborNode = new Node(neighborPos, current, gCost, this.calculateHeuristic(neighborPos, target));
                    openSet.add(neighborNode);
                    allNodes.put(neighborPos, neighborNode);
                }
            }
        }
        // No route found
        return null;
    }

    /**
     * Checks if a path is safe by simulating the movement and verifying
     * if there is still an escape route to the queue.
     * @param snake A copy of the snake to be moved
     * @param pathToFood The route to follow and verify
     * @return True if the route is safe. False otherwise.
     */
    private boolean isPathSafe(ISnakeAPI snake, List<Position> pathToFood) {
        // Creates a virtual copy of the snake's body
        List<Position> virtualSnakeBody = new LinkedList<>(snake.getBody());
        // Simulates the movement along the path to the food.
        for(Position nextMove : pathToFood){
            virtualSnakeBody.addFirst(nextMove);
            virtualSnakeBody.removeLast();
        }
        // After eating the food in the last position, the snake grows.
        virtualSnakeBody.addLast(virtualSnakeBody.getLast());
        // The new head will be in the position of the food.
        Position futureHead = virtualSnakeBody.getFirst();
        Position futureTail = virtualSnakeBody.getLast();
        Set<Position> futureObstacles = new HashSet<>(virtualSnakeBody);
        futureObstacles.remove(futureHead);
        futureObstacles.remove(futureTail);
        // Determine future direction
        Direction futureDirection = getDirectionFromPositions(snake.getHead(), pathToFood.getFirst());
        // Checks if from the future position there is a path to the future queue.
        List<Position> scapePath = findPath(futureHead, futureTail, futureObstacles, futureDirection);
        return this.isPathValid(scapePath);
    }

    /**
     * Finds an escape direction when panicking
     * @param head Initial position of the snake
     * @param obstacles Obstacles to avoid
     * @param currentDirection The current direction of the movement
     * @return A direction in which the snake can escape
     */
    private Direction findSafestPanicMove(Position head, Set<Position> obstacles, Direction currentDirection){
        for(Direction dir: Direction.values()){
            if(dir.equals(currentDirection.opposite())) continue;
            Position nextPos = head.add(new Position(dir.getDx(), dir.getDy()));
            if(this.isInvalid(nextPos, obstacles)){
                return dir; // Choose the first safe direction, prioritizing going straight ahead.
            }
        }
        // If even the three main options are blocked, choose the opposite direction as a last resort.
        return currentDirection.opposite();
    }

    private boolean isInvalid(Position pos, Set<Position> obstacles) {
        return pos.x() < 0 || pos.x() >= ModelConfig.GRID_WIDTH ||
                pos.y() < 0 || pos.y() >= ModelConfig.GRID_HEIGHT ||
                obstacles.contains(pos);
    }

    /**
     * Manhattan distance heuristics.
     * @param a Position 1 to be calculated.
     * @param b Position 2 to be calculated.
     * @return The sum of both distances.
     */
    private double calculateHeuristic(Position a, Position b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    /**
     * reconstructs the algorithm's path by collecting the “breadcrumbs”
     * left by the algorithm at each node.
     * @param targetNode The destination node.
     * @return A list of Positions representing the route to take.
     */
    private List<Position> reconstructPath(Node targetNode) {
        LinkedList<Position> path = new LinkedList<>();
        Node current = targetNode;
        while(current.parent() != null){
            path.addFirst(current.position());
            current = current.parent();
        }
        return path;
    }

    /**
     * Gets the next direction the snake will follow.
     * @param head The position of the snake's current head.
     * @param path The route the snake should follow
     * @return The next direction of the snake
     */
    private Direction getDirectionFromPath(Position head, List<Position> path) {
        return this.getDirectionFromPositions(head, path.getFirst());
    }

    /**
     * Utility method to determine the direction between two adjacent positions.
     * @param from The source position.
     * @param to The destination position.
     * @return The direction of movement.
     */
    private Direction getDirectionFromPositions(Position from, Position to) {
        if (to.x() > from.x()) return Direction.RIGHT;
        if (to.x() < from.x()) return Direction.LEFT;
        if (to.y() > from.y()) return Direction.DOWN;
        return Direction.UP;
    }

    private boolean isPathValid(List<Position> path){
        return path != null && !path.isEmpty();
    }

    @Override
    public void keyPressed(IGameContext game, ISnakeAPI snake, int keyCode) {
        // The AI doesn't respond to the keyboard, so this method is empty.
    }

    @Override
    public List<IConfigParameter> getConfigurationParameters() {
        return List.of(
            new SliderConfigParameter("AI_SPEED", "Speed Snake", ModelConfig.STARTING_FRAME_RATE/2f, ModelConfig.STARTING_FRAME_RATE*2, this.aiSpeed),
            new CheckBoxConfigParameter("AI_SHOW_PATH", "Show Path", this.showPath)
        );
    }

    @Override
    public boolean isGameStartAction(int keyCode) {
        // The AI does not start the game with a key, but with a UI button.
        return false;
    }

    @Override
    public float getDesiredSpeed() {
        return this.aiSpeed;
    }


    // --- Setters ---

    /**
     * Change the speed snake
     * @param aiSpeed the new value for the speed
     */
    private void setAiSpeed(float aiSpeed) {
        EventManager.getInstance().notify(new GameSpeedChangedEvent(aiSpeed));
        this.aiSpeed = aiSpeed;
    }

    /**
     * Change the show path of the snake
     * @param showPath true or false
     */
    private void setShowPath(boolean showPath) {
        this.showPath = showPath;
    }

    /**
     * Represents a node in the search of algorithm A*. Contains the position in the grid,
     * the associated costs (g, h, f) and a reference to its parent node to reconstruct the path.
     * Implements Comparable to be used directly in a PriorityQueue.
     * @param position The (x, y) coordinate of this node in the grid.
     * @param parent The node from which this node was arrived at.
     * @param gCost The cost of the path from the start to this node.
     * @param hCost The estimated heuristic cost from this node to the end.
     */
    private record Node(Position position, Node parent, double gCost, double hCost) implements Comparable<Node>{
        /**
         * Total cost (f_cost) is the sum of g_cost and h_cost.
         * @return the estimated total cost of the path through this node.
         */
        public double getFCost() { return gCost + hCost; }

        @Override
        public int compareTo(@NotNull Node o) {
            // It compares the nodes based on their f_cost. If they are equal, it uses the h_cost as a tie-breaker.
            // This causes the algorithm to prioritize the nodes that are closest to the target.
            int fCostCompare = Double.compare(this.getFCost(), o.getFCost());
            if(fCostCompare == 0){
                return Double.compare(this.hCost, o.hCost);
            }
            return fCostCompare;
        }
    }
}
