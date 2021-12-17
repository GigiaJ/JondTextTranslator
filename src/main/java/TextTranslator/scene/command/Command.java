package TextTranslator.scene.command;

import TextTranslator.scene.command.dialogue.LanguageData;
import TextTranslator.utils.Language;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public abstract class Command {

    /**
     * The main target selector for this command
     */
    private TargetSelector mainTargetSelector;
    /**
     * The row in the excel sheet that this line of dialogue is from.
     */
    private int row;
    /**
     * The original line of this command without modification
     */
    private String originalLine;
    /**
     * The type of command this object is
     */
    private CommandType type;
    /**
     * The language data related to this command object
     */
    private LanguageData languageData;

    /**
     * Generates a new command object with all of the fields in this class as parameters.
     *
     * @param mainTargetSelector    the target selector block associated with this command
     * @param row                   the row from the source of this command
     * @param originalLine          the original text for this command from the source
     * @param type                  the type of this command
     */
    public Command(TargetSelector mainTargetSelector, int row, String originalLine, CommandType type) {
        this.mainTargetSelector = mainTargetSelector;
        this.row = row;
        this.originalLine = originalLine;
        this.type = type;
        this.languageData = new LanguageData();
    }

    public abstract String toCommandForm(Language language);

}
