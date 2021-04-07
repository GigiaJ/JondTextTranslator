package TextTranslator;

import java.util.ArrayList;

/**
 * A class to store permutation matches and their respective matching line
 * @author Jaggar
 *
 */
public class PermutationMatch {
	String text;
	int start;
	int end;
	ArrayList<Integer> lineMatches;
	
	public PermutationMatch() {
		this.lineMatches = new ArrayList<Integer>();
	}
	
	/**
	 * Creates a permutation based on the start index to the end index of excel 
	 * sheet rows as well as stores the text for those combined lines
	 * @param text	the combined text
	 * @param start	the start of the excel rows
	 * @param end	the end of the excel rows
	 */
	public PermutationMatch(String text, int start, int end) {
		this.text = text;
		lineMatches = new ArrayList<Integer>();
		this.start = start;
		this.end = end;
		
	}
	
	/**
	 * Add the line that matches the tested permutation
	 * @param index the index of the matching line
	 */
	public void addLineMatch(int index) {
		lineMatches.add(index);
	}
	
	/**
	 * Gets the text of this permutation 
	 * @return text of the permutation
	 */
	public String getText() {
		return text;
	}	
	
	public ArrayList<Integer> getLineMatches() {
		return this.lineMatches;
	}
	
	public boolean hasLineMatches() {
		return !lineMatches.isEmpty();
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public int getSize() {
		//Ensures that we don't assume one line entries are empty
		return (end - start) + 1;
	}
	
	/**
	 * DEBUG PURPOSES ONLY
	 */
	public String toString() {
		return text;
	}
}