package sample.core.lang;

/**
 * The Identifiable interface declares a contract for objects that can be uniquely identified by their ID.
 *
 * @author John Blum
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface Identifiable<T> {

  T getId();

  void setId(T id);

}
