package TextTranslator;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import static TextTranslator.Library.ExtraInfo;

/**
 * A class to be used in generating useful data from all the input files
 *
 * @author Jaggar
 */
@Slf4j
public class MatchFinder {
    private final String[] args;

    private int EXCEL_SHEET_SIZE;
    private final int OUTPUT_INNER_ARRAY_SIZE;

    private final File spreadsheetFile;
    private static ArrayList<String> englishGameText;
    @SuppressWarnings("rawtypes")
    private final ArrayList[] additionalLanguageGameTexts;
    private ArrayList<CharacterSceneMatch> dialogues, dialogueMatchList, dialogueContainList;


    /**
     * Creates a MatchFinder object from the program arguments and loads the text files associated
     * @param programArgs   The program arguments for this program
     */
    public MatchFinder(String[] programArgs) {
        args = programArgs;
        spreadsheetFile = new File(programArgs[ARGS.SPREADSHEET.getPosition()]);
        File englishLanguageFile = new File(programArgs[ARGS.ENGLISH_LANGUAGE_DUMP.getPosition()]);
        englishGameText = FileHandler.loadTextFile(englishLanguageFile);
        File[] additionalLanguageFiles = new File[programArgs.length - ARGS.EXTRA_LANGUAGES.getPosition()];
        additionalLanguageGameTexts = new ArrayList[programArgs.length - ARGS.EXTRA_LANGUAGES.getPosition()];
        for (int i = ARGS.EXTRA_LANGUAGES.getPosition(); i < programArgs.length; i++) {
            additionalLanguageFiles[i - ARGS.EXTRA_LANGUAGES.getPosition()] = new File(programArgs[i]);
            additionalLanguageGameTexts[i - ARGS.EXTRA_LANGUAGES.getPosition()] =
                    FileHandler.loadTextFile(additionalLanguageFiles[i - ARGS.EXTRA_LANGUAGES.getPosition()]);
        }
        OUTPUT_INNER_ARRAY_SIZE = 1 + 1 + 1 + 1 + additionalLanguageGameTexts.length;
        log.info("Files loaded successfully.");
    }

    /**
     * Loads all the dialogue for the program and assigns places it in this objects dialogues list and then
     * sets the size for the excel sheet.
     */
    protected void loadDialogues() {
        dialogues = CharacterSceneHandler.assignDialogueToScene(DialogueLoader.loadDialogue(spreadsheetFile));
        EXCEL_SHEET_SIZE = dialogues.size();
        log.info("Dialogues loaded successfully. Command excel sheet size is " + EXCEL_SHEET_SIZE + ".");
    }

    /**
     * Matches the dialogues to their corresponding matches in the English text using both exact matches and
     * then only contain matches
     */
    protected void matchDialogue() {
        dialogueMatchList = PermutationMatchHandler.filterPermutations(CharacterSceneMatchHandler.getAllMatchingLines(dialogues, englishGameText, true));
        dialogueContainList = PermutationMatchHandler.filterPermutations(CharacterSceneMatchHandler.getAllMatchingLines(dialogues, englishGameText, false));
        log.info("Dialogues matched successfully.");
    }

    /**
     * Removes collisions from the list containing contain matches when compared to the exact match list and then
     * adds the remainder of the contain match list to the exact match list and then filters any remaining problematic
     * scene entries from the final output
     */
    protected void filterMatches() {
        CharacterSceneMatchHandler.removeCollisions(dialogueMatchList, dialogueContainList);
        dialogueMatchList.addAll(dialogueContainList);
        PermutationMatchHandler.filterPermutations(dialogueMatchList);
        log.info("Scene matches filtered successfully.");
    }

    /**
     * Generates a globally usable output to work from containing every dialogue entry per row and all the translation
     * data needed.
     * @return  A 2d String array containing the row data, the original command text, the text that found a match,
     * the line that the match corresponded to according to its permutation match, and the same line but for the
     * extra languages.
     */
    protected String[][] generateGlobalOutput() {
        return CharacterSceneMatchHandler.translate(CharacterSceneHandler.placeCommands(new String[EXCEL_SHEET_SIZE][OUTPUT_INNER_ARRAY_SIZE], dialogues), dialogueMatchList, englishGameText, additionalLanguageGameTexts);
    }

    /**
     * Saves the generated table output as a table
     */
    protected void saveAsSpreadSheet() {
        FileHandler.save(args[ARGS.SAVE_FILE.getPosition()], FileHandler.generateOutput(generateGlobalOutput()));
    }

    /**
     * An enumeration detailing the order of program arguments
     */
    enum ARGS {
        SAVE_FILE(0), SPREADSHEET(1), ENGLISH_LANGUAGE_DUMP(2), EXTRA_LANGUAGES(3);

        private final int position;

        ARGS(int position){
            this.position = position;
        }

        /**
         * Gets position in the program arguments array that this item is at
         * @return  The program argument position
         */
        int getPosition() {
            return this.position;
        }
    }
}
