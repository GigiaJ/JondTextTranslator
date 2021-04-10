package TextTranslator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class to store dialogue
 * @author Jaggar
 *
 */
class Dialogue {
	String speaker;
	String text;
	int minimum;
	int trigger;
	int talkTime;
	int row;
	
	public Dialogue() {
		
	}

	public Dialogue(String speaker, String text, int minimum, int trigger, int talkTime, int row) {
		super();
		this.speaker = speaker;
		this.text = text;
		this.minimum = minimum;
		this.trigger = trigger;
		this.talkTime = talkTime;
		this.row = row;
	}
	
	/**
	 * Gets the speaker of this line of dialogue
	 * @return			the speaker of dialogue
	 */
	public String getSpeaker() {
		return speaker;
	}

	/**
	 * Sets the speaker for this line of dialogue
	 * @param speaker	the speaker of dialogue
	 */
	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	/**
	 * Gets the text for this line of dialogue
	 * @return			the text in the dialogue
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Gets the excel sheet row for this line of dialogue
	 * @return			the excel sheet row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Sets the text for this dialogue object
	 * @param text		the text for the dialogue 
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * The minimum dialogue trigger tag value
	 * @return		the minimum dialogue trigger tag value
	 */
	public int getMinimum() {
		return minimum;
	}

	/**
	 * Sets the minimum dialogue trigger tag value
	 * @param minimum		the minimum trigger tag value needed for this dialogue to trigger
	 */
	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}

	/**
	 * Gets dialogue trigger tag value
	 * @return				the trigger tag for this dialogue to be triggered
	 */
	public int getTrigger() {
		return trigger;
	}

	/**
	 * Sets the dialogue trigger tag value 
	 * @param trigger		the trigger tag for this dialogue to be triggered
	 */
	public void setTrigger(int trigger) {
		this.trigger = trigger;
	}

	/**
	 * Get the time in the dialogue trigger tag in which this dialogue should execute
	 * and the time in which the previous dialogue should be pushed up (for player readability)
	 * 
	 * @return				the talk time value to determine dialogue execute time in the scene
	 */
	public int getTalkTime() {
		return talkTime;
	}

	/**
	 * Sets the time in the dialogue trigger tag in which this dialogue should execute
	 * 
	 * @param talkTime		the talk time value to determine dialogue execute time in the scene
	 */
	public void setTalkTime(int talkTime) {
		this.talkTime = talkTime;
	}

	/**
	 * Internal use only
	 * Use toText() instead
	 * 
	 * This is simply to be used in debug view to see the dialogue object contents
	 */
	public String toString() {
		return this.text;
	}
	
	/**
	 * Gets the all lines and their values based on Jond's formatting *UNFINISHED*
	 * 
	 * @param file the file to iterate through
	 * @return a list of dialogue data
	 */
	public static ArrayList<Dialogue> getDialogue(File file) {
		ArrayList<Dialogue> dialogueList = new ArrayList<Dialogue>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			//(?:"text":")((?:(?!",")(?!"})[^}])*)		 actual pattern
			//(?:""text"":"")((?:(?!"","")(?!""})[^}])*) adjusted pattern

			Pattern textPattern = Pattern.compile("(?:\"\"text\"\":\"\")((?:(?!\"\",\"\")(?!\"\"})[^}])*)"
					);
			Pattern dialogueTalkTimePattern = Pattern.compile("score_TalkTime=(\\d*)");
			Pattern dialogueTagPattern = Pattern.compile("score_DialogueTrigger=(\\d*)");
			Pattern selectorPattern = Pattern.compile("(\"\"selector\"\":\"\"@)");
			int counter = 1;
			while ((line = br.readLine()) != null) {
				line = FileHandler.correctLine(line);

				String s = "";

				Matcher matchText = textPattern.matcher(line);
				Matcher matchTalkTime = dialogueTalkTimePattern.matcher(line);
				Matcher matchTrigger = dialogueTagPattern.matcher(line);
				Matcher matchSelector = selectorPattern.matcher(line);

				int lastStart = 0;
				while (matchText.find()) {
					String text = matchText.group(1);
					while (matchSelector.find()) {
						if (matchText.start(1) > matchSelector.start(1) && matchSelector.start(1) > lastStart) {
							s += "@s";
						}
					}
					matchSelector.reset();
					s += text;
					lastStart = matchText.start(1);
				}

				String trigger = "";
				if (matchTrigger.find()) {
					trigger = matchTrigger.group(1);
				}
				String talkTime = "";
				if (matchTalkTime.find()) {
					talkTime = matchTalkTime.group(1);
				}

				String speaker = "";
				if (s.contains("<") && s.contains(">")) {
					speaker = s.substring(s.indexOf("<") + 1, s.indexOf(">"));
				}
				s = s.replaceAll("(<.*>) ", "");

				dialogueList
						.add(new Dialogue(speaker, s, 0, Integer.valueOf(trigger), Integer.valueOf(talkTime), counter));
				counter++;
			}
			br.close();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dialogueList;
	}
	
	
}
