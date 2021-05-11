package TextTranslator.utils;

/**
 * An enumeration of languages with relevant data within
 */
@SuppressWarnings("unused")
public enum Language {
    ENG, SPA, GER, FRE, ITA, KOR, JPN;

    Language() {
    }

    public double getLanguageInformationRate() {
        return switch (this) {
            case ENG -> 1.08;
            case SPA -> 0.98;
            case GER -> 0.90;
            case FRE -> 0.99;
            case ITA -> 0.96;
            case KOR -> 0.80;
            case JPN -> 0.74;
        };
    }

    public double getLanguageSyllabicRate() {
        return switch (this) {
            case ENG -> 6.19;
            case SPA -> 7.82;
            case GER -> 5.97;
            case FRE -> 7.18;
            case ITA -> 6.99;
            case KOR -> 7.00;
            case JPN -> 7.84;
        };

    }

    public double getLanguageInformationDensity() {
        return switch (this) {
            case ENG -> 0.91;
            case SPA -> 0.63;
            case GER -> 0.79;
            case FRE -> 0.74;
            case ITA -> 0.72;
            case KOR -> 0.70;
            case JPN -> 0.49;
        };
    }

}
