package TextTranslator.loading;

import TextTranslator.scene.command.TargetSelector;
import TextTranslator.scene.command.TellRaw;
import TextTranslator.scene.command.TellRawText;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static TextTranslator.utils.Library.ExtraInfo;

/**
 * A class for loading the dialogue efficiently
 *
 * @author Jaggar
 */
@Slf4j
public class DialogueLoader {
    /**
     * Gets the all lines and their values based on Jond's formatting *UNFINISHED*
     *
     * @param file the file to iterate through
     * @return a list of dialogue data
     */
    @ExtraInfo
    public static ArrayList<TellRaw> loadDialogue(File file) {
        ArrayList<TellRaw> dialogueList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
            String line;
            int row = 1;
            while ((line = br.readLine()) != null) {
                String originalLine = line;
                line = FileHandler.correctLine(line);
                String text = findText(line);
                dialogueList.add(
                        new TellRaw(
                                new TellRawText(findSpeaker(text), removeSpeaker(text), findColor(text)),
                                new TargetSelector(findDialogueTag(line),
                                findTriggerScore(line), findMinimumTrigger(line), findMinimumTalkTime(line), findTalkTime(line)),
                                row,
                                originalLine.replaceAll("\"\"", "\"")
                        ));
                row++;
            }
            br.close();
        } catch (NumberFormatException | IOException e) {
            log.error("Dialogue failed to be parsed from the line." , e);
        }
        log.info("Dialogue loaded successfully from: " + file.toString());
        return dialogueList;
    }

    /**
     * Finds the text from the command line extracted out of the excel sheet using a regex and loops through until
     * the string to return has all of the lines of text for this command
     * @param line The line of text from an excel sheet containing a tellraw command
     * @return		The text for this particular command
     */
    @ExtraInfo(UnitTested = true)
    protected static String findText(String line) {
        //(?:"text":")((?:(?!",")(?!"})[^}])*)		 actual pattern
        //(?:""text"":"")((?:(?!"","")(?!""})[^}])*) adjusted pattern
        //Patterns in the excel sheet have extra quotation marks when they are read so we adjust the pattern to
        //account for this discrepancy
        Matcher matchText = Pattern.compile("\"\"text\"\":\"\"((?:(?!\"\",\"\")(?!\"\"})[^}])*)"
        ).matcher(line);
        Matcher matchSelector = Pattern.compile("(\"\"selector\"\":\"\"@)").matcher(line);
        int lastStart = 0;
        StringBuilder s = new StringBuilder();
        while (matchText.find()) {
            String text = matchText.group(1);
            while (matchSelector.find()) {
                if (matchText.start(1) > matchSelector.start(1) && matchSelector.start(1) > lastStart) {
                    s.append("@s");
                }
            }
            matchSelector.reset();
            s.append(text);
            lastStart = matchText.start(1);
        }
        return s.toString();
    }

    /**
     * Finds the trigger tag from the command line extracted out of the excel sheet by using a
     * regex and loops through until the string to return has all of the lines of text for this command
     *
     * @param line The line of text from an excel sheet containing a tellraw command
     * @return The trigger tag for this particular command
     */
    @ExtraInfo(UnitTested = true)
    protected static int findTriggerScore(String line) {
        Matcher matchTrigger = Pattern.compile("score_DialogueTrigger=(\\d*)").matcher(line);
        return matchTrigger.find() ? Integer.parseInt(matchTrigger.group(1)) : -1;
    }

    /**
     * Finds the minimum trigger score from the command line extracted out of the excel sheet by using a
     * regex and loops through until the string to return has all of the lines of text for this command
     *
     * @param line The line of text from an excel sheet containing a tellraw command
     * @return The minimum trigger score for this particular command
     */
    @ExtraInfo(UnitTested = true)
    protected static int findMinimumTrigger(String line) {
        Matcher matchMinTrigger = Pattern.compile("score_DialogueTrigger_min=(\\d*)").matcher(line);
        return matchMinTrigger.find() ? Integer.parseInt(matchMinTrigger.group(1)) : -1;
    }

    /**
     * Finds the dialogue tag from the command line extracted out of the excel sheet by using a
     * regex and loops through until the string to return has all of the lines of text for this command
     *
     * @param line The line of text from an excel sheet containing a tellraw command
     * @return The dialogue tag for this particular command
     */
    @ExtraInfo(UnitTested = true)
    protected static String findDialogueTag(String line) {
        Matcher matchDialogueTag = Pattern.compile("tag=(!Dialogue\\d*)").matcher(line);
        return matchDialogueTag.find() ? matchDialogueTag.group(1) : null;
    }

    /**
     * Finds the minimum talk time score from the command line extracted out of the excel sheet by using a
     * regex and loops through until the string to return has all of the lines of text for this command
     *
     * @param line The line of text from an excel sheet containing a tellraw command
     * @return The minimum talk time score for this particular command
     */
    @ExtraInfo(UnitTested = true)
    protected static int findMinimumTalkTime(String line) {
        Matcher matchMinTalkTime = Pattern.compile("score_TalkTime_min=(\\d*)").matcher(line);
        return matchMinTalkTime.find() ? Integer.parseInt(matchMinTalkTime.group(1)) : -1;
    }

    /**
     * Finds the talk time from the command line extracted out of the excel sheet by using a
     * regex and loops through until the string to return has all of the lines of text for this command
     *
     * @param line The line of text from an excel sheet containing a tellraw command
     * @return The talk time for this particular command
     */
    @ExtraInfo(UnitTested = true)
    protected static int findTalkTime(String line) {
        Matcher matchTalkTime = Pattern.compile("score_TalkTime=(\\d*)").matcher(line);
        return matchTalkTime.find() ? Integer.parseInt(matchTalkTime.group(1)) : -1;
    }

    /**
     * Finds the speaker within the string passed to this method found by the <method>findText</method>
     * @param s		The text for the dialogue
     * @return The speaker of the dialogue
     */
    @ExtraInfo(UnitTested = true)
    protected static String findSpeaker(String s) {
        return (s.contains("<") && s.contains(">")) ? s.substring(s.indexOf("<") + 1, s.indexOf(">")) : "";
    }

    /**
     * Removes the speaker tag from the string
     *
     * @param s The string to remove the speaker tag from
     * @return The string without any speaker tags in it
     */
    @ExtraInfo(UnitTested = true)
    protected static String removeSpeaker(String s) {
        return s.replaceAll("(<.*>) ", "");
    }

    /**
     * Finds the color in the string if it exists otherwise null
     *
     * @param line The string to find the speaker color for
     * @return The color of the speaker in this text otherwise null
     */
    @ExtraInfo(UnitTested = true)
    protected static String findColor(String line) {
        Matcher matchColor = Pattern.compile("\"\"color\"\":\"\"((?:(?!\"\",\"\")(?!\"\"})[^}])*)"
        ).matcher(line);
        return matchColor.find() ? matchColor.group(1) : null;
    }
}
