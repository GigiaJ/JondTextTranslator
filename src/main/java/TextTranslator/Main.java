package TextTranslator;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;

@Slf4j
public class Main {	
	public static int EXCEL_SHEET_SIZE = 2950;	/*Potentially will simply find this */
	public static int INNER_ARRAY_SIZE = 5; 	/*ROWTEXT, MATCHED, ENGLISHFOUND ,ALTLANGUAGEFOUND, String.valueOf(dialogue.getRow()) */
	public static String OUTPUT_FILE_NAME = "test.tsv";


	/**
	 * Main method
	 * @param args		In order, the spreadsheet file followed by the English story file and then any additional language files
	 */
	public static void main(String[] args) {
		File spreadsheetFile = new File(args[0]);
		File englishLanguageFile = new File(args[1]);
		ArrayList<String> englishGameText = FileHandler.loadTextFile(englishLanguageFile);
		File[] additionalLanguageFiles = new File[args.length-2];
		var additionalLanguageGameTexts = new ArrayList[args.length-2];
		log.info(args[0]);
		for (int i = 2; i < args.length; i++) {
			additionalLanguageFiles[i - 2] = new File(args[i]);
			additionalLanguageGameTexts[i - 2] = FileHandler.loadTextFile(additionalLanguageFiles[i - 2]);
		}

		INNER_ARRAY_SIZE = 1 + 1 + 1 + 1 + additionalLanguageGameTexts.length;

		ArrayList<CharacterScene> scenes = CharacterSceneHandler.assignDialogueToScene(DialogueLoader.loadDialogue(spreadsheetFile));

		ArrayList<CharacterSceneMatch> dialogueMatchList;
		ArrayList<CharacterSceneMatch> dialogueContainList;
		dialogueMatchList = PermutationMatchHandler.filterPermutations(CharacterSceneMatchHandler.getMatchingLines(scenes, englishGameText, true));
		dialogueContainList = PermutationMatchHandler.filterPermutations(CharacterSceneMatchHandler.getMatchingLines(scenes, englishGameText, false));
		CharacterSceneMatchHandler.removeCollisions(dialogueMatchList, dialogueContainList);
		dialogueMatchList.addAll(dialogueContainList);
		PermutationMatchHandler.filterPermutations(dialogueMatchList);
		String[][] mapText = CharacterSceneMatchHandler.translate(CharacterSceneHandler.placeCommands(new String[EXCEL_SHEET_SIZE][INNER_ARRAY_SIZE], scenes), dialogueMatchList, englishGameText, additionalLanguageGameTexts);
		
		FileHandler.save(OUTPUT_FILE_NAME, FileHandler.generateOutput(mapText));
	}
}