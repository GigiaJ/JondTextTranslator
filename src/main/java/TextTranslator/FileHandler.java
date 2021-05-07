package TextTranslator;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import static TextTranslator.Library.ExtraInfo;

/**
 * A class to handle the file interactions of the program
 */
@Slf4j
public class FileHandler {

	/**
	 * Corrects a (typically command) line to be usable in comparisons 
	 * @param line			the line to correct
	 * @return				the corrected line
	 */
	@ExtraInfo(UnitTested = true)
	public static String correctLine(String line) {
		line = line.replaceAll("PokÃ©mon", "Pokémon");
		line = line.replaceAll("Pokemon", "Pokémon");
		line = line.replaceAll("Froakie", "@s");
		line = line.replaceAll("Chespin", "@s");
		line = line.replaceAll("Fennekin", "@s");
		while (line.contains("\\u2019")) {
			line = line.replace("\\u2019", "'");
		}
		while (line.contains("\\u266a")) {
			line = line.replace("\\u266a", "♪");
		}
		while (line.contains("\\u201c")) {
			line = line.replace("\\u201c", "“");
		}
		while (line.contains("\\u201d")) {
			line = line.replace("\\u201d", "”");
		}
		while (line.contains("\\u0020")) {
			line = line.replace("\\u0020", " ");
		}
		while (line.contains("\\u2026")) {
			line = line.replace("\\u2026", "...");
		}

		return line;
	}

	/**
	 * Normalizes a string (typically a text dump) to be usable in comparisons
	 *
	 * @param s The string to normalize for use
	 * @return A normalized form of the string
	 */
	@ExtraInfo(UnitTested = true)
	protected static String normalize(String s) {
		String[] LINES_TO_CORRECT = {"[VAR PKNAME(0000)]", "[VAR PKNAME(0001)]", "[VAR PKNAME(0002)]",
				"[VAR PKNAME(0003)]", "[VAR PKNAME(0004)]", "[VAR PKNAME(0005)]"
		};

		final String PKN0 = "[VAR PKNICK(0000)]";
		final String PKN1 = "[VAR PKNICK(0001)]";

		final String TNA0 = "[VAR TRNAME(0000)]";
		final String TNA1 = "[VAR TRNAME(0001)]";
		final String TNI0 = "[VAR TRNICK(0000)]";
		final String TNI1 = "[VAR TRNICK(0001)]";


		final String CL0 = "[VAR COLOR(0000)]";
		final String CL1 = "[VAR COLOR(0001)]";
		final String CL2 = "[VAR COLOR(0002)]";

		final String RETURN = "\\r";
		final String LINE_BREAK = "\\n";
		final String DIALOGUE_BREAK = "\\c";
		final String INCORRECT_APOSTROPHE = "’";

		final String TRAINER_PLACE_HOLDER = "@s";

		StringBuilder sb = new StringBuilder(s);
		Arrays.stream(LINES_TO_CORRECT).forEach(string -> {
			while (sb.indexOf(string) != -1) {
				sb.replace(sb.indexOf(string), sb.indexOf(string) + string.length(), "Pokémon");
			}
		});
		s = sb.toString();
		while (s.contains(PKN0) || s.contains(PKN1) ||
				s.contains(TNA0) || s.contains(TNA1) ||
				s.contains(TNI0) || s.contains(TNI1) ||
				s.contains(CL0) || s.contains(CL1) || s.contains(CL2) ||
				s.contains(LINE_BREAK) || s.contains(DIALOGUE_BREAK) || s.contains(RETURN) || s.contains(INCORRECT_APOSTROPHE)
		) {
			s = s.replace(PKN0, "");
			s = s.replace(PKN1, "");
			s = s.replace(TNA0, TRAINER_PLACE_HOLDER);
			s = s.replace(TNA1, TRAINER_PLACE_HOLDER);
			s = s.replace(TNI0, TRAINER_PLACE_HOLDER);
			s = s.replace(TNI1, TRAINER_PLACE_HOLDER);
			s = s.replace(CL0, "");
			s = s.replace(CL1, "");
			s = s.replace(CL2, "");
			s = s.replace(RETURN, "");
			s = s.replace(LINE_BREAK, " ");
			s = s.replace(DIALOGUE_BREAK, "");
			s = s.replace(INCORRECT_APOSTROPHE, "'");
		}

		return s;
	}
	
	/**
	 * Reads the lines of a text file and returns them in an array list
	 * 
	 * @param file 	the file to load
	 * @return 		the lines of text in the text file
	 */
	public static ArrayList<String> loadTextFile(File file) {
		ArrayList<String> lines = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
			String line;
			while ((line = br.readLine()) != null) {
				line = normalize(line);
				lines.add(line);
			}
			br.close();
		} catch (IOException e) {
			log.error("Failed to load the text file at :" + file.getAbsolutePath(), e);
		}
		return lines;
	}
	
	/**
	 * Creates a StringBuilder object and populates it with the contents of the inner string array in a manner
	 * easily viewable by a reader of the expected output
	 * (Should be in the format of tsv)
	 * 
	 * @param mapText			A 2D String array containing the excel sheet commands, the permutation match for that command, and the matched lines in the text dump
	 * @return					A string created in the expected output format containing the information within the mapText array
	 */
	public static String generateOutput(String[][] mapText) {
		StringBuilder output = new StringBuilder();
		for (String[] strings : mapText) {
			if (strings != null) {
				StringBuilder s = new StringBuilder();
				s.append(strings[0]).append("\t");
				for (int x = 2; x < strings.length; x++) {
					s.append(strings[x]).append("\t");
				}
				output.append(s.append("\n"));
			}
		}
		return output.toString();
	}
	
	/**
	 * Saves the given string to a file in the location of this program's execution
	 *
	 * @param fileName	the file name for the output file
	 * @param string	the string to save to the file
	 */
	public static void save(String fileName, String string) {
		try {
			File saveFile = new File(FileHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + addOutputFileExtension(fileName));
			if (saveFile.createNewFile()) {
				log.info("File created.");
			} else {
				log.info("File exists already.");
			}
			FileWriter myWriter = new FileWriter(saveFile);
			myWriter.write(string);
			myWriter.close();
			log.info("File successfully saved at: " + saveFile.getAbsolutePath());
		} catch (IOException e) {
			log.error("Error when saving the file.", e);
		}
	}

	/**
	 * Adds the file extension to the file passed
	 *
	 * @param fileName The file name
	 * @return The file name with the extension added
	 */
	public static String addOutputFileExtension(String fileName) {
		final String OUTPUT_EXTENSION = ".tsv";
		return fileName + OUTPUT_EXTENSION;
	}
}
