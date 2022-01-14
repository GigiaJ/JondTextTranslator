package TextTranslator.scene.command.dialogue;

import TextTranslator.scene.command.Pair;
import TextTranslator.utils.Language;
import TextTranslator.utils.Library.ExtraInfo;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * A class designed to store both language specific lines and language specific talk time values for commands
 */
public class LanguageData {

    public static final String NO_MATCH = "Matching line not found";
    private static final int NO_VALUE = -1;

    private final Hashtable<Language, ArrayList<Pair<String, Integer>>> linesAndTalkTimes = new Hashtable<>();

    /**
     * Adds a line to the passed language's respective variable
     *
     * @param line    The line to add
     * @param language The language to determine what variable to add to
     */
    @ExtraInfo(UnitTested = false)
    public void addLine(String line, Language language) {
        linesAndTalkTimes.putIfAbsent(language,
                new ArrayList<>());
        linesAndTalkTimes.computeIfPresent(language, (k, v) -> {
            v.add(new Pair<>(line, -1));
            return v;
        });
    }

    public Pair<String, Integer> getAndRemovePair(Language language) {
        Pair<String, Integer> pair = new Pair<>(NO_MATCH, NO_VALUE);
        if (linesAndTalkTimes.get(language) != null) {
            pair = linesAndTalkTimes.get(language).get(0);
            linesAndTalkTimes.get(language).remove(0);
        }
        return pair;
    }

    /**
     * Gets the scene containing the commands(dialogue) for the respective language
     *
     * @param language the language to get the line for
     * @return The line for the language passed
     */
    @ExtraInfo(UnitTested = false)
    public ArrayList<Pair<String, Integer>> getLines(Language language) {
        return linesAndTalkTimes.get(language);
    }

    /**
     *
     * @param language
     * @param talkTime
     * @param index
     */
    @ExtraInfo(UnitTested = false)
    public void setTalkTime(Language language, int talkTime, int index) {
        linesAndTalkTimes.computeIfPresent(language, (k, v) -> {
            Pair<String, Integer> pair = v.get(index);
            v.set(index, new Pair<>(pair.a(), talkTime));
            return v;
        });
    }


    /**
     *
     * @param line
     * @return
     */
    @ExtraInfo(UnitTested = false)
    public static int getCharacterCount(Pair<String, Integer> line) {
        return line.a().toCharArray().length;
    }
}
