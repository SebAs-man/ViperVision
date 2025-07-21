# ViperVision Project Roadmap
A structured plan to evolve the ViperVision project from its current alpha state to a polished, portfolio-ready application.

## Phase 0: Foundation Hardening & Refactoring
> [!NOTE]
> Solidify the existing codebase, improve robustness, and establish a testing culture before adding new features.

* [ ] Task 0.1: Setup Testing Framework
    * Integrate JUnit 5 and Mockito into the pom.xml.
    * Create src/test/java source folder.

* [ ] Task 0.2: Implement Unit Tests for the Model Layer
    * Write tests for Snake class (movement, growth, self-collision).
    * Write tests for Board class (wall collision, position validation).
    * Write tests for GameSession state logic (score, game over conditions).
    * Write tests for individual IEffect implementations.

* [ ] Task 0.3: Refactor to Value Objects
    * Convert the Position class to an immutable Java record.
    * Update all usages of Position to work with its new immutable nature.

* [ ] Task 0.4 (Advanced): Dependency Injection Setup
    * Research: Investigate Google Guice for Dependency Injection.
    * Implement: Create Guice "Modules" to define the bindings for your interfaces (e.g., bind(IGameSession.class).to(GameSession.class)).
    * Refactor Main: Refactor the entry point to use the Guice Injector to create the main application objects, instead of manual new instantiation.

## Phase 1: Core Gameplay and AI Implementation
> [!NOTE]
> Complete the core game mechanics and implement the planned AI strategies.

* [ ] Task 1.1: A Search Algorithm*
    * Implement the A* pathfinding algorithm.
    * Crucial: Run the A* calculation on a separate worker thread (new Thread or ExecutorService) to prevent UI freezing.
    * Use a PriorityQueue for the OPEN set for optimal performance.
    * Create a new event AiPathFoundEvent to safely communicate the resulting path back to the main game thread.

* [ ] Task 1.2: Genetic Algorithm
    * Design the chromosome structure for a snake's "brain."
    * Implement the core genetic operators: selection, crossover, and mutation.
    * Develop a fitness function (e.g., based on score, survival time).
    * Integrate it as a new IControlStrategy.

* [ ] Task 1.3: Bug Fixing
    * Address the known bug related to snake update speed changes.
    * Use the debugger and newly created tests to identify and fix other potential issues.

* [ ] Task 1.4: Gameplay Balancing
    * Adjust food spawn rates.
    * Balance the effects of different food types (duration, power).
    * Tune the game difficulty curve.

## Phase 2: UI/UX and Feature Polish
> [!NOTE]
> Enhance the user experience with a polished interface, better feedback, and quality-of-life features.

* [ ] Task 2.1: UI Enhancements
    * Add smooth transitions between game states (e.g., fade-in/fade-out).
    * Improve button feedback (hover effects, click animations).
    * Design a more visually appealing main menu and game over screen.

* [ ] Task 2.2: Sound Design
    * Add background music for the menu and gameplay.
    * Implement more sound effects for different events (e.g., power-up activation, poison).
    * Add a volume control slider in the menu.

* [ ] Task 2.3: User Profile and Persistence
    * Implement saving/loading of the UserProfile (high score) to a local file (e.g., JSON or simple text file).

* [ ] Task 2.4: Comprehensive In-Game HUD
    * Display active effects and their remaining duration on the HUD.
    * Show current AI mode.

## Phase 3: Documentation and Deployment
> [!NOTE]
> Finalize all documentation and package the application for easy distribution.

* [ ] Task 3.1: Code Documentation
    * Review and complete all Javadocs for public classes and methods. Ensure they are clear and consistent.

* [ ] Task 3.2: Project Documentation
    * Update README.md with final features, screenshots, and a GIF of the gameplay.
    * Add a section explaining the architecture and the design patterns used.
    * Create a final sequence diagram for a key interaction (e.g., "Player Eats Golden Apple").

* [ ] Task 3.3: Build and Packaging
    * Configure the Maven build process to create a single, executable .jar file (using maven-shade-plugin or similar) that includes all dependencies.

* [ ] Task 3.4: Final Review
    * Perform a final code review, checking for code style consistency, clarity, and any remaining "code smells."
    * Playtest the final build extensively.




