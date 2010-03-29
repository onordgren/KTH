package kth.projects.uppgift7;

import java.util.NoSuchElementException;

import kth.projects.uppgift7.VertexIterator;

/**
 * An undirected graph with a fixed number of vertices implemented using
 * an adjacency matrix. Space complexity is O(V*V) where V is the number
 * of vertices.
 * 
 * @author Stefan Nilsson
 * @version 2010-02-24
 */
public class MatrixGraph implements UndirectedGraph {
    /** Number of vertices in the graph. */
    private final int numVertices;
    
    /** Number of edges in the graph. */
    private int numEdges;

    /**
     *  Adjaceny matrix. cost[v][w] is -2 if v is not adjacent to w,
     *  otherwise cost[v][w] is -1 or the non-negative cost of the edge.
     */
    private final int[][] cost;
    private final static int EMPTY = -2;   // no edge
    private final static int NO_COST = -1; // an edge with no cost

    /**
     * Constructs a MatrixGraph with v vertices and no edges.
     * Time complexity: O(v<sup>2</sup>)
     * 
     * @throws IllegalArgumentException if v < 0
     */
    public MatrixGraph(int v) {
        if (v < 0)
            throw new IllegalArgumentException("v = " + v);

        numVertices = v;
        numEdges = 0;
        cost = new int[v][v];
        for (int i = 0; i < v; i++)
            for (int j = 0; j < v; j++)
                cost[i][j] = EMPTY;
    }

    /**
     * Returns the number of vertices in this graph.
     * Time complexity: O(1).
     * 
     * @return the number of vertices in this graph
     */
    @Override
    public int numVertices() {
        return numVertices;
    }

    /**
     * Returns the number of edges in this graph.
     * Time complexity: O(1).
     * 
     * @return the number of edges in this graph
     */
    @Override
    public int numEdges() {
        return numEdges;
    }

    /**
     * Returns the degree of vertex v.
     * Time complexity: O(v),
     * where v is the number of vertices in the graph.
     * 
     * @param  v vertex
     * @return the degree of vertex v
     * @throws IllegalArgumentException if v is out of range
     */
    @Override
    public int degree(int v) throws IllegalArgumentException {
        checkVertexParameter(v);

        int d = 0;
        for (int i = 0; i < numVertices; i++)
            if (cost[v][i] != EMPTY)
                d++;
        return d;
    }

    /**
     * Returns an iterator of vertices adjacent to v.
     * Time complexity for iterating over all vertices: O(v),
     * where v is the number of vertices in the graph.
     * 
     * @param  v vertex
     * @return an iterator of vertices adjacent to v
     * @throws IllegalArgumentException if v is out of range
     */
    @Override
    public VertexIterator adjacentVertices(int v)
    throws IllegalArgumentException {
        checkVertexParameter(v);

        return new AdjacentVerticesIterator(v);
    }

    private class AdjacentVerticesIterator implements VertexIterator {
        int nextPos = -1;
        final int vertex;

        AdjacentVerticesIterator(int v) {
            vertex = v;
            findNext();
        }

        private void findNext() {
            nextPos++;
            while (nextPos < numVertices &&
                    cost[vertex][nextPos] == EMPTY)
                nextPos++;
        }

        public boolean hasNext() {
            return nextPos < numVertices;
        }

        public int next() {
            int pos = nextPos;
            if (pos < numVertices) {
                findNext();
                return pos;
            }
            throw new NoSuchElementException(
            "This iterator has no more elements.");
        }
    }

    /**
     * Returns true iff v is adjacent to w.
     * Time complexity: O(1).
     * 
     * @param  v vertex
     * @param  w vertex
     * @return true iff v is adjacent to w
     * @throws IllegalArgumentException if v or w are out of range
     */
    @Override
    public boolean areAdjacent(int v, int w)
    throws IllegalArgumentException {
        checkVertexParameters(v, w);

        return cost[v][w] != EMPTY;
    }

    /**
     * Returns the edge cost if v and w are adjacent to w and an edge cost
     * has been assigned, -1 otherwise. Time complexity: O(1).
     * 
     * @param  v vertex
     * @param  w vertex
     * @return true iff v is adjacent to w
     * @throws IllegalArgumentException if v or w are out of range
     */
    @Override
    public int edgeCost(int v, int w) throws IllegalArgumentException {
        checkVertexParameters(v, w);

        return Math.max(NO_COST, cost[v][w]);
    }

    /**
     * Inserts an undirected edge between v and w.
     * (No edge cost is assigned.) Time complexity: O(1).
     * 
     * @param  v vertex
     * @param  w vertex
     * @throws IllegalArgumentException if v or w are out of range
     */
    @Override
    public void addEdge(int v, int w) throws IllegalArgumentException {
        checkVertexParameters(v, w);

        if (cost[v][w] == EMPTY)
            numEdges++;
        cost[v][w] = NO_COST;
        cost[w][v] = NO_COST;
    }

    /**
     * Inserts an undirected edge with cost c between v and w.
     * 
     * @param  c edge cost, c >= 0
     * @param  v vertex
     * @param  w vertex
     * @throws IllegalArgumentException if v or w are out of range
     * @throws IllegalArgumentException if c < 0
     */
    @Override
    public void addEdge(int v, int w, int c)
    throws IllegalArgumentException {
        checkVertexParameters(v, w);
        checkNonNegativeCost(c);

        if (cost[v][w] == EMPTY)
            numEdges++;
        cost[v][w] = c;
        cost[w][v] = c;
    }

    /**
     * Removes the edge between v and w.
     * Time complexity: O(1).
     * 
     * @param  v vertex
     * @param  w vertex
     * @throws IllegalArgumentException if v or w are out of range
     */
    @Override
    public void removeEdge(int v, int w)
    throws IllegalArgumentException {
        checkVertexParameters(v, w);

        if (cost[v][w] != EMPTY) {
            cost[v][w] = EMPTY;
            cost[w][v] = EMPTY;
            numEdges--;
        }
    }

    /**
     * Returns a string representation of this graph.
     * 
     * @return a String representation of this graph
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: " + numVertices + ", Edges: {");
        for (int i = 0; i < numVertices; i++) {
            for (int j = i; j < numVertices; j++) {
                if (cost[i][j] == NO_COST) {
                    sb.append("(" + i + "," + j + "), ");
                } else if (cost[i][j] >= 0) {
                    sb.append("(" + i + "," + j + "," + cost[i][j] + "), ");
                }
            }
        }
        if (numEdges > 0)
            sb.setLength(sb.length() - 2); // Remove trailing ", "
        sb.append("}");
        return sb.toString();
    }

    /**
     * Checks a single vertex parameter v.
     * @throws IllegalArgumentException if v is out of range
     */
    private void checkVertexParameter(int v) {
        if (v < 0 || v >= numVertices)
            throw new IllegalArgumentException(
                    "Out of range: v = " + v + ".");
    }

    /**
     * Checks two vertex parameters v and w.
     * @throws IllegalArgumentException if v or w is out of range
     */
    private void checkVertexParameters(int v, int w) {
        if (v < 0 || v >= numVertices || w < 0 || w >= numVertices)
            throw new IllegalArgumentException(
                    "Out of range: v = " + v + ", w = " + w + ".");
    }

    /**
     * Checks that the cost c is non-negative.
     * @throws IllegalArgumentException if c < 0
     */
    private void checkNonNegativeCost(int c) {
        if (c < 0)
            throw new IllegalArgumentException(
                    "Illegal cost: c = " + c + ".");
    }
}
