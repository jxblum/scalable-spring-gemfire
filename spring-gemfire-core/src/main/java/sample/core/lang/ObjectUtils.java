package sample.core.lang;

/**
 * ObjectUtils is an abstract utility class for working with java.lang.Objects.
 *
 * @author John Blum
 * @see java.lang.Object
 * @see org.springframework.util.ObjectUtils
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class ObjectUtils extends org.springframework.util.ObjectUtils {

  public static boolean equalsIgnoreNull(final Object obj1, final Object obj2) {
    return (obj1 == null ? obj2 == null : obj1.equals(obj2));
  }

}
