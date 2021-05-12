package TextTranslator.main;

public class Main {
	/**
	 * Main method to run the translation program
	 *
	 * @param args In order, the spreadsheet file followed by the English story file and then any additional language files
	 *             For better detail look at MatchFinder.ARGS
	 */
	public static void main(String[] args) {
		args = new String[]{"Test.tsv", "C:\\Users\\Jaggar\\Downloads\\englishSheet.csv", "C:\\Users\\Jaggar\\Downloads\\dialogue.mcfunction", "C:\\Users\\Jaggar\\Downloads\\eng_story.txt", "C:\\Users\\Jaggar\\Downloads\\spa_story.txt"};
		MatchFinder finder = new MatchFinder(args);
		finder.loadDialogues();
		finder.matchDialogue();
		finder.filterMatches();
		finder.saveAsSpreadSheet();
		finder.updateMCFunctionFile();
	}


}