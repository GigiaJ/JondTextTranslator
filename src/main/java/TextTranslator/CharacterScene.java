package TextTranslator;

import java.util.ArrayList;

/**
 * A collection of dialogues for a particular character in a given instance
 * 
 * Simply used as an array list, but could store additional data later
 * 
 * @author Jaggar
 *
 */
public class CharacterScene	extends ArrayList<Dialogue> {
	public CharacterScene() {
		
	}
	
	public ArrayList<String> getText() {
		ArrayList<String> text = new ArrayList<String>();
		for (Dialogue dialogue : this) {
			text.add(dialogue.getText());
		}
		return text;
	}
	
	public int getDialogueRowStart() {
		sortEntries();
		return this.get(0).getRow();
	}
	
	public int getDialogueRowEnd() {
		sortEntries();
		return this.get(this.size()-1).getRow();
	}
	
	public void removeCopies() {
		ArrayList<Dialogue> list = new ArrayList<Dialogue>();
		for (Dialogue toCheck : this) {
			boolean listContains = false;
			for (Dialogue toCheckAgainst : list) {
				if (toCheckAgainst.getText().equals(toCheck.getText())) {
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
	
	public void sortEntries() {
		this.sort((o1, o2) -> o1.getRow() - o2.getRow());
	}
	
	/**
	 * Generated serial
	 */
	private static final long serialVersionUID = 867038322666061875L;
}
