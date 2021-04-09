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
	CharacterScene scene;
	
	public PermutationMatch() {
		this.lineMatches = new ArrayList<Integer>();
	}
	
	/**
	 * Creates a permutation based on the start index to the end index of excel 
	 * sheet rows as well as stores the text for those combined lines
	 * @param text	the combined text
	 * @param start	the starting index in the scene which this match is based from
	 * @param end	the ending index in the scene which this match is based from
	 */
	public PermutationMatch(String text, int start, int end, CharacterScene scene) {
		this.text = text;
		lineMatches = new ArrayList<Integer>();
		this.start = start;
		this.end = end;
		this.scene = scene;
		
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
	
	public CharacterScene getScene() {
		return scene;
	}
	
	/**
	 * DEBUG PURPOSES ONLY
	 */
	public String toString() {
		return text;
	}
}