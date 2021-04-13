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

	/**
	 * Sets the scene that this scene match is related to
	 * 
	 * @param scene the character scene
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
		for (int i = 0; i < sceneMatches.size(); i++) {
			// Iterate through the matches
			int longest = 0;
			// Store the longest length permutation per match
			int longestIndex = -1;
			for (int x = 0; x < sceneMatches.get(i).getMatches().size(); x++) {
				if (!sceneMatches.get(i).getMatches().get(x).getLineMatches().isEmpty()) {
					if (longest == 0) {
						// Fill the values if the longest is 0
						longest = sceneMatches.get(i).getMatches().get(x).getSize();
						longestIndex = x;
					} else {
						if (sceneMatches.get(i).getMatches().get(x).getSize() > longest) {
							// if longer than the longest
							if (sceneMatches.get(i).getMatches().get(longestIndex).getStart() == sceneMatches.get(i)
									.getMatches().get(x).getStart()) {
								// if the index starts match
								longest = sceneMatches.get(i).getMatches().get(x).getSize();
								// this is the new longest
								sceneMatches.get(i)
										.removePermutationMatch(sceneMatches.get(i).getMatches().get(longestIndex));
								// remove the old longest
								longestIndex = x - 1;
								x = x - 1;
							} else {
								// if the index starts do not match
								if (sceneMatches.get(i).getMatches().get(longestIndex).getEnd() == sceneMatches.get(i)
										.getMatches().get(x).getEnd()) {
									sceneMatches.get(i).removePermutationMatch(sceneMatches.get(i).getMatches().get(x));
									// remove this as it is smaller than the longest for the start index
									x = x - 1;
								} else {
									longest = 0;
									x = x - 1;
								}
							}
						} else {
							// if the same size or shorter than the longest
							sceneMatches.get(i).removePermutationMatch(sceneMatches.get(i).getMatches().get(x));
							// remove this as it is smaller than the longest for the start index
							x = x - 1;
						}
					}
				} else {
					sceneMatches.get(i).removePermutationMatch(sceneMatches.get(i).getMatches().get(x));
					x = x - 1;
				}
			}
			if (sceneMatches.get(i).getMatches().isEmpty()) {
				sceneMatches.remove(i);
				i = i - 1;
			}
		}
		return sceneMatches;
	}

	/**
	 * Takes a 2d string array and fills the contents within the inner array with
	 * the scene matches and their corresponding lines within the text dumps of the
	 * English text dump file and another language text dump file
	 * 
	 * Typically this will be ran after CharacterScene.placeCommands()
	 * 
	 * @param map          The 2d string array to map the values in
	 * @param sceneMatches The scenes that contain permutation matches from the
	 *                     command excel sheet
	 * @param text         The English text dump file as an array list based on line
	 *                     breaks
	 * @param altText      The other language dump file as an array list based on
	 *                     line breaks
	 * @return The 2d string array with the values collected mapped to each other in
	 *         the inner array
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
	 * Checks for collisions by iterating over <var>m1</var><var>m2</var> both lists
	 * and then performing iterations over
	 * 
	 * the match objects within that list <var>match</var> within that the
	 * permutation match list <var>pm</var> within that the line matches list
	 * <var>line</var>
	 * 
	 * Within the iterations for <var>match</var> the matches are checked to ensure
	 * empty ones are skipped
	 * 
	 * Within the iterations for <var>line</var> the lines are compared to see if
	 * there is a collision/equal value in there if so the respective
	 * <var>match</var> is then removed and the method is repeated until all the
	 * collisions are removed
	 * 
	 * @param matches1 the list to check for collisions in
	 * @param matches2 the list to remove collisions from
	 * @return the second list without collisions in it
	 */
	public static void removeCollisions(ArrayList<CharacterSceneMatch> matches1,
			ArrayList<CharacterSceneMatch> matches2) {
		for (int i = 0; i < matches1.size(); i++) {
			CharacterSceneMatch scene1 = matches1.get(i);
			for (int x = 0; x < matches2.size(); x++) {
				CharacterSceneMatch scene2 = matches2.get(x);
				if (!scene1.getMatches().isEmpty() && !scene2.getMatches().isEmpty()) {
					for (int pmx = 0; pmx < scene1.getMatches().size(); pmx++) {
						PermutationMatch pm1 = scene1.getMatches().get(pmx);
						if (!scene2.getMatches().isEmpty()) {
							for (int pmy = 0; pmy < scene2.getMatches().size(); pmy++) {
								PermutationMatch pm2 = scene2.getMatches().get(pmy);
								if (removeLineMatch(pm1, pm2)) {
									scene2.removePermutationMatch(pm2);
									//matches2.set(x, scene2);
									pmy = pmy - 1;
								}
							}
						}
					}
					if (scene1.getCharacterScene().getTrigger() == matches2.get(x).getCharacterScene().getTrigger() &&
					scene1.getCharacterScene().get(0).getRow() == scene2.getCharacterScene().get(0).getRow()) {
							scene1.getMatches().addAll(scene2.getMatches());
					}
				} else {
					continue;
				}
			}
		}
	}
	
	/**
	 * Compares the line matches from the text dumps within two permutation matches
	 * @param pm1		The first permutation match to check
	 * @param pm2		The second permutation match to check
	 * @return			True if their is a collision and the permutation match needs to be removed
	 */
	public static boolean removeLineMatch(PermutationMatch pm1, PermutationMatch pm2) {
		if (pm1.getStart() == pm2.getStart() && pm1.getEnd() == pm2.getEnd()) {
			for (Integer s1 : pm1.getLineMatches()) {
				for (Integer s2 : pm2.getLineMatches()) {
					if (s1.equals(s2) && pm1.getScene().getTrigger() == pm2.getScene().getTrigger()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 
					//return removeCollisions(matches1, matches2);
	 */
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
	 * Outputs to the console a line in a scene that does not contain a match as
	 * well as the number of them
	 * 
	 * @param sceneMatchList the list of scenes containing matches
	 */
	public static void printNonMatches(ArrayList<CharacterSceneMatch> sceneMatchList) {
		int counter = 0;
		for (CharacterSceneMatch sceneMatch : sceneMatchList) {
			if (sceneMatch.getMatches().isEmpty()) {
				for (Dialogue dialogue : sceneMatch.getCharacterScene()) {
					System.out.println(dialogue.getText());
					//counter++;
				}
				System.out.println(sceneMatch.getCharacterScene().size());
				System.out.println();
				counter++;
			}
		}
		System.out.println(counter);
	}

	/**
	 * Outputs to the console a line in a scene that does a match as well as the
	 * number of them
	 * 
	 * @param sceneMatchList the list of scenes containing matches
	 * @param text           the text dump as an array list
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
