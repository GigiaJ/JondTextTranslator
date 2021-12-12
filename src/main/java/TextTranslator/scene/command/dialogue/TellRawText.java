package TextTranslator.scene.command.dialogue;

import lombok.NonNull;

/**
 * A class to store the text portion of TellRaws
 * @author Jaggar
 *
 * Generates a new TellRawText portion
 * @param speaker                the speaker for this line of tell raw
 * @param text                   the actual text for this line of tell raw
 * @param color                  the color of the speaker's name for this tell raw
 */
public record TellRawText(@NonNull String speaker, String text, String color) {}
