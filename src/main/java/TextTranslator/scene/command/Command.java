package TextTranslator.scene.command;

import lombok.Data;
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
     * The command type specific values
     */
    protected String[] args;

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
    }

    protected abstract String toCommandForm();

}
