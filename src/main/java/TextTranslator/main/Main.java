package TextTranslator.main;

public class Main {
	/**
	 * Main method to run the translation program
	 *
	 * @param args In order, the spreadsheet file followed by the English story file and then any additional language files
	 *             For better detail look at MatchFinder.ARGS
	 */
	public static void main(String[] args) {
		final String USER = "Gigia";
		args = new String[]{"Test.tsv", "C:\\Users\\"+ USER +"\\Downloads\\englishSheet.csv", "C:\\Users\\"+ USER +"\\Downloads\\dialogue.mcfunction",
				"C:\\Users\\"+ USER +"\\Downloads\\eng_story.txt", "C:\\Users\\"+ USER +"\\Downloads\\spa_story.txt"};
		MatchFinder finder = new MatchFinder(args);
		finder.loadCommands();
		//finder.loadDialogues();
		finder.matchDialogue();
		finder.filterMatches();
		finder.remergeTriggerSets();
		finder.saveAsSpreadSheet();
		finder.generateMCFunctionFile();
		//finder.updateMCFunctionFile();
	}


}