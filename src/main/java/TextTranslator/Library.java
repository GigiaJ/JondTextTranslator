package TextTranslator;

import com.jcabi.aspects.Loggable;
import org.slf4j.Marker;

import java.lang.annotation.*;
import java.util.Iterator;

public class Library {
    /**
     * Allows the assignment of an author, the purpose of a given method/variable, and whether the
     * given method or variable is undergoes direct testing
     *
     */
    @Loggable
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    public @ interface ExtraInfo {
        String Author() default "Jaggar"; //Writer of the method
        boolean UnitTested() default false; //Is the method unit tested directly
    }
}
