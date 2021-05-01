package TextTranslator;

import lombok.Data;

import java.util.ArrayList;

/**
 * A class to store permutation matches and their respective matching line
 * @author Jaggar
 *
 */
@Data
public class PermutationMatch {
	/** The text of the dialogues this permutation match corresponds with in their correct order */
	String text;
	/** The starting index in list of dialogues in the scene which this match is based on */
	int start;
	/** The ending index in list of dialogues in the scene which this match is based on */
	int end;
	/** The lines in the text dump file that the this permutation match corresponds with */
	ArrayList<Integer> lineMatches;
	/** The scene that this match is based on */
	CharacterScene scene;

	/**
	 * Creates a permutation based on the start index to the end index of excel 
	 * sheet rows as well as stores the text for those combined lines
	 * @param text	the combined text
	 * @param start	the starting index in the scene which this match is based from
	 * @param end	the ending index in the scene which this match is based from
	 */
	public PermutationMatch(String text, int start, int end, CharacterScene scene) {
		this.text = text;
		lineMatches = new ArrayList<>();
		this.start = start;
		this.end = end;
		this.scene = scene;
	}

	/**
	 * Add the line that matches the tested permutation
	 * @param index the index of the matching line
	 */
	public void addLineMatch(int index) {
		if ((index != -1)) {
			lineMatches.add(index);
		}
	}

	/**
	 * Determines if the permutation matched any lines
	 * @return	true if the permutation matched any lines else false
	 */
	public boolean hasLineMatches() {
		return !lineMatches.isEmpty();
	}
	
	/**
	 * Gets the size of this permutation based on how many entries in the scene it contains
	 * Adds one to the size to ensure that single entries aren't considered empty
	 * 
	 * @return  the size of the permutation
	 */
	public int getSize() {
		return (end - start) + 1;
	}
}