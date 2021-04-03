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

public class Main {
	private static final String BROKEN = "BROKEN SEGMENT DO NOT FIND THIS";
	private static final String SEPERATOR_VALUE = "~!~~!~!~!@SEPERATE~@!@!@";
	
	public static void main(String[] args) {
		File spreadSheetInput = new File("C:\\Users\\Jaggar\\Downloads\\englishSheet.csv");
		File engMain = new File("C:\\Users\\Jaggar\\Downloads\\eng_main.txt");
		File spaMain = new File("C:\\Users\\Jaggar\\Downloads\\spa_main.txt");
		
		ArrayList<DialogueMatch> dialogueList = new ArrayList<DialogueMatch>();
		ArrayList<ArrayList<String>> kalosSSE = new ArrayList<ArrayList<String>>();
		
		ArrayList<String> kalosEngMainText = new ArrayList<String>();
		ArrayList<String> kalosSpaMainText = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(spreadSheetInput));
			String line;
			int counter = 1;
			
			Pattern textPattern = Pattern.compile("(?:\"text\"\":\"\")([^}^\"\"$]*)(?:\"\")");
			Pattern dialogueTagPattern = Pattern.compile("score_DialogueTrigger=(\\d*)");
			//ArrayList<String> list = new ArrayList<String>();

			DialogueMatch dialogue = new DialogueMatch();
			String currentSpeaker = "";
			String currentTag = "";
			while ((line = br.readLine()) != null) {
				String s = "";
				
				Matcher matchText = textPattern.matcher(line);
				Matcher matchTag = dialogueTagPattern.matcher(line);
				
				while (matchText.find()) {
					String group = matchText.group(1);
					s += group;	
				}
				
				String tag = "";
				if(matchTag.find()) {
					tag = matchTag.group(1);
				}
				String speaker = "";
				if (s.contains("<") && s.contains(">")) {
					speaker = s.substring(s.indexOf("<")+1, s.indexOf(">"));
				}
				else {
					//THIS IS A NON DIALOGUE MESSAGE
				}
				s = s.replaceAll("(<.*>) ", "");
				
				if (dialogue.getSheetLineCount() == 0) {
					dialogue.addSheetLine(s);
					currentSpeaker = speaker;
					currentTag = tag;
				}
				else {
					if (!speaker.equals(currentSpeaker) || 
							Integer.valueOf(tag) != Integer.valueOf(currentTag)) {
						dialogueList.add(dialogue);
						dialogue = new DialogueMatch();
						dialogue.addSheetLine(s);
						currentSpeaker = speaker;
						currentTag = tag;
					}
					else {
						dialogue.addSheetLine(s);
					}
				}
				counter++;
			}
			br.close();
						
			kalosEngMainText = loadTextFile(engMain);
			kalosSpaMainText = loadTextFile(spaMain);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		dialogueList = getMatchingLines(dialogueList, kalosEngMainText);
		
		for (DialogueMatch dialogue : dialogueList) {
			for (PermutationMatch match : dialogue.getMatches()) {

				System.out.println(match.text);
				for (int i : match.getLineMatches()) {
					System.out.println(kalosEngMainText.get(i));
				}
				
			}
		}		
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
	 * @return				the list of dialogues with their potential matching lines
	 */
	private static ArrayList<DialogueMatch> getMatchingLines(ArrayList<DialogueMatch> dialogueList, ArrayList<String> textDump) {
		//Iterate through the dialogue list to get the dialogue combination matches
		for (int i = 0; i < dialogueList.size(); i++) {
			DialogueMatch dialogue = dialogueList.get(i);
			//List of the possible permutations of this text
			ArrayList<PermutationMatch> permutationsList = combinations(dialogue.getSheetLines(), new ArrayList<PermutationMatch>(), 0);
			//Iterate through all of the permutations
			for (int t = 0; t < permutationsList.size(); t++) {
				PermutationMatch match = permutationsList.get(t);
				//Now iterate through the English text to find lines that match the permutations
				//K being the line in the text file
				for (int k = 0; k < textDump.size(); k++) {
					String textToCheck = textDump.get(k); //The line in the text dump we want to check
					String permutationToCheck = permutationsList.get(t).getText();
					if (textToCheck.equals(permutationToCheck)) {
						permutationsList.get(t).addLineMatch(k);
					}
				}
				if (match.hasLineMatches()) {
					dialogue.addPermutationMatch(match);
				}
			}
			dialogueList.set(i, dialogue);
			
		}
		return dialogueList;
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
				combined += s + " ";
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
		
		s = s.replace(PK0, "Pokemon");
		s = s.replace(PK1, "Pokemon");
		s = s.replace(PK2, "Pokemon");
		s = s.replace(PK3, "Pokemon");
		s = s.replace(PK4, "Pokemon");
		s = s.replace(PK5, "Pokemon");
		s = s.replace(TN, "");
		s = s.replace(PKN0, "");
		s = s.replace(PKN1, "");
		
		return s;
	}
}

/**
 * A class to store permutation matches and their respective matching line
 * @author Jaggar
 *
 */
class PermutationMatch {
	String text;
	int start;
	int end;
	ArrayList<Integer> lineMatches;
	
	public PermutationMatch() {
		this.lineMatches = new ArrayList<Integer>();
	}
	
	/**
	 * Creates a permutation based on the start index to the end index of excel 
	 * sheet rows as well as stores the text for those combined lines
	 * @param text	the combined text
	 * @param start	the start of the excel rows
	 * @param end	the end of the excel rows
	 */
	public PermutationMatch(String text, int start, int end) {
		this.text = text;
		lineMatches = new ArrayList<Integer>();
		this.start = start;
		this.end = end;
		
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
	
	public ArrayList<Integer> getLineMatches() {
		return this.lineMatches;
	}
	
	public boolean hasLineMatches() {
		return !lineMatches.isEmpty();
	}
	
	/**
	 * DEBUG PURPOSES ONLY
	 */
	public String toString() {
		return text;
	}
}

/**
 * A class to store the respective lines of text and their matches 
 * @author Jaggar
 *
 */
class DialogueMatch {
	ArrayList<String> sheetLines;
	ArrayList<PermutationMatch> permutationMatches;
	int sheetLineStart;
	
	DialogueMatch() {
		sheetLines = new ArrayList<String>();
		permutationMatches = new ArrayList<PermutationMatch>();
	}
	
	/**
	 * Sets the start to the row in the excel sheet where the lines of text start
	 * @param start the row in the excel sheet
	 */
	public void setSheetLineStart(int start) {
		this.sheetLineStart = start;
	}
	
	/**
	 * Gets the starting excel row
	 * @return the start row
	 */
	public int getSheetLineStart() {
		return this.sheetLineStart;
	}
	
	/**
	 * Gets the correlating excel row
	 * @return the end row
	 */
	public int getSheetLineEnd() {
		return this.sheetLineStart + sheetLines.size();
	}
	
	/**
	 * Gets the number of text lines in this set
	 * @return the number of text lines
	 */
	public int getSheetLineCount() {
		return this.sheetLines.size();
	}
	
	/**
	 * Gets the list of excel sheet lines
	 * @return the excel sheet lines
	 */
	public ArrayList<String> getSheetLines() {
		return this.sheetLines;
	}
	
	/**
	 * Gets the list of permutation matches
	 * @return the list of permutation matches
	 */
	public ArrayList<PermutationMatch> getMatches() {
		return this.permutationMatches;
	}
	
	/**
	 * Adds a line of dialogue to the list of lines
	 * @param line
	 */
	public void addSheetLine(String line) {
		sheetLines.add(line);
	}
	
	/**
	 * Adds to the list of permutations and their matches
	 * @param match
	 */
	public void addPermutationMatch(PermutationMatch match) {
		permutationMatches.add(match);
	}		
} 
	

