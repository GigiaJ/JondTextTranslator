package TextTranslator.scene.command;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * A class to store the text portion of TellRaws
 */
@Slf4j
@Data
public class TellRawText {
    /**
     * The speaker of the text, color of the speaker's name, and the text associated with the speaker for
     * this tellraw
     */
    private String speaker, text, color;

    /**
     * Generates a new TellRawText portion
     * @param speaker                the speaker for this line of tell raw
     * @param text                   the actual text for this line of tell raw
     * @param color                  the color of the speaker's name for this tell raw
     */
    public TellRawText(@NonNull String speaker, String text, String color) {
        this.speaker = speaker;
        this.text = text;
        this.color = color;
    }
}
