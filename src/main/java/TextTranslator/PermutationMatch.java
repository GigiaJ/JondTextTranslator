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
	
	/**
	 * Returns the matching lines for this permutation
	 * @return	the matching lines
	 */
	public ArrayList<Integer> getLineMatches() {
		return this.lineMatches;
	}
	
	/**
	 * Determines if the permutation matched any lines
	 * @return	true if the permutation matched any lines else false
	 */
	public boolean hasLineMatches() {
		return !lineMatches.isEmpty();
	}
	
	/**
	 * Gets the index in the scene where this permutation starts
	 * @return	the start index
	 */
	public int getStart() {
		return start;
	}
	
	/**
	 * Gets the index in the scene where this permutation ends
	 * @return	the end index
	 */
	public int getEnd() {
		return end;
	}
	
	/**
	 * Gets the size of this permutation based on how many entries in the scene it contains
	 * 
	 * @return  the size of the permutation
	 */
	public int getSize() {
		//Ensures that we don't assume one line entries are empty
		return (end - start) + 1;
	}
	
	/**
	 * Gets the scene associated with this permutation
	 * @return	the associated scene
	 */
	public CharacterScene getScene() {
		return scene;
	}
	
	/**
	 * Generates all permutations from the beginning index onward. IE [The, Big,
	 * Cat] would be effectively [The, The Big, The Big Cat, Big Cat, Cat]
	 * 
	 * @param a      the strings to combine
	 * @param output all combined strings
	 * @param index  the index to start searching from
	 * @return the combined strings in the form of a permutation match
	 */
	public static ArrayList<PermutationMatch> combinations(CharacterScene scene, ArrayList<PermutationMatch> output,
			int index) {
		if (index < scene.size()) {
			String combined = "";
			for (int i = index; i < scene.size(); i++) {
				String s = scene.get(i).getText();
				if (i == index) {
					combined += s;
				} else {
					combined += " " + s;
				}
				output.add(new PermutationMatch(combined, index, i, scene));
			}
			return combinations(scene, output, index + 1);
		}
		return output;
	}
	
	/**
	 * DEBUG PURPOSES ONLY
	 * (Useful for seeing the contents of a permutation match in debug view)
	 */
	public String toString() {
		return getSize() + text;
	}
}