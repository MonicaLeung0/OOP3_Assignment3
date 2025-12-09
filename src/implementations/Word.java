package implementations;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.*;

/**
 * Represents a unique word found in the text files, tracking its occurrences
 * across different files and line numbers. This object is stored as the data element
 * in the BST.
 *
 * @author  Precious, Monica, Jasmine, Mitali
 */
public class Word implements Comparable<Word>, Serializable {

	private static final long serialVersionUID = 1L;
	
	private String word;
	// Maps a filename (String) to a List of line numbers (Integer) where the word occurs in that file.
	private Map<String, List<Integer>> fileMap;
	/**
	 * Constructor for the Word object.
	 * @param word The unique word string.
	 */
	
	public Word(String word) {
		
		this.word = word;
		this.fileMap = new HashMap<>();
	
	}
	
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
		
		fileMap.putIfAbsent(fileName, new ArrayList<>());
		fileMap.get(fileName).add(lineNumber);
		
	}
	
	public Set<String> getFileNames(){
		
		return fileMap.keySet();
		
	}
	
	public List<Integer> getLineNumbers(String fileName) {
		
		return fileMap.get(fileName);
		
	}
	
	public int getTotalFrequency() {
		
		int total = 0;
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
		// TODO Auto-generated method stub
		return this.word.compareTo(o.word);
		
	}
	
	@Override
	public String toString() {
		
		return word;
		
	}
	
	public String toPFString() {
		
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append(word).append(": ");
		
		for (String file : fileMap.keySet()) {
			
			stringbuilder.append(file).append(" ");
			
		}
		
		return stringbuilder.toString().trim();
		
	}
	
	/**
	 * Formats the output for the -pf flag (Word: file1 file2...).
	 * Prints the word and the list of files it occurred in.
	 * @return The formatted string.
	 */
	
	public String toPLString() {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(word).append(": ");
		
		for (String file : fileMap.keySet()) {
			
			stringBuilder.append(file).append("[");
			List<Integer> lines = fileMap.get(file);
			
			for (int i = 0; i < lines.size(); i++) {
				
				stringBuilder.append(lines.get(i));
				
				if (i < lines.size() - 1) stringBuilder.append(", ");
				
			}
			
			stringBuilder.append("] ");
			
		}
		
		return stringBuilder.toString().trim();
		
	}
	
	public String toPOString() {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder .append(word).append(": ");
		
		for (String file : fileMap.keySet()) {
			
			stringBuilder.append(file).append("[");
			List<Integer> lines = fileMap.get(file);
			
			for (int i = 0; i< lines.size(); i++) {
				
				stringBuilder.append(lines.get(i));
				
				if (i < lines.size() - 1) stringBuilder.append(", ");
				
			}
			
			stringBuilder.append("] ");
			
		}
		
		stringBuilder.append("(freq = ").append(getTotalFrequency()).append(")");
		
		return stringBuilder.toString().trim();
		
	}
	
	
}
