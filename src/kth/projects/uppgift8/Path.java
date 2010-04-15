package kth.projects.uppgift8;


import java.io.*;
import java.util.*;
import kth.projects.uppgift7.MatrixGraph;
import kth.projects.uppgift7.UndirectedGraph;
import kth.projects.uppgift7.VertexIterator;

/**
 * Class Path which scans a file with coordinates and returns the closest path
 * from the goal to the end declared by the user.
 * Usage: java Path FROM TO.
 * @author otto
 *
 */
public class Path {
	private final static String NAME = "Path";
    private final static String USAGE = "Usage: java " + NAME + " FROM TO";
    private final static String TYPE_ERR = "Wrong type, must be int";
    private final static String OPEN_ERR = NAME + ": cannot find ";
    private final static String READ_ERR = NAME + ": error reading ";
    private static UndirectedGraph graph;
    
    /**
     * Scans a textfile with coordinates for a graph.
     * @param args Takes two numbers FROM and TO.
     * @return
     */
	private static boolean readFile(String[] args) {
		if (args.length != 2) {
            System.err.println(USAGE);
            return false;
        }
		String line;
		String fileName = "Distances.txt";
		final int from;
		final int to;
		
		try {
			from = Integer.parseInt(args[0]);
			to = Integer.parseInt(args[1]);
		} 
		catch(IllegalArgumentException e) {
			System.err.println(TYPE_ERR);
			return false;
		}
		
		Scanner scanner;
		File file;
		
		try {
			file = new File(fileName);
			scanner = new Scanner(file);
		}
		catch (FileNotFoundException e) {
            System.err.println(OPEN_ERR + fileName);
            return false;
        } catch (IOException e) {
            System.err.println(READ_ERR + fileName);
            return false;
        }
		while(scanner.hasNext()) {
			line = scanner.nextLine();
			if(line.startsWith("//"))
				System.out.print("");
			else if(line.contains("// Size")) {
				String[] sizes;
				int size;
				sizes = line.split(" ", 2);
				System.out.println(sizes[0] + "Size");
				size = Integer.parseInt(sizes[0]);
				graph = new MatrixGraph(size);
			}
			else if(line.length() != 0 && !line.startsWith("//")) {
				int v, w, c;
				String[] vertices;
				vertices = line.split(" ", 6);
				System.out.println(vertices[0] + " " + vertices[1] + " " + vertices[2]);
				v = Integer.parseInt(vertices[0]);
				w = Integer.parseInt(vertices[1]);
				c = Integer.parseInt(vertices[2]);
				graph.addEdge(v, w, c);
			}
		}
		System.out.println("Shortest path from " + from + " to " + to +  ": " + BFS(graph, from, to));
		
		return false;
	}
	
	/**
	 * Mainmethod which runs readFile.
	 * @param args
	 */
	public static void main(String[] args) {
		System.exit(Path.readFile(args) ? 0 : 1);
	}
	
	/**
	 * Searches the graph for a way from start to goal. If the goal is found;
	 * returns the closest path from start to the goal.
	 * @param graph The type graph to use in when building the graph
	 * @param start Starting vertex
	 * @param goal Ending vertex
	 * @return
	 */
	public static String BFS(UndirectedGraph graph, int start, int goal) {
		LinkedList<Integer> q = new LinkedList<Integer>(); //Queue list
		LinkedList<Integer> v = new LinkedList<Integer>(); //Visited list
		LinkedList<Integer> p = new LinkedList<Integer>(); //Path list
		boolean found = false; 
		VertexIterator adjVert; //Iterator with adjacent vertices
		q.add(start); //Adds starting vertex to the queue
		while(!q.isEmpty() && !found) { 
			int a = q.poll();
			adjVert = graph.adjacentVertices(a);
			v.add(a);
			while(adjVert.hasNext()) {
				int next = adjVert.next();
				if(next == goal) {
					found = true;
					break;
				}
				if(!v.contains(next) && !q.contains(next))
					q.add(next);
			}
		}
		if(found) {
			p.addFirst(goal);
			int current = goal;
			while(current != start) {
				adjVert = graph.adjacentVertices(current);
				while(adjVert.hasNext()) {
					int next = adjVert.next();
					if(v.contains(next)) {
						if(!p.contains(next))
							p.addFirst(next);
					}
					current = next;
					break;
				}
			}
		}
		return p.toString();
	}
}
