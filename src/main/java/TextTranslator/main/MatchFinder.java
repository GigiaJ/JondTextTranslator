package TextTranslator.main;

import TextTranslator.loading.DialogueLoader;
import TextTranslator.loading.FileHandler;
import TextTranslator.scene.LanguagesScene;
import TextTranslator.scene.character.*;
import TextTranslator.scene.command.CommandMaker;
import TextTranslator.scene.command.CommandScene;
import TextTranslator.scene.commands.Command;
import TextTranslator.scene.commands.CommandLoader;
import TextTranslator.utils.Language;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;

import static TextTranslator.utils.Library.ExtraInfo;
/**
 * A class to be used in generating useful data from all the input files
 *
 * @author Jaggar
 */
@Slf4j
@SuppressWarnings("unused")
public class MatchFinder {
    private final String[] args;

    private Format format;

    private int EXCEL_SHEET_SIZE;
    private final int OUTPUT_INNER_ARRAY_SIZE;

    private final File spreadsheetFile;
    private static ArrayList<String> mcFunctionText;
    private final File mcFunctionFile;
    private static ArrayList<String> englishNormalizedGameText;
    private static ArrayList<String> englishPlainGameText;
    private final ArrayList<ArrayList<String>> additionalLanguageGameTexts;
    private ArrayList<CharacterSceneMatch> scenes, dialogueMatchList, dialogueContainList;


    /**
     * Creates a MatchFinder object from the program arguments and loads the text files associated
     *
     * @param programArgs The program arguments for this program
     */
    public MatchFinder(String[] programArgs) {
        args = programArgs;
        spreadsheetFile = new File(programArgs[ARGS.SPREADSHEET.getPosition()]);
        mcFunctionFile = new File(programArgs[ARGS.MCFUNCTION.getPosition()]);
        File englishLanguageFile = new File(programArgs[ARGS.ENGLISH_LANGUAGE_DUMP.getPosition()]);
        mcFunctionText = FileHandler.loadTextFile(mcFunctionFile, false);
        englishNormalizedGameText = FileHandler.loadTextFile(englishLanguageFile, true);
        englishPlainGameText = FileHandler.loadTextFile(englishLanguageFile, false);
        File[] additionalLanguageFiles = new File[programArgs.length - ARGS.EXTRA_LANGUAGES.getPosition()];
        additionalLanguageGameTexts = new ArrayList<>();
        for (int i = ARGS.EXTRA_LANGUAGES.getPosition(); i < programArgs.length; i++) {
            additionalLanguageFiles[i - ARGS.EXTRA_LANGUAGES.getPosition()] = new File(programArgs[i]);
            additionalLanguageGameTexts.add(FileHandler.loadTextFile(additionalLanguageFiles[i - ARGS.EXTRA_LANGUAGES.getPosition()], false));
        }
        OUTPUT_INNER_ARRAY_SIZE = 1 + 1 + 1 + 1 + additionalLanguageGameTexts.size();
        log.info("Files loaded successfully.");
    }

    /**
     * Loads all of the commands for the program and places it in this objects command list
     */
    protected void loadCommands() {
        ArrayList<Command> dialogues = CommandLoader.loadCommands(mcFunctionFile);
        log.info("Commands loaded successfully.");
    }

    /**
     * Loads all the dialogue for the program and assigns places it in this objects dialogues list and then
     * sets the size for the excel sheet.
     */
    @ExtraInfo(UnitTested = true)
    protected void loadDialogues() {
        ArrayList<Dialogue> dialogues = DialogueLoader.loadDialogue(spreadsheetFile);
        scenes = CharacterSceneHandler.assignDialogueToScene(dialogues);
        EXCEL_SHEET_SIZE = dialogues.size();
        log.info("Dialogues loaded successfully. Command excel sheet size is " + EXCEL_SHEET_SIZE + ".");
    }

    /**
     * Matches the dialogues to their corresponding matches in the English text using both exact matches and
     * then only contain matches
     */
    @ExtraInfo(UnitTested = true)
    protected void matchDialogue() {
        dialogueMatchList = PermutationMatchHandler.filterPermutations(CharacterSceneMatchHandler.getAllMatchingLines(scenes, englishNormalizedGameText, true));
        dialogueContainList = PermutationMatchHandler.filterPermutations(CharacterSceneMatchHandler.getAllMatchingLines(scenes, englishNormalizedGameText, false));
        log.info("Dialogues matched successfully.");
    }

    /**
     * Removes collisions from the list containing contain matches when compared to the exact match list and then
     * adds the remainder of the contain match list to the exact match list and then filters any remaining problematic
     * scene entries from the final output
     */
    @ExtraInfo(UnitTested = true)
    protected void filterMatches() {
        CharacterSceneMatchHandler.removeCollisions(dialogueMatchList, dialogueContainList, false);
        dialogueMatchList.addAll(dialogueContainList);
        PermutationMatchHandler.filterPermutations(dialogueMatchList);
        log.info("Scene matches filtered successfully.");
    }

    /**
     * Returns all the languages loaded in a single array list. These have not undergone any normalization of the text
     * and thus still contain markers for dialogue separation.
     *
     * @return All of the unedited language text dumps
     */
    @ExtraInfo(UnitTested = true)
    protected ArrayList<ArrayList<String>> getPlainLanguageText() {
        ArrayList<ArrayList<String>> texts = new ArrayList<>();
        texts.add(englishPlainGameText);
        texts.addAll(additionalLanguageGameTexts);
        return texts;
    }

    /**
     * Returns the scene matches for this match finder object
     *
     * @return the scene matches
     */
    @ExtraInfo(UnitTested = true)
    protected ArrayList<CharacterSceneMatch> getSceneMatches() {
        return dialogueMatchList;
    }

    /**
     * Generates an output useful for checking the matches by creating a 2d string array
     * containing every dialogue entry per row and all the translation data needed.
     *
     * @return A 2d String array containing the row data, the original command text, the text that found a match,
     * the line that the match corresponded to according to its permutation match, and the same line but for the
     * extra languages.
     */
    @ExtraInfo(UnitTested = true)
    protected String[][] generateMatchOutput() {
        return CharacterSceneMatchHandler.translate(CharacterSceneHandler.placeCommands(new String[EXCEL_SHEET_SIZE][OUTPUT_INNER_ARRAY_SIZE], scenes), dialogueMatchList, englishPlainGameText, additionalLanguageGameTexts);
    }

    /**
     * Saves the generated table output as a table
     */
    @ExtraInfo(UnitTested = true)
    protected void saveAsSpreadSheet() {
        FileHandler.save(args[ARGS.SAVE_FILE.getPosition()], FileHandler.generateOutput(generateMatchOutput()));
    }

    /**
     * Generates a list of languages scenes which contain the multiple languages the scene matches have been
     * translated to in their command form (dialogue object)
     *
     * @return The list of language scenes
     */
    @ExtraInfo(UnitTested = true)
    public ArrayList<LanguagesScene> getTranslatedCommands() {
        ArrayList<LanguagesScene> allCommands = new ArrayList<>();
        ArrayList<ArrayList<String>> dumps = this.getPlainLanguageText();
        for (CharacterSceneMatch scene : this.getSceneMatches()) {
            LanguagesScene languagesScene = new LanguagesScene();
            for (int i = 1; i < dumps.size(); i++) {
                CommandScene sceneCommandsForLanguage = new CommandScene();
                for (int t = 0; t < scene.getPermutationMatches().size(); t++)
                    sceneCommandsForLanguage.addAll(new CommandMaker(scene, dumps.get(i), Language.values()[i]).createCommands(t));
                languagesScene.add(sceneCommandsForLanguage, Language.values()[i]);
            }
            allCommands.add(languagesScene);
        }
        return allCommands;
    }

    /**
     * Updates the entries in the passed mcfunction file with the alternate language commands
     */
    @ExtraInfo(UnitTested = true)
    public void updateMCFunctionFile() {
        String text = "";
        for (String line : mcFunctionText) {
            text += line + "\n";
        }
        ArrayList<LanguagesScene> languagesScenes = this.getTranslatedCommands();
        for (LanguagesScene languagesScene : languagesScenes) {
            for (int i = 0; i < languagesScene.get(Language.SPA).size(); i++) {
                String originalLine = languagesScene.get(Language.SPA).get(i).getOriginalLine();
                originalLine = originalLine.substring(1, originalLine.length() - 1);
                String languageLine = languagesScene.get(Language.SPA).get(i).toCommandForm();
                while (text.contains(originalLine)) {
                    text = text.replace(originalLine, languageLine);
                }
            }
        }

        FileHandler.save("Test.mcfunction", text);
    }

    /**
     * An enumeration detailing the order of program arguments
     */
    enum ARGS {
        SAVE_FILE(0), SPREADSHEET(1), MCFUNCTION(2), ENGLISH_LANGUAGE_DUMP(3), EXTRA_LANGUAGES(4);

        private final int position;

        ARGS(int position) {
            this.position = position;
        }

        /**
         * Gets position in the program arguments array that this item is at
         *
         * @return The program argument position
         */
        int getPosition() {
            return this.position;
        }
    }

    /**
     * An enumeration of the output format selections
     */
    enum Format {
        ExcelMatchCheck(0), ExcelSceneOutput(1), FunctionOutput(2);

        private final int format;

        Format(int format) {
            this.format = format;
        }

        /**
         * Gets the format selection
         *
         * @return The format this program will output in
         */
        int getFormat() {
            return this.format;
        }
    }
}
