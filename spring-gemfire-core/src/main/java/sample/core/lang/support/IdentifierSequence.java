package sample.core.lang.support;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import sample.core.lang.Identifiable;

/**
 * The IdentifierSequence class is an unique identifier sequence generator.
 *
 * @author John Blum
 * @see java.lang.System#nanoTime()
 * @see java.util.UUID
 * @see java.util.concurrent.atomic.AtomicLong
 * @see sample.core.lang.Identifiable
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class IdentifierSequence {

  public static final IdentifierSequence INSTANCE = new IdentifierSequence();

  private static final AtomicLong ID_SEQUENCE = new AtomicLong(System.nanoTime());

  public synchronized Identifiable<Long> setLongId(final Identifiable<Long> identifiableObject) {
    identifiableObject.setId(System.nanoTime());
    return identifiableObject;
  }

  public Identifiable<Long> setSequentialLongId(final Identifiable<Long> identifiableObject) {
    identifiableObject.setId(ID_SEQUENCE.incrementAndGet());
    return identifiableObject;
  }

  public Identifiable<String> setStringId(final Identifiable<String> identifiableObject) {
    identifiableObject.setId(UUID.randomUUID().toString());
    return identifiableObject;
  }

}
