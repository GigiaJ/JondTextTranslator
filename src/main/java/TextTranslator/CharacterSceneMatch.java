package TextTranslator;

import java.util.ArrayList;
import java.util.function.BiConsumer;

/**
 * A class to store the respective lines of text and their matches
 * 
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
	 * 
	 * @return the list of permutation matches
	 */
	public ArrayList<PermutationMatch> getMatches() {
		return this.permutationMatches;
	}

	/**
	 * Adds to the list of permutations and their matches
	 * 
	 * @param match			the permutation match to add
	 */
	public void addPermutationMatch(PermutationMatch match) {
		permutationMatches.add(match);
	}

	/**
	 * Removes from the list of permutations and their matches
	 * 
	 * @param match			the permutation match to remove
	 */
	public void removePermutationMatch(PermutationMatch match) {
		permutationMatches.remove(match);
	}

	/**
	 * Sets the scene that this scene match is related to
	 * 
	 * @param scene the scene
	 */
	public void setScene(CharacterScene scene) {
		this.scene = scene;
	}

	/**
	 * Gets the character scene that the scene match is associated with
	 * 
	 * @return the character scene
	 */
	public CharacterScene getCharacterScene() {
		return scene;
	}

	/**
	 * Filters the permutations to remove the shorter line matches and leave only
	 * the longest possible match as well as removing duplicates.
	 * 
	 * @param sceneMatch the list of scenes match objects
	 * @return the filtered list
	 */
	public static ArrayList<CharacterSceneMatch> filterPermutations(ArrayList<CharacterSceneMatch> sceneMatches) {
		for (int i = 0; i < sceneMatches.size(); i++) { // Iterate through the matches
			ArrayList<PermutationMatch> permutationMatchList = sceneMatches.get(i).getMatches();
			int longest = 0; // Store the longest length permutation per match
			PermutationMatch permutationMatch = new PermutationMatch();
			for (int x = 0; x < permutationMatchList.size(); x++) {
				if (!permutationMatchList.get(x).getLineMatches().isEmpty()) {
					if (longest == 0) { // Fill the values if the longest is 0
						longest = permutationMatchList.get(x).getSize();
						permutationMatch = permutationMatchList.get(x);
					} else {
						if (permutationMatch.getStart() == permutationMatchList.get(x).getStart()) { 
							// if the index starts match
							if (permutationMatchList.get(x).getSize() > longest) { // if longer than the longest
								longest = permutationMatchList.get(x).getSize(); // this is the new longest
								sceneMatches.get(i).removePermutationMatch(permutationMatch); // remove the old longest
							} else { // if the same size or shorter than the longest
								sceneMatches.get(i).removePermutationMatch(permutationMatchList.get(x)); 
								/* remove this as it is smaller than the longest for the start index */
								x = 0;
							}
						} else { // if the index starts do not match
							longest = 0;
						}
					}

				} else {
					sceneMatches.get(i).removePermutationMatch(permutationMatchList.get(x));
				}
			}
			if (sceneMatches.get(i).getMatches().isEmpty()) {
				sceneMatches.remove(i);
			}
		}
		return sceneMatches;
	}

	/**
	 * Takes a 2d string array and fills the contents within the inner array with the scene matches and 
	 * their corresponding lines within the text dumps of the English text dump file and another language
	 * text dump file
	 * 
	 * Typically this will be ran after CharacterScene.placeCommands()
	 * 
	 * @param map				The 2d string array to map the values in
	 * @param sceneMatches		The scenes that contain permutation matches from the command excel sheet
	 * @param text				The English text dump file as an array list based on line breaks
	 * @param altText			The other language dump file as an array list based on line breaks
	 * @return					The 2d string array with the values collected mapped to each other in the inner array
	 */
	public static String[][] translate(String[][] map, ArrayList<CharacterSceneMatch> sceneMatches,
			ArrayList<String> text, ArrayList<String> altText) {
		for (int i = 0; i < sceneMatches.size(); i++) {
			for (PermutationMatch match : sceneMatches.get(i).getMatches()) {
				for (int x = match.getStart(); x < match.getSize() + match.getStart(); x++) {
					int row = match.getScene().get(x).getRow() - 1;
					if (match.hasLineMatches()) {
						String english = "";
						String other = "";
						for (int line : match.getLineMatches()) {
							english += text.get(line);
							other += altText.get(line);
						}
						String[] value = new String[] { map[row][0], match.getText(), english, other };
						if (map[row][1] != null && map[row][1].equals("")) {
							map[row] = value;
						} else {
							value = new String[] { map[row][0], map[row][1] + " / " + match.getText(),
									map[row][2] + " / " + english, map[row][3] + " / " + other };
							map[row] = value;

						}
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * Checks for collisions by iterating over <var>m1</var><var>m2</var>
	 * both lists and then performing iterations over
	 * 
	 * the match objects within that list	<var>match</var>
	 * within that
	 * the permutation match list <var>pm</var>
	 * within that
	 * the line matches list <var>line</var>
	 * 
	 * Within the iterations for <var>match</var> the matches are checked 
	 * to ensure empty ones are skipped
	 * 
	 * Within the iterations for <var>line</var> the lines are compared
	 * to see if there is a collision/equal value in there
	 * if so the respective <var>match</var> is then removed and
	 * the method is repeated until all the collisions are removed
	 * 
	 * @param matches1		the list to check for collisions in
	 * @param matches2		the list to remove collisions from
	 * @return				the second list without collisions in it
	 */
	public static ArrayList<CharacterSceneMatch> removeCollisions(ArrayList<CharacterSceneMatch> matches1,
			ArrayList<CharacterSceneMatch> matches2) {
	BiConsumer<ArrayList<CharacterSceneMatch>, ArrayList<CharacterSceneMatch>> remove = (m1, m2) -> {
		for (int a = 0; a < m1.size(); a++) {//Iterate through the matches in the first list
		for (int b = 0; b < m2.size(); b++) {//Iterate through the matches in the second list
		CharacterSceneMatch match1 = m1.get(a), match2 = m2.get(b); //Create an individual scene object for the matches
			if (!match1.getMatches().isEmpty() && !match2.getMatches().isEmpty()) { //Make sure the matches aren't empty
				match1.getMatches().forEach(pm1 -> pm1.getLineMatches().forEach(line1 -> { //Iterate through each permutation match and then the line matches within
				match2.getMatches().forEach(pm2 -> pm2.getLineMatches().forEach(line2 -> { //Iterate through each permutation match and then the line matches within
						if (line1.equals(line2)) { //Compare the line matches within each permutation
							m2.remove(match2); //If a match is found then remove it from the second matches list
							removeCollisions(m1, m2); //Rerun the method until all matches are removed
							return;
		}}));}));}}}};
		
		remove.accept(matches1, matches2);
		return matches2;
	}
	
	/**
	 * Gets all the lines in the text dump that match the permutations generated
	 * from the dialogue lines
	 * 
	 * @param dialogueList the list of dialogues
	 * @param textDump     the lines in the text file
	 * @return the list of character scenes with their potential matching lines
	 */
	public static ArrayList<CharacterSceneMatch> getMatchingLines(ArrayList<CharacterScene> scenes,
			ArrayList<String> textDump, boolean exactMatch) {
		ArrayList<CharacterSceneMatch> sceneMatchList = new ArrayList<CharacterSceneMatch>();
		// Iterate through the character scenes to find matches
		for (int i = 0; i < scenes.size(); i++) {
			CharacterScene scene = scenes.get(i);
			CharacterSceneMatch sceneMatch = new CharacterSceneMatch();
			// List of the possible permutations of this text
			ArrayList<PermutationMatch> permutationsList = PermutationMatch.combinations(scene,
					new ArrayList<PermutationMatch>(), 0);
			// Iterate through all of the permutations
			for (int t = 0; t < permutationsList.size(); t++) {
				PermutationMatch match = permutationsList.get(t);
				// Now iterate through the English text to find lines that match the
				// permutations
				// K being the line in the text file
				for (int k = 0; k < textDump.size(); k++) {
					String textToCheck = textDump.get(k); // The line in the text dump we want to check
					String permutationToCheck = match.getText();
					if (exactMatch) {
						if (textToCheck.equals(permutationToCheck)) {
							match.addLineMatch(k);
						}
					} else {
						if (textToCheck.contains(permutationToCheck)) {
							match.addLineMatch(k);
						}
					}
				}
				if (match.hasLineMatches()) {
					sceneMatch.addPermutationMatch(match);
				}
				sceneMatch.setScene(scene);
			}
			sceneMatchList.add(sceneMatch);

		}
		return sceneMatchList;
	}

	/**
	 * Outputs to the console a line in a scene that does not contain a match as well as the number of them
	 * 
	 * @param sceneMatchList		the list of scenes containing matches			
	 */
	public static void printNonMatches(ArrayList<CharacterSceneMatch> sceneMatchList) {
		int counter = 0;
		for (CharacterSceneMatch sceneMatch : sceneMatchList) {
			if (sceneMatch.getMatches().isEmpty()) {
				for (Dialogue dialogue : sceneMatch.getCharacterScene()) {
					System.out.println(dialogue.getText());
					counter++;
				}
				System.out.println(sceneMatch.getCharacterScene().size());
				System.out.println();
				counter++;
			}
		}
		System.out.println(counter);
	}
	
	/**
	 * Outputs to the console a line in a scene that does a match as well as the number of them
	 * 
	 * @param sceneMatchList		the list of scenes containing matches		
	 * @param text					the text dump as an array list
	 */
	public static void printMatches(ArrayList<CharacterSceneMatch> sceneMatchList, ArrayList<String> text) {
		int counter = 0;
		for (CharacterSceneMatch sceneMatch : sceneMatchList) {
			for (PermutationMatch match : sceneMatch.getMatches()) {
				counter++;
				System.out.println(match.text);
				for (int i : match.getLineMatches()) {
					System.out.println(text.get(i));
				}

			}
		}
		System.out.println(counter);
	}
}
