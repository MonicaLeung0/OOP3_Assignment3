package implementations;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.*;

/**
 * Represents a unique word found in the text files, tracking its occurrences
 * across different files and line numbers. This object is stored as the data element
 * in the BST. It implements Comparable for sorting and Serializable for persistence.
 *
 * @author  Precious, Monica, Jasmine, Mitali
 */
public class Word implements Comparable<Word>, Serializable {

	private static final long serialVersionUID = 1L;
	
	private String word; // The word string itself.
	// Maps a filename (String) to a List of line numbers (Integer) where the word occurs in that file.
	private Map<String, List<Integer>> fileMap;
	
	/**
	 * Constructor for the Word object.
	 * Initializes the word string and an empty HashMap to track occurrences.
	 * @param word The unique word string.
	 */
	public Word(String word) {
		
		this.word = word;
		this.fileMap = new HashMap<>(); // Initialize the map for file tracking.
	
	}
	
	/**
	 * Retrieves the word string.
	 * @return The word string.
	 */
	public String getWord() {
		
		return word;
		
	}
	
	/**
	 * Records a new occurrence of the word. If the file is new, it creates a new
	 * list for line numbers for that file.
	 * @param fileName The name of the file where the word was found.
	 * @param lineNumber The line number where the word was found.
	 */
	public void addOccurence(String fileName, int lineNumber ) {
		
		// Adds a new ArrayList if the key (fileName) is absent; otherwise, returns the existing list.
		fileMap.putIfAbsent(fileName, new ArrayList<>());
		// Adds the specific line number to the list associated with the fileName.
		fileMap.get(fileName).add(lineNumber);
		
	}
	
	/**
	 * Retrieves the set of unique filenames in which this word has appeared.
	 * @return A Set of filenames (the keys of the fileMap).
	 */
	public Set<String> getFileNames(){
		
		return fileMap.keySet();
		
	}
	
	/**
	 * Retrieves the list of line numbers where the word was found in a specific file.
	 * @param fileName The file to query.
	 * @return A List of Integer line numbers for that file.
	 */
	public List<Integer> getLineNumbers(String fileName) {
		
		return fileMap.get(fileName);
		
	}
	
	/**
	 * Calculates the total frequency of the word across all tracked files.
	 * @return The total count of occurrences.
	 */
	public int getTotalFrequency() {
		
		int total = 0;
		// Sums the size of all line number lists (values) in the map.
		for (List<Integer> list : fileMap.values()) {
			total += list.size();
			}
		
		return total;
		
	}
	
	/**
	 * Compares this Word object to another for sorting in the BST.
	 * Comparison is based on the natural alphabetical ordering of the word string.
	 * @param o The other Word object to compare against.
	 * @return A negative integer, zero, or a positive integer as this object
	 * is less than, equal to, or greater than the specified object.
	 */
	
	@Override
	public int compareTo(Word o) {
		// Use the String class's natural ordering for comparison.
		return this.word.compareTo(o.word);
		
	}
	
	/**
	 * Standard toString method, returns just the word string.
	 */
	@Override
	public String toString() {
		
		return word;
		
	}
	
	/**
	 * Formats the output for the -pf flag (Print Files).
	 * Output format: Word: file1 file2 file3 ...
	 * @return The formatted string containing the word and all files it occurred in.
	 */
	public String toPFString() {
		
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append(word).append(": ");
		
		// Append all filenames, separated by a space.
		for (String file : fileMap.keySet()) {
			
			stringbuilder.append(file).append(" ");
			
		}
		
		return stringbuilder.toString().trim(); // Trim trailing space.
		
	}
	
	/**
	 * Formats the output for the -pl flag (Print Lines).
	 * Output format: Word: file1[L1, L2] file2[L3] ...
	 * Prints the word, files, and line numbers.
	 * @return The formatted string.
	 */
	
	public String toPLString() {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(word).append(": ");
		
		// Iterate through each file tracked.
		for (String file : fileMap.keySet()) {
			
			stringBuilder.append(file).append("[");
			List<Integer> lines = fileMap.get(file); // Get the list of line numbers for this file.
			
			// Append all line numbers, separated by ", ".
			for (int i = 0; i < lines.size(); i++) {
				
				stringBuilder.append(lines.get(i));
				
				if (i < lines.size() - 1) stringBuilder.append(", ");
				
			}
			
			stringBuilder.append("] "); // Close bracket and add space before the next file.
			
		}
		
		return stringBuilder.toString().trim();
		
	}
	
	/**
	 * Formats the output for the -po flag (Print Occurrences).
	 * Output format: Word: file1[L1, L2] file2[L3] (freq = N)
	 * Prints word, files, line numbers, and total frequency.
	 * @return The formatted string.
	 */
	public String toPOString() {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder .append(word).append(": ");
		
		// Iterate through each file, appending filename and line numbers (same as PL).
		for (String file : fileMap.keySet()) {
			
			stringBuilder.append(file).append("[");
			List<Integer> lines = fileMap.get(file);
			
			for (int i = 0; i< lines.size(); i++) {
				
				stringBuilder.append(lines.get(i));
				
				if (i < lines.size() - 1) stringBuilder.append(", ");
				
			}
			
			stringBuilder.append("] ");
			
		}
		
		// Append the total frequency count at the end of the line.
		stringBuilder.append("(freq = ").append(getTotalFrequency()).append(")");
		
		return stringBuilder.toString().trim();
		
	}
	
	
}