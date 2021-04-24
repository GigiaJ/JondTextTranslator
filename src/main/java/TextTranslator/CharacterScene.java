package TextTranslator;

import java.io.Serial;
import java.util.ArrayList;

import static TextTranslator.Library.ExtraInfo;

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

	public CharacterScene(){}
	
	/**
	 * Returns all the text in this objects list in order of appearance in the excel sheet
	 * @return the text in order of appearance in the commands excel sheet
	 */
	@ExtraInfo(UnitTested = true)
	public ArrayList<String> getText() {
		ArrayList<String> text = new ArrayList<>();
		for (Dialogue dialogue : this) {
			text.add(dialogue.getText());
		}
		return text;
	}

	/**
	 * Gets the row for the first dialogue at in this character scene object
	 *
	 * @return Gets the row for the first dialogue in this character scene object
	 */
	public int getRow() {
		return getRow(0);
	}

	/**
	 * Gets the excel row for the dialogue at the index of this character scene object
	 *
	 * @param index The index of the dialogue
	 * @return The row of the dialogue at the given index
	 */
	public int getRow(int index) {
		return this.get(index).getRow();
	}

	/**
	 * Gets the trigger tag for this scene. All dialogues in a scene contain the same trigger tag so
	 * simply grabbing the first entry in the list is fine.
	 *
	 * @return The trigger tag for this scene
	 */
	public int getTrigger() {
		return getTrigger(0);
	}

	/**
	 * Gets the trigger tag for the scene based on the dialogue at the given index of the scene. All trigger tags
	 * should be identical.
	 * @param index		The index in this character scene associated with a dialogue
	 * @return			The trigger tag for this scene
	 */
	@ExtraInfo(UnitTested = true)
	public int getTrigger(int index) {
		return this.get(index).getTrigger();
	}

	/**
	 * Removes any duplicates in the list of dialogue based on their text content and their excel row
	 */
	@ExtraInfo(UnitTested = true)
	public void removeCopies() {
		ArrayList<Dialogue> list = new ArrayList<>();
		for (Dialogue toCheck : this) {
			boolean listContains = false;
			for (Dialogue toCheckAgainst : list) {
				if (toCheckAgainst.getText().equals(toCheck.getText()) && toCheckAgainst.getRow() == toCheck.getRow()) {
					listContains = true;
					break;
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
	 * Generated serial
	 */
	@Serial
	private static final long serialVersionUID = 867038322666061875L;
}
