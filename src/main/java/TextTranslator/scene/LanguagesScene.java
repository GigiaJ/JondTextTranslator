package TextTranslator.scene;


import TextTranslator.scene.character.CharacterScene;
import TextTranslator.utils.Language;

import static TextTranslator.utils.Library.ExtraInfo;

/**
 * A class to populate with the different languages for a given scene
 */
public class LanguagesScene {
    private CharacterScene englishScenes;
    private CharacterScene spanishScenes;
    private CharacterScene germanScenes;
    private CharacterScene frenchScenes;
    private CharacterScene italianScene;
    private CharacterScene koreanScene;
    private CharacterScene japaneseScene;

    public LanguagesScene() {
    }

    /**
     * Adds a scene to the passed language's respective list
     *
     * @param scene    The scene to add
     * @param language The language to determine what list to add to
     */
    @ExtraInfo(UnitTested = true)
    public void add(CharacterScene scene, Language language) {
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
    public CharacterScene get(Language language) {
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
