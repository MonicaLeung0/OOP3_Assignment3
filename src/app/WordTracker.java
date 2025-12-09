package app;

import implementations.Word;
import utilities.Iterator;
import implementations.BSTree;
import implementations.BSTreeNode;

import java.io.*;
import java.util.*;

/**
 * The main application for the Word Tracker program.
 * It reads text files, stores unique words and their locations (file/line) in a Binary Search Tree (BST),
 * serializes the BST to a repository file for persistence, and generates reports based on command line flags.
 *
 * @author  Precious, Monica, Jasmine, Mitali
 */

public class WordTracker {
	
	private static final String REPO_FILE = "repository.ser";
	
	/**
	 * Main method to run the Word Tracker. Handles command-line arguments and orchestrates the process.
	 * Expected arguments: <input.txt> -pf/-pl/-po [-f output.txt]
	 * @param args Command line arguments provided by the user.
	 */
	public static void main(String[] args) {
		
		// 1. Initial argument validation (minimum 2 arguments required)
		if (args.length < 2) {
			
			System.out.println(" Use => java WordTracker <input.txt> -pf/-pl/-po [-f output.txt] ");
			return;
			
		}
		
		String inputFile = args[0];
		String flag = args[1];
		String outputFile = null;
		
		// 2. Mutual Exclusion Check: Ensure only one valid flag is used.
		if (!flag.matches("-pf|-pl|-po")) {
			System.err.println("Error: Invalid flag '" + flag + "'. Use -pf, -pl, or -po.");
			return;
		}
		
		// 3. Optional output file check
		if (args.length == 4 && args[2].equals("-f")) {
			outputFile = args[3];
		} else if (args.length > 2 && !(args.length == 4 && args[2].equals("-f"))) {
			// Catch cases like 3 arguments or 4 arguments where the 3rd isn't -f
			System.err.println("Error: Invalid argument format. Ensure flags are mutually exclusive and '-f' is followed by an output filename.");
			return;
		}

		
		// 4. Loading or creating BST
		BSTree<Word> tree = loadRepository();
		
		// 5. Reading input text file and adding words
		// We only continue if the file processing is successful (returns true).
		boolean success = processInputFile(inputFile, tree);
		
		if (success) {
			// 6. Saving updated tree ONLY if the input file was processed successfully.
			saveRepository(tree);
		} else {
			// If file processing failed (e.g., FileNotFound), we stop here unless we explicitly want to run generateOutput on the old repo data.
			System.err.println("Program terminated due to file processing error. Repository state unchanged.");
			return;
		}
		
		// 7. Generating output report
		generateOutput(tree, flag, outputFile);
		
	}
		
	/**
	 * Loads the existing BST from the repository file (repository.ser).
	 * @return The restored BST or a new empty BST.
	 */
	private static BSTree<Word> loadRepository(){
			
		File file = new File(REPO_FILE);
		
		if (!file.exists()) {
			
			// If no repository file is found, start with a new, empty BST.
			return new BSTree<>();
			
		}
		
		// Attempting to load the serialized BST
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			
			@SuppressWarnings("unchecked")
			BSTree<Word> tree = (BSTree<Word>) ois.readObject();
			
			return tree;
			
		}catch (Exception e) {
			
			System.err.println("Error loading repository. Starting with a new tree.");
			e.printStackTrace();
			return new BSTree<>();
			
		}
	}
		
	/**
	 * Saves the current BST to the repository file (repository.ser) using Java serialization.
	 * @param tree The BST to be serialized and saved.
	 */
	private static void saveRepository(BSTree<Word> tree) {
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(REPO_FILE))) {
			
			oos.writeObject(tree);
			
		}catch (Exception e) {
			
			System.err.println("Error saving repository.");
			e.printStackTrace();
			
		}
		
	}
		
	/**
	 * Reads the input file, processes words, and updates/inserts them into the BST.
	 * @param fileName The path/name of the file to process.
	 * @param tree The BST (repository) to update.
	 * @return true if file processing was successful, false otherwise.
	 */
	private static boolean processInputFile(String fileName, BSTree<Word> tree) {
		
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			
			String line;
			int lineNumber = 1;
			
			while ((line = br.readLine()) != null ) {
				
				// Preprocessing: remove punctuation (keep letters, digits, and spaces) and convert to lowercase.
				line = line.replaceAll("[^a-zA-Z0-9 ]", " ").toLowerCase();
				
				String[] words = line.split("\\s+");
				
				for (String w : words) {
					
					// Skip empty strings.
					if (w.trim().isEmpty()) continue; 
					
					// Create a temporary Word object for searching.
					Word temp = new Word(w);
					
					// Checking if word exists already using the BST search logic.
					BSTreeNode<Word> node = tree.search(temp);
					Word existing = (node != null) ? node.getData() : null;
					
					
					if (existing == null) {
						
						// New word: record occurrence and add new node to BST.
						Word newWord = new Word(w);
						newWord.addOccurence(fileName, lineNumber);
						tree.add(newWord);
						
						
					}else {
						
						// Existing word: update the occurrence data on the existing Word object (mutation).
						existing.addOccurence(fileName, lineNumber);
						
					}
					
					
				}
				
				lineNumber++;
				
			} 
			
			return true; // Successfully processed the file.
			
		}catch (FileNotFoundException e) {
		    System.err.println("Error: Input file '" + fileName + "' not found. No words were processed.");
		    return false; // Processing failed.
		}catch (IOException e) {
		    System.err.println("Error reading file: " + fileName);
		    e.printStackTrace();
		    return false; // Processing failed.
		}catch (Exception e) {
			System.err.println("An unexpected error occurred during file processing.");
			e.printStackTrace();
			return false; // Processing failed.
		}
	}
		
	/**
	 * Generates the final output report based on the flag and redirects output if specified.
	 * @param tree The final, updated BST.
	 * @param flag The output format flag (-pf, -pl, -po).
	 * @param outputFile The file to write to, or null for console output.
	 */
	private static void generateOutput(BSTree<Word> tree, String flag, String outputFile) {
		
		StringBuilder sb = new StringBuilder();
		
		// Use the in-order iterator for the required alphabetical order of words.
		Iterator<Word> iterator = tree.inorderIterator();
		
		while (iterator.hasNext()) {
			
			Word w = iterator.next();
			String line = "";
			
			// Selects the appropriate output formatting method based on the command-line flag.
			switch (flag) {
				case "-pf": 
					line = w.toPFString();
					break;
				case "-pl":
					line = w.toPLString();
					break;
				case "-po":
					line = w.toPOString();
					break;
				// NOTE: Invalid flag check is already done in main, but left for redundancy.
				default: 
					System.out.println("Internal Error: Invalid flag passed to generateOutput.");
					return;	
			}
			
			sb.append(line).append("\n");
			
		}
		
		// Output to screen or file.
		if (outputFile == null) {
			
			// Print the accumulated report to the console.
			System.out.print(sb.toString());
			
		}else {
			
			// Write the report to the specified file using PrintWriter.
			try (PrintWriter pw = new PrintWriter(new FileWriter(outputFile))) {
				
				pw.print(sb.toString());
				
			}catch (Exception e) {
				
				System.err.println("Error writing report to output file: " + outputFile);
				e.printStackTrace();
				
			}
			
		}
		
	}
}