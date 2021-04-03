package TextTranslator;

import java.util.ArrayList;

/**
 * A class to store the respective lines of text and their matches 
 * @author Jaggar
 *
 */
public class CharacterSceneMatch {
	CharacterScene scene;
	ArrayList<PermutationMatch> permutationMatches;
	
	CharacterSceneMatch() {
		permutationMatches = new ArrayList<PermutationMatch>();
	}
	
	
	/**
	 * Gets the list of permutation matches
	 * @return the list of permutation matches
	 */
	public ArrayList<PermutationMatch> getMatches() {
		return this.permutationMatches;
	}
	
	/**
	 * Adds to the list of permutations and their matches
	 * @param match
	 */
	public void addPermutationMatch(PermutationMatch match) {
		permutationMatches.add(match);
	}		
	
	/**
	 * Sets the scene that this scene match is related to
	 * @param scene 		the scene
	 */
	public void setScene(CharacterScene scene) {
		this.scene = scene;
	}
	
	/**
	 * Gets the character scene that the scene match is associated with
	 * @return		the character scene
	 */
	public CharacterScene getCharacterScene() {
		return scene;
	}
} 
