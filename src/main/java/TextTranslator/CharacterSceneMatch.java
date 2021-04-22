package TextTranslator;

import lombok.Getter;

import java.util.ArrayList;
/**
 * A class to store the respective lines of text and their matches
 * 
 * @author Jaggar
 *
 */
public class  CharacterSceneMatch {
	@Getter
	CharacterScene scene;
	@Getter
	ArrayList<PermutationMatch> permutationMatches;

	CharacterSceneMatch() {
		permutationMatches = new ArrayList<>();
	}

	/**
	 * Adds to the list of permutations and their matches
	 * 
	 * @param match the permutation match to add
	 */
	public void addPermutationMatch(PermutationMatch match) {
		permutationMatches.add(match);
	}

	/**
	 * Removes from the list of permutations and their matches
	 * 
	 * @param match the permutation match to remove
	 */
	public void removePermutationMatch(PermutationMatch match) {
		permutationMatches.remove(match);
	}

	public void setScene(CharacterScene scene) {
		this.scene = scene;
	}
}
