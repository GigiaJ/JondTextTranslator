package TextTranslator;

import java.io.File;
import java.util.ArrayList;

public class Main {	
	public static int EXCEL_SHEET_SIZE = 2950;	/*Potentially will simply find this */
	public static int INNER_ARRAY_SIZE = 5; 	/*ROWTEXT, MATCHED, ENGLISHFOUND ,ALTLANGUAGEFOUND, String.valueOf(dialogue.getRow()) */
	
	public static void main(String[] args) {
		File spreadSheetInput = new File("C:\\Users\\Jaggar\\Downloads\\englishSheet.csv");
		File engMain = new File("C:\\Users\\Jaggar\\Downloads\\eng_story.txt");
		File spaMain = new File("C:\\Users\\Jaggar\\Downloads\\spa_story.txt");

		ArrayList<CharacterSceneMatch> dialogueMatchList = new ArrayList<CharacterSceneMatch>();
		ArrayList<CharacterSceneMatch> dialogueContainList = new ArrayList<CharacterSceneMatch>();

		ArrayList<String> kalosEngMainText = FileHandler.loadTextFile(engMain);
		ArrayList<String> kalosSpaMainText = FileHandler.loadTextFile(spaMain);
		ArrayList<CharacterScene> scenes = CharacterSceneHandler.assignDialogueToScene(DialogueLoader.loadDialogue(spreadSheetInput));

		dialogueMatchList = CharacterSceneMatchHandler.filterPermutations(CharacterSceneMatchHandler.getMatchingLines(scenes, kalosEngMainText, true));
		dialogueContainList = CharacterSceneMatchHandler.filterPermutations(CharacterSceneMatchHandler.getMatchingLines(scenes, kalosEngMainText, false));
		CharacterSceneMatchHandler.removeCollisions(dialogueMatchList, dialogueContainList);
		dialogueMatchList.addAll(dialogueContainList);
		CharacterSceneMatchHandler.filterPermutations(dialogueMatchList);
		String[][] mapText = CharacterSceneMatchHandler.translate(CharacterSceneHandler.placeCommands(new String[EXCEL_SHEET_SIZE][INNER_ARRAY_SIZE], scenes), dialogueMatchList, kalosEngMainText, kalosSpaMainText);
		
		FileHandler.save("test.tsv", FileHandler.generateOutput(mapText));
	}
}