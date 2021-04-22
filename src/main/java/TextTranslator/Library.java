package TextTranslator;


import java.lang.annotation.*;

public class Library {
    /**
     * Allows the assignment of an author, the purpose of a given method/variable, and whether the
     * given method or variable is undergoes direct testing
     *
     */
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    public @ interface ExtraInfo {
        String Author() default "Jaggar"; //Writer of the method
        boolean UnitTested() default false; //Is the method unit tested directly
        boolean Review() default false;//Should we go back and look at this method particularly
    }
}
