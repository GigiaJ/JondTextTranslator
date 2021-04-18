package TextTranslator;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A collection of dialogues for a particular character in a given instance
 * 
 * This collection of dialogue tends to be based on the character speaking
 *  as well as the dialogue's trigger tag
 * 
 * @author Jaggar
 *
 */
public class CharacterScene	extends ArrayList<Dialogue> {
	public CharacterScene() {
		
	}
	
	/**
	 * Returns all the text in this objects list in order of appearance in the excel sheet
	 * @return		the text in order of appearance in the commands excel sheet
	 */
	public ArrayList<String> getText() {
		ArrayList<String> text = new ArrayList<String>();
		for (Dialogue dialogue : this) {
			text.add(dialogue.getText());
		}
		return text;
	}
	
	public int getTrigger() {
		return this.get(0).getTrigger();
	}
	
	/**
	 * Gets the starting row of this set of dialogue in the excel sheets
	 * NOTE: It also sorts the list of dialogue to do this.
	 * @return the starting index of the dialogue in the excel sheet
	 */
	public int getDialogueRowStart() {
		sortEntries();
		return this.get(0).getRow();
	}
	
	/**
	 * Gets the ending row of this set of dialogue in the excel sheets
	 * NOTE: It also sorts the list of dialogue to do this.
	 * @return the ending index of the dialogue in the excel sheet
	 */
	public int getDialogueRowEnd() {
		sortEntries();
		return this.get(this.size()-1).getRow();
	}
	
	/**
	 * Removes any duplicates in the list of dialogue
	 * TODO:
	 * Fix the actual duplicates with a different row being removed
	 */
	public void removeCopies() {
		ArrayList<Dialogue> list = new ArrayList<Dialogue>();
		for (Dialogue toCheck : this) {
			boolean listContains = false;
			for (Dialogue toCheckAgainst : list) {
				if (toCheckAgainst.getText().equals(toCheck.getText()) && toCheckAgainst.getRow() == toCheck.getRow()) {
					listContains = true;
				}
			}
			if (!listContains) {
				list.add(toCheck);
			}
		}
		this.clear();
		this.addAll(list);
	}
	
	/**
	 * Sorts the entries by the excel sheet row
	 */
	public void sortEntries() {
		this.sort((o1, o2) -> o1.getRow() - o2.getRow());
	}
	

	
	
	/**
	 * Generated serial
	 */
	private static final long serialVersionUID = 867038322666061875L;
}
