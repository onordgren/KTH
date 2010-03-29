package kth.projects.uppgift7;


/**
 * An example implementation of depth first search.
 * 
 * @author Stefan Nilsson
 * @version 2010-02-24
 */
public class GraphAlgorithms {
    private static final boolean DEBUG = false;

    public GraphAlgorithms() {}

    /**
     * Builds a graph and prints the components to stdout.
     * @param args not used
     */
    public static void main(String[] args) {
        new GraphAlgorithms().testSearch();
    }

    /**
     * Builds a graph g and prints the components of g to stdout.
     */
    public void testSearch() {
        //   The graph g:
        //
        //   0---1   2---
        //   |   |   |  |
        //   3---4   ----
        //   |
        //   5   6---7
        final int size = 8;
        final UndirectedGraph g = new MatrixGraph(size);
        g.addEdge(0, 1);
        g.addEdge(2, 2);
        g.addEdge(0, 3);
        g.addEdge(1, 4);
        g.addEdge(3, 4);
        g.addEdge(5, 3);
        g.addEdge(6, 7);

        System.out.println();
        System.out.println(g);
        printComponentsDFS(g);
    }

    /**
     * Prints the components of g to stdout in depth-first order.
     * Each component is written on a separate line.
     */
    private void printComponentsDFS(UndirectedGraph g) {
        final int size = g.numVertices();
        final boolean[] visited = new boolean[size];
        final VertexAction action = new PrintAction();

        System.out.printf("%n%s%n", "The components of g (DFS):");
        // Do a DFS for each node that has not yet been visited.
        for (int v = 0; v < size; v++) {
            if (!visited[v]) {
                depthFirstSearch(g, v, visited, action);
                System.out.println();
            }
        }
    }

    private static class PrintAction implements VertexAction {
    	@Override
        public void act(UndirectedGraph g, int v) {
            System.out.print(v + " ");
        }
    }

    /**
     * Traverses the nodes of g that have not yet been visited.
     * The nodes are visited in depth-first order starting at a.
     * The act() method in the VertexAction object is called once for
     * each node.
     * 
     * @param  g an undirected graph
     * @param  a start vertex
     * @param  visited visited[i] is true iff node i has been visited
     */
    private void depthFirstSearch(UndirectedGraph g, int a,
            boolean[] visited,
            VertexAction action) {
        if (DEBUG) {
            System.out.println("[=> DFS a=" + a + " " + visited[a] + "]");
        }
        if (visited[a]) {
            return;
        }
        
        visited[a] = true;
        action.act(g, a);

        final VertexIterator vi = g.adjacentVertices(a);
        while (vi.hasNext()) {
            depthFirstSearch(g, vi.next(), visited, action);
        }
        if (DEBUG) {
            System.out.println("[<= DFS a=" + a + "]");
        }
    }
}