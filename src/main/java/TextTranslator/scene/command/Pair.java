package TextTranslator.scene.command;

/**
 * Allows returning two objects for instance by simply using generics
 * @author Jaggar
 *
 * @param <A> first generic object
 * @param <B> second generic object
 */
public class Pair<A,B> {
    public final A a;
    public final B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getFirstObject() {
        return a;
    }

    public B getSecondObject() {
        return b;
    }
}