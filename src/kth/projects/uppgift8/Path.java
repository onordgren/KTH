package kth.projects.uppgift8;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import kth.projects.uppgift7.MatrixGraph;

public class Path {
	private static MatrixGraph graph;

	private static void readFile(String fileName) {
		String line;
		try {
			File file = new File(fileName);
			Scanner scanner = new Scanner(file);
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
				graph.toString();
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("usage: java TextScanner1"
					+ "file location");
			System.exit(0);
		}
		readFile(args[0]);
	}
}
