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
	 * The dialogue tag for this object
	 */
	String dialogueTag;
	/**
	 * The minimum trigger tag required for this dialogue to execute
	 */
	int minimumTrigger;
	/**
	 * The trigger score for this specific line of dialogue.
	 */
	int triggerScore;
	/**
	 * The talk time (the time in the dialogue trigger (character scene)) in which this dialogue actually executes.
	 * Useful for determining the dialogues position in the scene.
	 */
	int talkTime;
	/**
	 * The minimum talk time for this dialogue to trigger
	 */
	int minimumTalkTime;
	/**
	 * The row in the excel sheet that this line of dialogue is from.
	 */
	int row;
	/**
	 * The original line of this command without modification
	 */
	String originalLine;

	/**
	 * Generates a new dialogue object with most of the fields in this class as parameters.
	 */
	public Dialogue(@NonNull String speaker, String text, String color, String dialogueTag, int triggerScore, int minimumTrigger, int minimumTalkTime, int talkTime, int row) {
		this.speaker = speaker;
		this.text = text;
		this.color = color;
		this.dialogueTag = dialogueTag;
		this.triggerScore = triggerScore;
		this.minimumTrigger = minimumTrigger;
		this.minimumTalkTime = minimumTalkTime;
		this.talkTime = talkTime;
		this.row = row;
		this.originalLine = null;
	}

	/**
	 * Generates a new dialogue object with all of the fields in this class as parameters.
	 */
	public Dialogue(@NonNull String speaker, String text, String color, String dialogueTag, int triggerScore, int minimumTrigger, int minimumTalkTime, int talkTime, int row, String originalLine) {
		this.speaker = speaker;
		this.text = text;
		this.color = color;
		this.dialogueTag = dialogueTag;
		this.triggerScore = triggerScore;
		this.minimumTrigger = minimumTrigger;
		this.minimumTalkTime = minimumTalkTime;
		this.talkTime = talkTime;
		this.row = row;
		this.originalLine = originalLine;
	}


}
