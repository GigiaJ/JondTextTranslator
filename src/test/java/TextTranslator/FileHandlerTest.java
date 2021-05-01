package TextTranslator;

import org.junit.Assert;
import org.junit.Test;

public class FileHandlerTest extends FileHandler {

    @Test
    public void testNormalize() {
        String mockLine = "[VAR PKNAME(0000)] [VAR PKNAME(0001)] [VAR PKNAME(0002)] [VAR PKNAME(0003)] [VAR PKNAME(0004)]"
                + " [VAR PKNAME(0005)] [VAR PKNICK(0000)] [VAR PKNICK(0001)] [VAR TRNICK(0000)] [VAR COLOR(0000)] [VAR COLOR(0001)] [VAR COLOR(0002)]";
        String expectedLine = "Pokémon Pokémon Pokémon Pokémon Pokémon Pokémon   @s   ";
        Assert.assertEquals(expectedLine, normalize(mockLine));
    }

    @Test
    public void testCorrectLine() {
        String mockLine = "PokÃ©mon Pokemon Froakie Chespin Fennekin \\u2019 \\u266a \\u201c \\u201d \\u0020 \\u2026";
        String expectedLine = "Pokémon Pokémon @s @s @s ' ♪ “ ”   ...";
        Assert.assertEquals(expectedLine, correctLine(mockLine));
    }
}
