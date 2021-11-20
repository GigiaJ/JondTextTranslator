package TextTranslator.scene.command;

import lombok.Getter;
import lombok.Setter;

public enum CommandType {
    TELLRAW("tellraw", TellRaw.class), EXECUTE("execute", Generic.class),
    TP("tp", Generic.class),
    SCOREBOARD("scoreboard", Scoreboard.class), CLEAR("clear", Generic.class),
    GIVE("give", Generic.class), SCENE_DIVIDER("#----", Generic.class),
    COMMENT("#", Generic.class), BLANK("", Generic.class);

    @Setter
    @Getter
    String commandWord;
    @Setter
    @Getter
    Class<?> clazz;

    CommandType(String commandWord, Class<?> clazz) {
        this.commandWord = commandWord;
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "CommandType{" + name() + ", " +
                "commandWord='" + commandWord + '\'' +
                '}';
    }

}
