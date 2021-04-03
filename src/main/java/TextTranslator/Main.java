package TextTranslator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static net.gcardone.junidecode.Junidecode.*;


public class Main {
	
	static int countguy = 0;
	public static void main(String[] args) {
		File spreadSheetInput = new File("C:\\Users\\Jaggar\\Downloads\\englishSheet.csv");
		File engMain = new File("C:\\Users\\Jaggar\\Downloads\\eng_story.txt");
		File spaMain = new File("C:\\Users\\Jaggar\\Downloads\\spa_main.txt");
		
		ArrayList<CharacterSceneMatch> dialogueMatchList = new ArrayList<CharacterSceneMatch>();

		ArrayList<String> kalosEngMainText = loadTextFile(engMain);
		ArrayList<String> kalosSpaMainText = loadTextFile(spaMain);
		ArrayList<CharacterScene> scenes = assignDialogueToScene(getDialogue(spreadSheetInput));

		dialogueMatchList = getMatchingLines(scenes, kalosEngMainText);
		
		for (CharacterSceneMatch dialogueMatch : dialogueMatchList) {
			for (PermutationMatch match : dialogueMatch.getMatches()) {
				System.out.println(match.text);
				for (int i : match.getLineMatches()) {
					System.out.println(kalosEngMainText.get(i));
				}
				
			}
		}
		System.out.println(countguy);
	}
	
	/**
	 * Gets the all lines and their values based on Jond's formatting 
	 * *UNFINISHED*
	 * 
	 * @param file		the file to iterate through 
	 * @return			a list of dialogue data
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
				while (line.contains("\\u2019")) {
					line = line.replace("\\u2019", "'");
				}
				line = line.replaceAll("Pokémon", "Pok�mon");
				String s = "";
				
				Matcher matchText = textPattern.matcher(line);
				Matcher matchTalkTime = dialogueTalkTimePattern.matcher(line);
				Matcher matchTrigger = dialogueTagPattern.matcher(line);
				
				while (matchText.find()) {
					String group = matchText.group(1);
					s += group;	
				}
				
				String trigger = "";
				if(matchTrigger.find()) {
					trigger = matchTrigger.group(1);
				}
				String talkTime = "";
				if(matchTalkTime.find()) {
					talkTime = matchTalkTime.group(1);
				}
				
				String speaker = "";
				if (s.contains("<") && s.contains(">")) {
					speaker = s.substring(s.indexOf("<")+1, s.indexOf(">"));
				}
				s = s.replaceAll("(<.*>) ", "");
				
				dialogueList.add(new Dialogue(speaker, s, 0, Integer.valueOf(trigger), Integer.valueOf(talkTime), counter));
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
	 * Assigns the dialogue in the dialogue list to the correct scenes and then also re-orders
	 * that scene to put the dialogue in correct order
	 * 
	 * @param dialogueList		the list of dialogue to assign to scenes
	 * @return					the scenes
	 */
	public static ArrayList<CharacterScene> assignDialogueToScene(ArrayList<Dialogue> dialogueList) {
		ArrayList<CharacterScene> scenes = new ArrayList<CharacterScene>();
		
		String currentSpeaker = "";
		int currentTalkTime = 0;
		int currentTrigger = 0;
		
		CharacterScene scene = new CharacterScene();
		for (Dialogue dialogue : dialogueList) {
			//Assigns these values for the first iteration through the loop
			if (currentSpeaker == "" || currentTalkTime == 0 || currentTrigger == 0) {
				currentSpeaker = dialogue.getSpeaker();
				currentTalkTime = dialogue.getTalkTime();
				currentTrigger = dialogue.getTrigger();
				scene.add(dialogue);
			}
			//If the values don't match then we're on a new scene
			if (!dialogue.getSpeaker().equals(currentSpeaker) || dialogue.getTrigger() != currentTrigger) {
				//Sorts the scene by the correct speaking lines
				Collections.sort(scene, (o1, o2) -> o1.getTalkTime() - o2.getTalkTime());
				scenes.add(scene);
				currentSpeaker = dialogue.getSpeaker();
				currentTalkTime = dialogue.getTalkTime();
				currentTrigger = dialogue.getTrigger();
				scene = new CharacterScene();
				scene.add(dialogue);
			}
			else {
				scene.add(dialogue);
			}
		}
		
		return scenes;
	}
	
	
	/**
	 * Reads the lines of a text file and returns them in an array list
	 * @param file		the file to load
	 * @return			the lines of text in the text file
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
	 * Gets all the lines in the text dump that match the permutations generated from the dialogue lines
	 * @param dialogueList	the list of dialogues
	 * @param textDump		the lines in the text file
	 * @return				the list of character scenes with their potential matching lines
	 */
	private static ArrayList<CharacterSceneMatch> getMatchingLines(ArrayList<CharacterScene> scenes, ArrayList<String> textDump) {
		ArrayList<CharacterSceneMatch> sceneMatchList = new ArrayList<CharacterSceneMatch>();
		//Iterate through the character scenes to find matches
		for (int i = 0; i < scenes.size(); i++) {
			CharacterScene scene = scenes.get(i);
			CharacterSceneMatch sceneMatch = new CharacterSceneMatch();
			//List of the possible permutations of this text
			ArrayList<PermutationMatch> permutationsList = combinations(scene.getText(), new ArrayList<PermutationMatch>(), 0);
			//Iterate through all of the permutations
			for (int t = 0; t < permutationsList.size(); t++) {
				PermutationMatch match = permutationsList.get(t);
				//Now iterate through the English text to find lines that match the permutations
				//K being the line in the text file
				for (int k = 0; k < textDump.size(); k++) {
					String textToCheck = textDump.get(k); //The line in the text dump we want to check
					String permutationToCheck = match.getText();
					if (textToCheck.equals(permutationToCheck)) {
						match.addLineMatch(k);
						countguy = countguy+1;
					}
				}
				if (match.hasLineMatches()) {
					sceneMatch.addPermutationMatch(match);
				}
			}
			sceneMatchList.add(sceneMatch);
			
		}
		return sceneMatchList;
	}
	
	/**
	 * Generates all permutations from the beginning index onward.
	 * IE [The, Big, Cat] would be effectively [The, The Big, The Big Cat, Big Cat, Cat]
	 * 
	 * @param a 		the strings to combine
	 * @param output	all combined strings
	 * @param index		the index to start searching from
	 * @return			the combined strings in the form of a permutation match
	 */
	public static ArrayList<PermutationMatch> combinations(ArrayList<String> a, ArrayList<PermutationMatch> output, int index) {
		if (index < a.size()) {
			String combined = "";
			for (int i = index; i < a.size(); i++) {
				String s = a.get(i);
				if (i != a.size() - 1) {
					combined += s + " ";
				}
				else {
					combined += s;
				}
				output.add(new PermutationMatch(combined, index, i));
			}
			return combinations(a, output, index+1);
		}
		return output;
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

		//s = s.replace("Pokemon", "");
		
		s = s.replace(PK0, "Pok�mon");
		s = s.replace(PK1, "Pok�mon");
		s = s.replace(PK2, "Pok�mon");
		s = s.replace(PK3, "Pok�mon");
		s = s.replace(PK4, "Pok�mon");
		s = s.replace(PK5, "Pok�mon");
		s = s.replace(TN, "");
		s = s.replace(PKN0, "");
		s = s.replace(PKN1, "");
		
		return s;
	}
}





	

