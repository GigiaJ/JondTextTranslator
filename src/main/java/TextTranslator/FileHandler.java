package TextTranslator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {

	/**
	 * Corrects a (typically command) line to be usable in comparisons 
	 * @param line			the line to correct
	 * @return				the corrected line
	 */
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
		//while (line.contains("\\\"\"")) {
		//	line = line.replace("\\\"\"", "\"");
		//}
		return line;
	}
	
	/**
	 * Normalizes a string (typically a text dump) to be usable in comparisons
	 * @param s				The string to normalize for use
	 * @return				A normalized form of the string
	 */
	private static String normalize(String s) {
		final String PK0 = "[VAR PKNAME(0000)]";
		final String PK1 = "[VAR PKNAME(0001)]";
		final String PK2 = "[VAR PKNAME(0002)]";
		final String PK3 = "[VAR PKNAME(0003)]";
		final String PK4 = "[VAR PKNAME(0004)]";
		final String PK5 = "[VAR PKNAME(0005)]";

		final String PKN0 = "[VAR PKNICK(0000)]";
		final String PKN1 = "[VAR PKNICK(0001)]";

		final String TN0 = "[VAR TRNICK(0000)]";
		final String TN1 = "[VAR TRNICK(0001)]";
		
		final String TRAINER_PLACE_HOLDER = "@s";

		// s = s.replace("Pokemon", "");

		s = s.replace(PK0, "Pokémon");
		s = s.replace(PK1, "Pokémon");
		s = s.replace(PK2, "Pokémon");
		s = s.replace(PK3, "Pokémon");
		s = s.replace(PK4, "Pokémon");
		s = s.replace(PK5, "Pokémon");
		s = s.replace(TN0, TRAINER_PLACE_HOLDER);
		//s = s.replace(TN1, TRAINER_PLACE_HOLDER);
		s = s.replace(PKN0, "");
		s = s.replace(PKN1, "");

		return s;
	}
	
	/**
	 * Reads the lines of a text file and returns them in an array list
	 * 
	 * @param file the file to load
	 * @return the lines of text in the text file
	 */
	public static ArrayList<String> loadTextFile(File file) {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				line = normalize(line);
				lines.add(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
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
		for (int i = 0; i < mapText.length; i++) {
			if (mapText[i] != null) {
				output.append(mapText[i][0] + "\t" + mapText[i][1] + "\t" + mapText[i][2] + "\t" + mapText[i][3] + "\n");
			}
		}
		return output.toString();
	}
	
	/**
	 * Saves the string to a file
	 * Needed as Eclipse will not show the entire string during debug
	 * as well as useful for seeing the data in a neater manner
	 * 
	 * @param string
	 */
	public static void save(String fileName, String string) {
	    try {
	        File myObj = new File("C:\\Users\\Jaggar\\Downloads\\" + fileName);
	        if (myObj.createNewFile()) {
	          System.out.println("File created: " + myObj.getName());
	        } else {
	          System.out.println("File already exists.");
	        }
	        
	        FileWriter myWriter = new FileWriter("C:\\Users\\Jaggar\\Downloads\\" + fileName);
	        myWriter.write(string);
	        myWriter.close();
	        System.out.println("Successfully wrote to the file.");
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	}
}
