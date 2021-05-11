package TextTranslator.scene.character;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * A class to store dialogue
 * @author Jaggar
 *
 */
@Slf4j
@Data
public class Dialogue {

	/**
	 * The speaker of the text of dialogue
	 */
	@NonNull
	String speaker;
	/**
	 * The text in this line of dialogue
	 */
	String text;
	/**
	 * The color of the speaker tag in this line of dialogue
	 */
	String color;
	/**
	 * The minimum trigger tag required for this dialogue to execute
	 */
	int minimum;
	/**
	 * The trigger tag for this specific line of dialogue.
	 */
	int trigger;
	/**
	 * The talk time (the time in the dialogue trigger (character scene)) in which this dialogue actually executes.
	 * Useful for determining the dialogues position in the scene.
	 */
	int talkTime;
	/**
	 * The row in the excel sheet that this line of dialogue is from.
	 */
	int row;


	/**
	 * Generates a new dialogue object with all of the fields in this class as parameters.
	 */
	public Dialogue(@NonNull String speaker, String text, String color, int minimum, int trigger, int talkTime, int row) {
		this.speaker = speaker;
		this.text = text;
		this.color = color;
		this.minimum = minimum;
		this.trigger = trigger;
		this.talkTime = talkTime;
		this.row = row;
	}


}
