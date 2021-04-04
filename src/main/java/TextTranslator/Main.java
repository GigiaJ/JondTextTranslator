package TextTranslator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static int sceneCount = 0;
	public static int scenePMCount = 0;
	public static int multiLine = 0;
	public static int diaCount = 0;
	
	public static void main(String[] args) {
		File spreadSheetInput = new File("C:\\Users\\Clam\\Downloads\\englishSheet.csv");
		File engMain = new File("C:\\Users\\Clam\\Downloads\\eng_story.txt");
		File spaMain = new File("C:\\Users\\Clam\\Downloads\\spa_story.txt");

		ArrayList<CharacterSceneMatch> dialogueMatchList = new ArrayList<CharacterSceneMatch>();
		ArrayList<CharacterSceneMatch> dialogueContainList = new ArrayList<CharacterSceneMatch>();

		ArrayList<String> kalosEngMainText = loadTextFile(engMain);
		ArrayList<String> kalosSpaMainText = loadTextFile(spaMain);
		ArrayList<CharacterScene> scenes = assignDialogueToScene(getDialogue(spreadSheetInput));

		dialogueMatchList = getMatchingLines(scenes, kalosEngMainText, true);
		dialogueContainList = getMatchingLines(getNonMatchingScenes(dialogueMatchList),	kalosEngMainText, false);

		
		ArrayList<CharacterSceneMatch> foundList = removeCollisions(dialogueMatchList, filterPermutations(dialogueContainList));
		
		HashMap<String, String[]> mappedText = translate(foundList, kalosEngMainText, kalosSpaMainText);
		
		mappedText.forEach((found, translated)
                -> System.out.println("COMMAND: " + found + "\nENGLISH: "+ translated[0] + "\nSPANISH: " + translated[1] + "\n"));
		
		mappedText = translate(dialogueMatchList, kalosEngMainText, kalosSpaMainText);
		
		mappedText.forEach((found, translated)
                -> System.out.println("COMMAND: " + found + "\nENGLISH: "+ translated[0] + "\nSPANISH: " + translated[1] + "\n"));
		
		System.out.println("Multiple Lines: " + multiLine);
		System.out.println("Scene permutation count: " + scenePMCount);
		System.out.println("Scene count: " + sceneCount);
		System.out.println("Dialogue count: " + diaCount);
		// printMatches(dialogueContainList, kalosEngMainText);
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
	 * Filters the permutations to remove the shorter line matches and leave only the longest
	 * possible match as well as removing duplicates.
	 * 
	 * @param sceneMatch		the list of scenes match objects
	 * @return					the filtered list
	 */
	public static ArrayList<CharacterSceneMatch> filterPermutations(ArrayList<CharacterSceneMatch> sceneMatches) {
		for (int i = 0; i < sceneMatches.size(); i++) {	//Iterate through the matches
			ArrayList<PermutationMatch> permutationMatchList = sceneMatches.get(i).getMatches();
			int longest = 0;	//Store the longest length permutation per match
			PermutationMatch permutationMatch = new PermutationMatch();
			for (int x = 0; x < permutationMatchList.size(); x++) {
				if (!permutationMatchList.get(x).getLineMatches().isEmpty()) {
					if (longest == 0) {		//Fill the values if the longest is 0
						longest = permutationMatchList.get(x).getSize();
						permutationMatch = permutationMatchList.get(x);
					} else {
					if (permutationMatch.getStart() == permutationMatchList.get(x).getStart()) { //if the index starts match 
						if (permutationMatchList.get(x).getSize() > longest) {				//if longer than the longest
							longest = permutationMatchList.get(x).getSize();				//this is the new longest
							sceneMatches.get(i).removePermutationMatch(permutationMatch);	//remove the old longest
						} else {															//if the same size or shorter than the longest
							sceneMatches.get(i).removePermutationMatch(permutationMatchList.get(x));	//remove this as it is smaller than the longest for the start index
							x = 0;
						}
					} else {	//if the index starts do not match
						longest = 0;
					}
				}
					
				} else {
					sceneMatches.get(i).removePermutationMatch(permutationMatchList.get(x));
				}
			}
		}
		return sceneMatches;
	}

	/**
	 * Do translations here
	 */
	public static HashMap<String, String[]> translate(ArrayList<CharacterSceneMatch> sceneMatches, ArrayList<String> text, ArrayList<String> altText) {
		HashMap<String, String[]> map = new HashMap<String, String[]>();
		for (int i = 0; i < sceneMatches.size(); i++) {
			sceneCount++;
			for (PermutationMatch match : sceneMatches.get(i).getMatches()) {
				scenePMCount++;
				if (match.getLineMatches().size() > 1) {
					multiLine++;
				}
				map.put(match.getText(), new String[] {text.get(match.lineMatches.get(0)), altText.get(match.lineMatches.get(0))});
			}
			diaCount += sceneMatches.get(i).getCharacterScene().size();
		}
		return map;
	}

	/**
	 * Gets the non-matching scenes
	 * 
	 * @param dialogueMatchList		the list to check			
	 * @return						a list containing the non-matching scenes
	 */
	public static ArrayList<CharacterScene> getNonMatchingScenes(ArrayList<CharacterSceneMatch> dialogueMatchList) {
		ArrayList<CharacterScene> scenes = new ArrayList<CharacterScene>();
		for (CharacterSceneMatch dialogueMatch : dialogueMatchList) {
			if (dialogueMatch.getMatches().isEmpty()) {
				scenes.add(dialogueMatch.getCharacterScene());
			}
		}
		return scenes;
	}

	/**
	 * Prints test data
	 * 
	 * @param dialogueMatchList
	 * @param text
	 * @return
	 */
	public static void printNonMatches(ArrayList<CharacterSceneMatch> dialogueMatchList, ArrayList<String> text) {
		int counter = 0;
		for (CharacterSceneMatch dialogueMatch : dialogueMatchList) {
			if (dialogueMatch.getMatches().isEmpty()) {
				for (Dialogue dialogue : dialogueMatch.getCharacterScene()) {
					System.out.println(dialogue.getText());
				}
				System.out.println();
				counter++;

			}
		}
		System.out.println(counter);
	}

	public static void printMatches(ArrayList<CharacterSceneMatch> dialogueMatchList, ArrayList<String> text) {
		int counter = 0;
		for (CharacterSceneMatch dialogueMatch : dialogueMatchList) {
			for (PermutationMatch match : dialogueMatch.getMatches()) {
				counter++;
				System.out.println(match.text);
				for (int i : match.getLineMatches()) {
					// System.out.println(text.get(i));
				}

			}
		}
		System.out.println(counter);
	}

	/**
	 * Gets the all lines and their values based on Jond's formatting *UNFINISHED*
	 * 
	 * @param file the file to iterate through
	 * @return a list of dialogue data
	 */
	public static ArrayList<Dialogue> getDialogue(File file) {
		ArrayList<Dialogue> dialogueList = new ArrayList<Dialogue>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			Pattern textPattern = Pattern.compile("(?:\"text\"\":\"\")([^}^\"\"$]*)(?:\"\")");
			Pattern dialogueTalkTimePattern = Pattern.compile("score_TalkTime=(\\d*)");
			Pattern dialogueTagPattern = Pattern.compile("score_DialogueTrigger=(\\d*)");
			int counter = 1;
			while ((line = br.readLine()) != null) {
				line = correctLine(line);

				String s = "";

				Matcher matchText = textPattern.matcher(line);
				Matcher matchTalkTime = dialogueTalkTimePattern.matcher(line);
				Matcher matchTrigger = dialogueTagPattern.matcher(line);

				while (matchText.find()) {
					String group = matchText.group(1);
					s += group;
				}

				String trigger = "";
				if (matchTrigger.find()) {
					trigger = matchTrigger.group(1);
				}
				String talkTime = "";
				if (matchTalkTime.find()) {
					talkTime = matchTalkTime.group(1);
				}

				String speaker = "";
				if (s.contains("<") && s.contains(">")) {
					speaker = s.substring(s.indexOf("<") + 1, s.indexOf(">"));
				}
				s = s.replaceAll("(<.*>) ", "");

				dialogueList
						.add(new Dialogue(speaker, s, 0, Integer.valueOf(trigger), Integer.valueOf(talkTime), counter));
				counter++;
			}
			br.close();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dialogueList;
	}

	/**
	 * Assigns the dialogue in the dialogue list to the correct scenes and then also
	 * re-orders that scene to put the dialogue in correct order
	 * 
	 * @param dialogueList the list of dialogue to assign to scenes
	 * @return the scenes
	 */
	public static ArrayList<CharacterScene> assignDialogueToScene(ArrayList<Dialogue> dialogueList) {
		ArrayList<CharacterScene> scenes = new ArrayList<CharacterScene>();

		String currentSpeaker = "";
		int currentTalkTime = 0;
		int currentTrigger = 0;

		CharacterScene scene = new CharacterScene();
		for (Dialogue dialogue : dialogueList) {
			// Assigns these values for the first iteration through the loop
			if (currentSpeaker == "" || currentTalkTime == 0 || currentTrigger == 0) {
				currentSpeaker = dialogue.getSpeaker();
				currentTalkTime = dialogue.getTalkTime();
				currentTrigger = dialogue.getTrigger();
				scene.add(dialogue);
			}
			// If the values don't match then we're on a new scene
			if (!dialogue.getSpeaker().equals(currentSpeaker) || dialogue.getTrigger() != currentTrigger) {
				// Sorts the scene by the correct speaking lines
				Collections.sort(scene, (o1, o2) -> o1.getTalkTime() - o2.getTalkTime());
				scenes.add(scene);
				currentSpeaker = dialogue.getSpeaker();
				currentTalkTime = dialogue.getTalkTime();
				currentTrigger = dialogue.getTrigger();
				scene = new CharacterScene();
				scene.add(dialogue);
			} else {
				scene.add(dialogue);
			}
		}

		return scenes;
	}

	/**
	 * Reads the lines of a text file and returns them in an array list
	 * 
	 * @param file the file to load
	 * @return the lines of text in the text file
	 */
	private static ArrayList<String> loadTextFile(File file) {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				line = normalizeTo(line);
				lines.add(line);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines;
	}

	/**
	 * Gets all the lines in the text dump that match the permutations generated
	 * from the dialogue lines
	 * 
	 * @param dialogueList the list of dialogues
	 * @param textDump     the lines in the text file
	 * @return the list of character scenes with their potential matching lines
	 */
	private static ArrayList<CharacterSceneMatch> getMatchingLines(ArrayList<CharacterScene> scenes,
			ArrayList<String> textDump, boolean exactMatch) {
		ArrayList<CharacterSceneMatch> sceneMatchList = new ArrayList<CharacterSceneMatch>();
		// Iterate through the character scenes to find matches
		for (int i = 0; i < scenes.size(); i++) {
			CharacterScene scene = scenes.get(i);
			CharacterSceneMatch sceneMatch = new CharacterSceneMatch();
			// List of the possible permutations of this text
			ArrayList<PermutationMatch> permutationsList = combinations(scene.getText(),
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
	 * Generates all permutations from the beginning index onward. IE [The, Big,
	 * Cat] would be effectively [The, The Big, The Big Cat, Big Cat, Cat]
	 * 
	 * @param a      the strings to combine
	 * @param output all combined strings
	 * @param index  the index to start searching from
	 * @return the combined strings in the form of a permutation match
	 */
	public static ArrayList<PermutationMatch> combinations(ArrayList<String> a, ArrayList<PermutationMatch> output,
			int index) {
		if (index < a.size()) {
			String combined = "";
			for (int i = index; i < a.size(); i++) {
				String s = a.get(i);
				if (i != a.size() - 1) {
					combined += s + " ";
				} else {
					combined += s;
				}
				output.add(new PermutationMatch(combined, index, i));
			}
			return combinations(a, output, index + 1);
		}
		return output;
	}

	public static String correctLine(String line) {
		line = line.replaceAll("PokÃ©mon", "Pokémon");
		line = line.replaceAll("Pokemon", "Pokémon");
		// line = line.replaceAll("Froakie", "@s");
		while (line.contains("\\u2019")) {
			line = line.replace("\\u2019", "'");
		}
		while (line.contains("\\u266a")) {
			line = line.replace("\\u266a", "♪");
		}
		while (line.contains("\\u201c")) {
			line = line.replace("\\u201c", "“");
		}
		while (line.contains("\\u201d")) {
			line = line.replace("\\u201d", "”");
		}
		while (line.contains("\\u0020")) {
			line = line.replace("\\u0020", " ");
		}
		return line;
	}

	private static String normalizeTo(String s) {
		final String PK0 = "[VAR PKNAME(0000)]";
		final String PK1 = "[VAR PKNAME(0001)]";
		final String PK2 = "[VAR PKNAME(0002)]";
		final String PK3 = "[VAR PKNAME(0003)]";
		final String PK4 = "[VAR PKNAME(0004)]";
		final String PK5 = "[VAR PKNAME(0005)]";

		final String PKN0 = "[VAR PKNICK(0000)]";
		final String PKN1 = "[VAR PKNICK(0001)]";

		final String TN = "[VAR TRNICK(0000)]";

		// s = s.replace("Pokemon", "");

		s = s.replace(PK0, "Pokémon");
		s = s.replace(PK1, "Pokémon");
		s = s.replace(PK2, "Pokémon");
		s = s.replace(PK3, "Pokémon");
		s = s.replace(PK4, "Pokémon");
		s = s.replace(PK5, "Pokémon");
		s = s.replace(TN, "");
		s = s.replace(PKN0, "");
		s = s.replace(PKN1, "");

		return s;
	}
}
