package TextTranslator;

import java.util.ArrayList;

/**
 * A class to store the respective lines of text and their matches 
 * @author Jaggar
 *
 */
public class CharacterSceneMatch {
	ArrayList<String> sheetLines;
	ArrayList<PermutationMatch> permutationMatches;
	int sheetLineStart;
	
	CharacterSceneMatch() {
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
