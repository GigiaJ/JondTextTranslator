package TextTranslator.scene;


import TextTranslator.scene.dialogue.DialogueScene;
import TextTranslator.utils.Language;

import static TextTranslator.utils.Library.ExtraInfo;

/**
 * A class to populate with the different languages for a given scene
 */
public class LanguagesScene {
    private DialogueScene englishScenes;
    private DialogueScene spanishScenes;
    private DialogueScene germanScenes;
    private DialogueScene frenchScenes;
    private DialogueScene italianScene;
    private DialogueScene koreanScene;
    private DialogueScene japaneseScene;

    public LanguagesScene() {
    }

    /**
     * Adds a scene to the passed language's respective list
     *
     * @param scene    The scene to add
     * @param language The language to determine what list to add to
     */
    @ExtraInfo(UnitTested = true)
    public void add(DialogueScene scene, Language language) {
        switch (language) {
            case ENG -> englishScenes = scene;
            case SPA -> spanishScenes = scene;
            case GER -> germanScenes = scene;
            case FRE -> frenchScenes = scene;
            case ITA -> italianScene = scene;
            case KOR -> koreanScene = scene;
            case JPN -> japaneseScene = scene;
        }
    }

    /**
     * Gets the scene containing the commands(dialogue) for the respective language
     *
     * @param language the language to get the scene for
     * @return The scene for the language passed
     */
    @ExtraInfo(UnitTested = true)
    public DialogueScene get(Language language) {
        return switch (language) {
            case ENG -> englishScenes;
            case SPA -> spanishScenes;
            case GER -> germanScenes;
            case FRE -> frenchScenes;
            case ITA -> italianScene;
            case KOR -> koreanScene;
            case JPN -> japaneseScene;
        };
    }
}
