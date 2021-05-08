package TextTranslator;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

/**
 * A class to store the respective lines of text and their matches
 *
 * @author Jaggar
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuppressWarnings("unused")
public class CharacterSceneMatch extends CharacterScene {
	/** The scene object associated with this match */
	CharacterScene scene;
	/** The permutation matches for the given scene */
	ArrayList<PermutationMatch> permutationMatches;

	CharacterSceneMatch(CharacterScene scene) {
		this.addAll(scene);
		this.scene = scene;
		this.permutationMatches = new ArrayList<>();
	}

	/**
	 * Adds to the list of permutations and their matches
	 *
	 * @param match the permutation match to add
	 */
	public void addPermutationMatch(PermutationMatch match) {
		this.permutationMatches.add(match);
	}

	/**
	 * Removes from the list of permutations and their matches
	 * 
	 * @param match the permutation match to remove
	 */
	public void removePermutationMatch(PermutationMatch match) {
		this.permutationMatches.remove(match);
	}


}
