package app;

import implementations.Word;
import utilities.Iterator;
import implementations.BSTree;
import implementations.BSTreeNode;

import java.io.*;
import java.util.*;

public class WordTracker {
	
	private static final String REPO_FILE = "repository.ser";
	
	public static void main(String[] args) {
		
		//validating arguments 
		if (args.length < 2) {
			
			System.out.println(" Use => java WordTracker <input.txt> -pf/-pl/-po [-f output.txt] ");
			
			return;
			
		}
		
		String inputFile = args[0];
		String flag = args[1];
		String outputFile = null;
		
		if (args.length == 4 && args[2].equals("-f")) {
			
			outputFile = args[3];
			
		}
		
		//loading or creating BST
		BSTree<Word> tree = loadRepository();
		
		//reading input text file and adding words
		processInputFile(inputFile, tree);
		
		//saving updated tree 
		saveRepository(tree);
		
		generateOutput(tree, flag, outputFile);
		
	}
		
	// placeholder methods to fill....
		private static BSTree<Word> loadRepository(){
			
			File file = new File(REPO_FILE);
			
			if (!file.exists()) {
				
				// no repository yet -- return empty BST
				return new BSTree<>();
				
			}
			
			//loading 
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
				
				@SuppressWarnings("unchecked")
				BSTree<Word> tree = (BSTree<Word>) ois.readObject();
				
				return tree;
				
			}catch (Exception e) {
				
				e.printStackTrace();
				return new BSTree<>();
				
			}
			
			
			
		}
		
		private static void saveRepository(BSTree<Word> tree) {
			
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(REPO_FILE))) {
				
				oos.writeObject(tree);
				
			}catch (Exception e) {
				
				e.printStackTrace();
				
			}
			
		}
		
		private static void processInputFile(String fileName, BSTree<Word> tree) {
			
			try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
				
				String line;
				int lineNumber = 1;
				
				while ((line = br.readLine()) != null ) {
					
					//removing punctuation except letters and digits.... 
					line = line.replaceAll("[^a-zA-Z0-9 ]", " ").toLowerCase();
					
					String[] words = line.split("\\s+");
					
					for (String w : words) {
						
						// skip empty....
						if (w.trim().isEmpty()) continue; 
						
						Word temp = new Word(w);
						
						//Checking if word exists already..
						BSTreeNode<Word> node = tree.search(temp);
						Word existing = (node != null) ? node.getData() : null;
						
						
						if (existing == null) {
							
							//new word
							Word newWord = new Word(w);
							newWord.addOccurence(fileName, lineNumber);
							tree.add(newWord);
							
							
						}else {
							
							//Updating existing 
							existing.addOccurence(fileName, lineNumber);
							
						}
						
						
					}
					
					lineNumber++;
					
				} 
				
			}catch (Exception e) {
				
				e.printStackTrace();
			}
			
		}
		
		private static void generateOutput(BSTree<Word> tree, String flag, String outputFile) {
			
			StringBuilder sb = new StringBuilder();
			
			//Alphabetical order (inorder traveral); 
			Iterator<Word> iterator = tree.inorderIterator();
			
			while (iterator.hasNext()) {
				
				Word w = iterator.next();
				String line = "";
				
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
					default: 
						System.out.println("Invalid flag. Use -pf, -pl, or, -po");
					return;	
				
				
				}
				
				sb.append(line).append("\n");
				
			}
			
			//Output to screen or file..
			
			if (outputFile == null) {
				
				//Print to console
				System.out.print(sb.toString());
				
			}else {
				
				//write to file 
				try (PrintWriter pw = new PrintWriter(new FileWriter(outputFile))) {
					
					pw.print(sb.toString());
					
					
				}catch (Exception e) {
					
					e.printStackTrace();
					
				}
				
			}
			
		}
		
	
	
}
