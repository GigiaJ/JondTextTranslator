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
	 * The scene object associated with this match
	 */
	CharacterScene scene;
	/**
	 * The permutation matches for the given scene
	 */
	ArrayList<PermutationMatch> permutationMatches;

	public CharacterSceneMatch(CharacterScene scene) {
		this.addAll(scene);
		this.scene = scene;
		this.permutationMatches = new ArrayList<>();
	}

	/**
	 * Absorbs a tellraw at a respective index (indexOfCSM1 or indexOfCSM2) from one of the passed CharacterSceneMatch
	 * based on whether the second CharacterSceneMatch contains permutation matches.
	 *
	 * @param csm1			The CharacterSceneMatch to add the respective scene (indexOfCSM1) from on a matchless csm2
	 * @param csm2			The CharacterSceneMatch to add the respective scene (indexOfCSM2) from if match exists
	 * @param indexOfCSM1	The index of the tellraw within the scene to add from
	 * @param indexOfCSM2	The index of the tellraw within the scene to add from
	 *
	 */
	public void subsume(CharacterSceneMatch csm1, CharacterSceneMatch csm2, int indexOfCSM1, int indexOfCSM2) {
		if (csm2.hasPermutationMatches()) {
			this.add(csm2.getScene().get(indexOfCSM2));
			scene.add(csm2.getScene().get(indexOfCSM2));
			for (int i = 0; i < csm2.getPermutationMatches().size(); i++) {
				this.addPermutationMatch(csm2.getPermutationMatches().get(i));
			}
		}
		else {
			this.add(csm1.getScene().get(indexOfCSM1));
			scene.add(csm1.getScene().get(indexOfCSM1));
		}
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
