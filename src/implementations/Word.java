package implementations;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.*;

public class Word implements Comparable<Word>, Serializable {

	private static final long serialVersionUID = 1L;
	
	private String word;
	private Map<String, List<Integer>> fileMap;
	
	public Word(String word) {
		
		this.word = word;
		this.fileMap = new HashMap<>();
	
	}
	
	public String getWord() {
		
		return word;
		
	}
	
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
