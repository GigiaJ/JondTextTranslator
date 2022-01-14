package TextTranslator.scene.character;

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
	/**
	 * The permutation matches for the given scene
	 */
	ArrayList<PermutationMatch> permutationMatches;

	public CharacterSceneMatch(CharacterScene scene) {
		this.addAll(scene);
		this.permutationMatches = new ArrayList<>();
	}

	/**
	 * Checks to determine if the CharacterSceneMatch object has any matches bound to it
	 */
	public boolean hasPermutationMatches() {
		return !this.getPermutationMatches().isEmpty();
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
