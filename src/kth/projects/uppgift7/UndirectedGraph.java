package kth.projects.uppgift7;

import kth.projects.uppgift7.VertexIterator;

/**
 * An undirected graph with a fixed number of vertices.
 * The vertices are accessed using integers from 0 to V-1,
 * were V is the number of vertices in the graph.
 * Edges may be added or removed from the graph.
 * An edge may have an optional non-negative cost.
 * 
 * @author Stefan Nilsson
 * @version 2010-02-24
 */

public interface UndirectedGraph {
    /**
     * Returns the number of vertices in this graph.
     * @return the number of vertices in this graph
     */
     int numVertices();

    /**
     * Returns the number of edges in this graph.
     * @return the number of edges in this graph
     */
    int numEdges();

    /**
     * Returns the degree of vertex v.
     * 
     * @param  v vertex
     * @return the degree of vertex v
     * @throws IllegalArgumentException if v is out of range
     */
    int degree(int v) throws IllegalArgumentException;

    /**
     * Returns an iterator of vertices adjacent to v.
     * 
     * @param  v vertex
     * @return an iterator of vertices adjacent to v
     * @throws IllegalArgumentException if v is out of range
     */
    VertexIterator adjacentVertices(int v) throws IllegalArgumentException;

    /**
     * Returns true iff v is adjacent to w.
     * 
     * @param  v vertex
     * @param  w vertex
     * @return true iff v is adjacent to w
     * @throws IllegalArgumentException if v or w are out of range
     */
    boolean areAdjacent(int v, int w) throws IllegalArgumentException;

    /**
     * Returns the edge cost if v and w are adjacent and an edge cost
     * has been assigned, -1 otherwise.
     * 
     * @param  v vertex
     * @param  w vertex
     * @return edge cost if available, -1 otherwise
     * @throws IllegalArgumentException if v or w are out of range
     */
    int edgeCost(int v, int w) throws IllegalArgumentException;

    /**
     * Inserts an undirected edge between v and w.
     * (No edge cost is assigned.)
     * 
     * @param  v vertex
     * @param  w vertex
     * @throws IllegalArgumentException if v or w are out of range
     */
    void addEdge(int v, int w) throws IllegalArgumentException;

    /**
     * Inserts an undirected edge with edge cost c between v and w.
     * 
     * @param  c edge cost, c >= 0
     * @param  v vertex
     * @param  w vertex
     * @throws IllegalArgumentException if v or w are out of range
     * @throws IllegalArgumentException if c < 0
     */
    void addEdge(int v, int w, int c) throws IllegalArgumentException;

    /**
     * Removes the edge between v and w.
     * 
     * @param  v vertex
     * @param  w vertex
     * @throws IllegalArgumentException if v or w are out of range
     */
    void removeEdge(int v, int w) throws IllegalArgumentException;
}
