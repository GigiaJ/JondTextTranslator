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
	
	
	/**
	 * Generated serial
	 */
	private static final long serialVersionUID = 867038322666061875L;
}
