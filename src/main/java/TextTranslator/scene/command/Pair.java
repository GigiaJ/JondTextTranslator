package TextTranslator.scene.command;

/**
 * A record designed to hold a pair of information
 * @param <A>   The type of the first object
 * @param <B>   The type of the second object
 */
public record Pair<A, B>(A a, B b){}