package kth.projects.uppgift8;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Path {
	private static void readFile(String fileName) {
		try {
			File file = new File(fileName);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				if(scanner.next().equals("//"))
					System.out.println(scanner.next());
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
