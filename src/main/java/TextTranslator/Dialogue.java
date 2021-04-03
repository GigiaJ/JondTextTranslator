package TextTranslator;
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
	
	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public String getText() {
		return text;
	}
	
	public int getRow() {
		return row;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getMinimum() {
		return minimum;
	}

	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}

	public int getTrigger() {
		return trigger;
	}

	public void setTrigger(int trigger) {
		this.trigger = trigger;
	}

	public int getTalkTime() {
		return talkTime;
	}

	public void setTalkTime(int talkTime) {
		this.talkTime = talkTime;
	}


}
