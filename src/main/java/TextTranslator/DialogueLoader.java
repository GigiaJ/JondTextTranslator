package TextTranslator;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static TextTranslator.Library.ExtraInfo;

@Slf4j
public class DialogueLoader {
    /**
     * Gets the all lines and their values based on Jond's formatting *UNFINISHED*
     *
     * @param file the file to iterate through
     * @return a list of dialogue data
     */
    @ExtraInfo
    public static ArrayList<Dialogue> loadDialogue(File file) {
        ArrayList<Dialogue> dialogueList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
            String line;
            int row = 1;
            while ((line = br.readLine()) != null) {
                line = FileHandler.correctLine(line);
                String text = findText(line);
                dialogueList.add(
                        new Dialogue(
                                findSpeaker(text), removeSpeaker(text), 0, findTriggerTag(line), findTalkTime(line), row
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
     * @param line The line of text from an excel sheet containing a tellraw command
     * @return		The trigger tag for this particular command
     */
    @ExtraInfo(UnitTested = true)
    protected static int findTriggerTag(String line) {
        Matcher matchTrigger = Pattern.compile("score_DialogueTrigger=(\\d*)").matcher(line);
        if (matchTrigger.find()) {
            return Integer.parseInt(matchTrigger.group(1));
        }
        return -1;
    }

    /**
     * Finds the talk time from the command line extracted out of the excel sheet by using a
     * regex and loops through until the string to return has all of the lines of text for this command
     * @param line The line of text from an excel sheet containing a tellraw command
     * @return		The trigger tag for this particular command
     */
    @ExtraInfo(UnitTested = true)
    protected static int findTalkTime(String line) {
        Matcher matchTalkTime = Pattern.compile("score_TalkTime=(\\d*)").matcher(line);
        if (matchTalkTime.find()) {
            return Integer.parseInt(matchTalkTime.group(1));
        }
        return -1;
    }

    /**
     * Finds the speaker within the string passed to this method found by the <method>findText</method>
     * @param s		The text for the dialogue
     * @return		The speaker of the dialogue
     */
    @ExtraInfo(UnitTested = true)
    protected static String findSpeaker(String s) {
        if (s.contains("<") && s.contains(">")) {
            return s.substring(s.indexOf("<") + 1, s.indexOf(">"));
        }
        return "";
    }

    @ExtraInfo(UnitTested = true)
    protected static String removeSpeaker(String s) {
        return s.replaceAll("(<.*>) ", "");
    }
}
