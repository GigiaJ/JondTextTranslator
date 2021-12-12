package TextTranslator.scene.command;


public enum TargetSelectorType {
    ALL_PLAYERS("@a"), ALL_ENTITIES("@e"), NEAREST_PLAYER("@p"), RANDOM_PLAYER("@r"), SELF("@s");

    String value;

    TargetSelectorType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
