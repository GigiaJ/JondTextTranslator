package TextTranslator.scene.command;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Command {

    /**
     * The main target selector for this command
     */
    TargetSelector mainTargetSelector;
    /**
     * The row in the excel sheet that this line of dialogue is from.
     */
    int row;
    /**
     * The original line of this command without modification
     */
    String originalLine;
    /**
     * The type of command this object is
     */
    CommandType type;
    /**
     * The command type specific values
     */
    String[] args;

    /**
     * Generates a new command object with all of the fields in this class as parameters.
     *
     * @param row             the row from the source of this command
     * @param originalLine    the original text for this command from the source
     */
    public Command(TargetSelector mainTargetSelector, int row, String originalLine, CommandType type) {
        this.mainTargetSelector = mainTargetSelector;
        this.row = row;
        this.originalLine = originalLine;
        this.type = type;
    }

}
