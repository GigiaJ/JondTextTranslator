package TextTranslator.scene.command.dialogue;

import TextTranslator.utils.Language;
import TextTranslator.utils.Library.ExtraInfo;

/**
 * A class designed to store both language specific lines and language specific talk time values for commands
 */
public class LanguageData {

    private String englishLine, spanishLine, germanLine, frenchLine, italianLine, koreanLine, japaneseLine;

    private int englishTalkTime, spanishTalkTime, germanTalkTime, frenchTalkTime, italianTalkTime, koreanTalkTime, japaneseTalkTime;

    /**
     * Adds a line to the passed language's respective variable
     *
     * @param line    The line to add
     * @param language The language to determine what variable to add to
     */
    @ExtraInfo(UnitTested = false)
    public void addLine(String line, Language language) {
        switch (language) {
            case ENG -> englishLine = line;
            case SPA -> spanishLine = line;
            case GER -> germanLine = line;
            case FRE -> frenchLine = line;
            case ITA -> italianLine = line;
            case KOR -> koreanLine = line;
            case JPN -> japaneseLine = line;
        }
    }

    /**
     * Gets the scene containing the commands(dialogue) for the respective language
     *
     * @param language the language to get the line for
     * @return The line for the language passed
     */
    @ExtraInfo(UnitTested = false)
    public String getLine(Language language) {
        String line = "";
        switch (language) {
            case ENG -> line = englishLine;
            case SPA -> line = spanishLine;
            case GER -> line = germanLine;
            case FRE -> line = frenchLine;
            case ITA -> line = italianLine;
            case KOR -> line = koreanLine;
            case JPN -> line = japaneseLine;
        }
        return (line == null) ? "Matching line not found" : line;
    }

    /**
     * Gets the talkTime for the command based on the language passed
     *
     * @return the language specific talktime
     */
    @ExtraInfo(UnitTested = false)
    public int getTalkTime(Language language) {
        return switch (language) {
            case ENG -> englishTalkTime;
            case SPA -> spanishTalkTime;
            case GER -> germanTalkTime;
            case FRE -> frenchTalkTime;
            case ITA -> italianTalkTime;
            case KOR -> koreanTalkTime;
            case JPN -> japaneseTalkTime;
        };
    }

    /**
     * Sets the talkTime for the command based on the language passed
     */
    @ExtraInfo(UnitTested = false)
    public void setTalkTime(Language language, int talkTime) {
        switch (language) {
            case ENG -> englishTalkTime = talkTime;
            case SPA -> spanishTalkTime = talkTime;
            case GER -> germanTalkTime = talkTime;
            case FRE -> frenchTalkTime = talkTime;
            case ITA -> italianTalkTime = talkTime;
            case KOR -> koreanTalkTime = talkTime;
            case JPN -> japaneseTalkTime = talkTime;
        }
    }


    /**
     * Retrieves the character count of the given language's line
     *
     * @return The character count for the language's line
     */
    @ExtraInfo(UnitTested = false)
    public int getCharacterCount(Language language) {
        return getLine(language).toCharArray().length;
    }
}
