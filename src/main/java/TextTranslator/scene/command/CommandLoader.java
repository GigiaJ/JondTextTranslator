package TextTranslator.scene.command;

import TextTranslator.loading.FileHandler;
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
 * A class for loading the commands efficiently
 *
 * @author Jaggar
 */
@Slf4j
public class CommandLoader {
    /**
     * Gets the all lines and their values based on Jond's formatting *UNFINISHED*
     *
     * @param file the file to iterate through
     * @return a list of mcfunction data
     */
    @ExtraInfo
    public static ArrayList<Command> loadCommands(File file) {
        ArrayList<Command> commandList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
            String line;
            int row = 1;
            while ((line = br.readLine()) != null) {
                line = FileHandler.correctLine(line);
                String text = findText(line);
                commandList.add(
                        CommandFactory.create(
                                CommandFactory.identifyCommandType(line),
                                row,
                                new TargetSelector(
                                            findDialogueTag(line),
                                            findTriggerScore(line), findMinimumTrigger(line), findMinimumTalkTime(line), findTalkTime(line),
                                        findMinimumStarterPick(line), findStarterPick(line)),
                                line,
                                findSpeaker(text), removeSpeaker(text), findColor(text),
                                findScoreboardCommand(line), findScoreboardAction(line), findPostMainTargetSelector(line))
                        );
                row++;
            }
            br.close();
        } catch (NumberFormatException | IOException e) {
            log.error("Command failed to be parsed from the line.", e);
        }
        log.info("Command loaded successfully from: " + file.toString());
        return commandList;
    }

    /**
     * ,
     * Finds the text from the command line extracted out of the input file using a regex and loops through until
     * the string to return has all of the lines of te
     *
     * @return The text for this particular command
     */
    @ExtraInfo(UnitTested = true)
    protected static String findText(String line) {
        Matcher matchText = Pattern.compile("\"text\":\"((?:(?!\",\")(?!\"})[^}])*)"
        ).matcher(line);
        Matcher matchSelector = Pattern.compile("(\"selector\":\"@)").matcher(line);
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
     * Finds the trigger score from the command line extracted out of the input file by using a
     * regex and loops through until the string to return has all of the lines of text for this command
     *
     * @param line The line of text from an input file
     * @return The trigger score for this particular command
     */
    @ExtraInfo(UnitTested = true)
    protected static int findTriggerScore(String line) {
        Matcher matchTrigger = Pattern.compile("score_DialogueTrigger=(\\d*)").matcher(line);
        return matchTrigger.find() ? Integer.parseInt(matchTrigger.group(1)) : -1;
    }

    /**
     * Finds the minimum trigger score from the command line extracted out of the input file by using a
     * regex and loops through until the string to return has all of the lines of text for this command
     *
     * @param line The line of text from an input file containing a tellraw command
     * @return The minimum trigger score for this particular command
     */
    @ExtraInfo(UnitTested = true)
    protected static int findMinimumTrigger(String line) {
        Matcher matchMinTrigger = Pattern.compile("score_DialogueTrigger_min=(\\d*)").matcher(line);
        return matchMinTrigger.find() ? Integer.parseInt(matchMinTrigger.group(1)) : -1;
    }

    /**
     * Finds the dialogue tag from the command line extracted out of the input file by using a
     * regex and loops through until the string to return has all of the lines of text for this command
     *
     * @param line The line of text from an input file
     * @return The dialogue tag for this particular command
     */
    @ExtraInfo(UnitTested = true)
    protected static String findDialogueTag(String line) {
        Matcher matchDialogueTag = Pattern.compile("tag=(\\w*\\d|\\W\\w*\\d*)").matcher(line);
        return matchDialogueTag.find() ? matchDialogueTag.group(1) : null;
    }

    /**
     * Finds the minimum talk time score from the command line extracted out of the input file by using a
     * regex and loops through until the string to return has all of the lines of text for this command
     *
     * @param line The line of text from an input file
     * @return The minimum talk time score for this particular command
     */
    @ExtraInfo(UnitTested = true)
    protected static int findMinimumTalkTime(String line) {
        Matcher matchMinTalkTime = Pattern.compile("score_TalkTime_min=(\\d*)").matcher(line);
        return matchMinTalkTime.find() ? Integer.parseInt(matchMinTalkTime.group(1)) : -1;
    }

    /**
     * Finds the talk time from the command line extracted out of the input file by using a
     * regex and loops through until the string to return has all of the lines of text for this command
     *
     * @param line The line of text from an input file
     * @return The talk time for this particular command
     */
    @ExtraInfo(UnitTested = true)
    protected static int findTalkTime(String line) {
        Matcher matchTalkTime = Pattern.compile("score_TalkTime=(\\d*)").matcher(line);
        return matchTalkTime.find() ? Integer.parseInt(matchTalkTime.group(1)) : -1;
    }

    /**
     * Finds the starter pick from the command line extracted out of the input file by using a
     * regex and loops through until the string to return has all of the lines of text for this command
     *
     * @param line The line of text from an input file
     * @return The starter pick for this particular command
     */
    @ExtraInfo(UnitTested = true)
    protected static int findStarterPick(String line) {
        Matcher matchStarterPick = Pattern.compile("score_StarterPick=(\\d*)").matcher(line);
        return matchStarterPick.find() ? Integer.parseInt(matchStarterPick.group(1)) : -1;
    }

    /**
     * Finds the starter pick from the command line extracted out of the input file by using a
     * regex and loops through until the string to return has all of the lines of text for this command
     *
     * @param line The line of text from an input file
     * @return The starter pick for this particular command
     */
    @ExtraInfo(UnitTested = true)
    protected static int findMinimumStarterPick(String line) {
        Matcher matchStarterPickMin = Pattern.compile("score_StarterPick_min=(\\d*)").matcher(line);
        return matchStarterPickMin.find() ? Integer.parseInt(matchStarterPickMin.group(1)) : -1;
    }


    /**
     * Finds the speaker within the string passed to this method found by the <method>findText</method>
     *
     * @param s The text for the dialogue
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
        Matcher matchColor = Pattern.compile("\"color\":\"((?:(?!\",\")(?!\"})[^}])*)"
        ).matcher(line);
        return matchColor.find() ? matchColor.group(1) : "";
    }

    /**
     * Finds the content following the target selector from the command line
     * extracted out of the input file by using a regex and loops through until
     * the string to return has all of the lines of text for this command
     *
     * @param line The line of text from an input file
     * @return The string following the target selector for this particular command
     */
    @ExtraInfo(UnitTested = false)
    private static String findPostMainTargetSelector(String line) {
        Matcher matchPostTargetSelector = Pattern.compile("@a\\[.*\\d{1,3}](.*)").matcher(line);
        return matchPostTargetSelector.find() ? matchPostTargetSelector.group(1) : null;
    }

    /**
     * Finds the scoreboard action from the command line extracted out of the input file by using a
     * regex and loops through until the string to return has all of the lines of text for this command
     *
     * @param line The line of text from an input file
     * @return The scoreboard target for this particular command
     */
    @ExtraInfo(UnitTested = false)
    private static String findScoreboardAction(String line) {
        Matcher matchScoreboard = Pattern.compile("scoreboard (\\w*) (\\w*) @a(?:\\[.*])(.*)").matcher(line);
        return matchScoreboard.find() ? matchScoreboard.group(2) : null;
    }

    /**
     * Finds the scoreboard target from the command line extracted out of the input file by using a
     * regex and loops through until the string to return has all of the lines of text for this command
     *
     * @param line The line of text from an input file
     * @return The scoreboard command for this particular command line
     */
    @ExtraInfo(UnitTested = false)
    private static String findScoreboardCommand(String line) {
        Matcher matchScoreboard = Pattern.compile("scoreboard (\\w*) (\\w*) @a(?:\\[.*])(.*)").matcher(line);
        return matchScoreboard.find() ? matchScoreboard.group(1) : null;
    }

}
