package TextTranslator.scene.character;

import TextTranslator.scene.Scene;
import TextTranslator.scene.command.TellRaw;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import static TextTranslator.utils.Library.ExtraInfo;

/**
 * A collection of dialogues for a particular character in a given instance
 * <p>
 * This collection of dialogue tends to be based on the character speaking
 * as well as the dialogue's trigger tag
 *
 * @author Jaggar
 */
public class CharacterScene extends ArrayList<TellRaw> implements Scene {

	public CharacterScene() {
	}

	public CharacterScene(List<TellRaw> list) {
		super(list);
	}

	/**
	 * Returns all the text in this objects list in order of appearance in the excel sheet
	 *
	 * @return the text in order of appearance in the commands excel sheet
	 */
	@ExtraInfo(UnitTested = true)
	@Override
	public ArrayList<String> getText() {
		ArrayList<String> text = new ArrayList<>();
		for (TellRaw dialogue : this) {
			text.add(dialogue.getText());
		}
		return text;
	}

	/**
	 * Gets the excel row for the dialogue at the index of this character scene object
	 *
	 * @param index The index of the dialogue
	 * @return The row of the dialogue at the given index
	 */
	@Override
	public int getRow(int index) {
		return this.get(index).getRow();
	}

	/**
	 * Gets the trigger tag for the scene based on the dialogue at the given index of the scene. All trigger tags
	 * should be identical.
	 * @param index        The index in this character scene associated with a dialogue
	 * @return The trigger tag for this scene
	 */
	@Override
	public int getTrigger(int index) {
		return this.get(index).getTriggerScore();
	}

	/**
	 * Removes any duplicates in the list of dialogue based on their text content and their excel row
	 */
	@ExtraInfo(UnitTested = true)
	public void removeCopies() {
		ArrayList<TellRaw> list = new ArrayList<>();
		for (TellRaw toCheck : this) {
			if (!Scene.checkContains(toCheck, list)) {
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
