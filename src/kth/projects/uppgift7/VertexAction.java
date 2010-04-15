package kth.projects.uppgift7;


/**
 * This interface contains a single act() method that may be called
 * for each node during a traversal of a graph.
 * 
 * @author Stefan Nilsson
 * @version 2010-02-24
 */

public interface VertexAction {
    /**
     * An action performed when a search of the graph g visits node v.
     * 
     * @param  g    a graph
     * @param  v	a vertex in the graph
     */
    void act(UndirectedGraph g, int v);
}
