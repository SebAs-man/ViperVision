package com.github.sebasman.presenter.strategies;

import com.github.sebasman.contracts.vo.Position;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a node in the search of algorithm A*. Contains the position in the grid,
 * the associated costs (g, h, f) and a reference to its parent node to reconstruct the path.
 * Implements Comparable to be used directly in a PriorityQueue.
 * @param position The (x, y) coordinate of this node in the grid.
 * @param parent The node from which this node was arrived at.
 * @param gCost The cost of the path from the start to this node.
 * @param hCost The estimated heuristic cost from this node to the end.
 */
public record Node(Position position, Node parent, double gCost, double hCost) implements Comparable<Node>{
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
