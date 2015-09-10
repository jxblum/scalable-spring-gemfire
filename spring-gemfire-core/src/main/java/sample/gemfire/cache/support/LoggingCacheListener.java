package sample.gemfire.cache.support;

import java.util.logging.Logger;

import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;

/**
 * LoggingCacheListener is a GemFire CacheListener implementation logging Region entry events.
 *
 * @author John Blum
 * @see com.gemstone.gemfire.cache.util.CacheListenerAdapter
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class LoggingCacheListener extends CacheListenerAdapter {

  protected final Logger log = Logger.getLogger(getClass().getName());

  @Override
  public void afterCreate(final EntryEvent event) {
    log.info(String.format("Created Entry (%1$s = %2$s) in Region (%3$s)",
      event.getKey(), event.getNewValue(), event.getRegion().getFullPath()));
  }

  @Override
  public void afterDestroy(final EntryEvent event) {
    log.info(String.format("Destroyed Entry (%1$s = %2$s) in Region (%3$s)",
      event.getKey(), event.getOldValue(), event.getRegion().getFullPath()));
  }

  @Override
  public void afterInvalidate(final EntryEvent event) {
    log.info(String.format("Invalidated Entry (%1$s = %2$s) in Region (%3$s)",
      event.getKey(), event.getOldValue(), event.getRegion().getFullPath()));
  }

  @Override
  public void afterUpdate(final EntryEvent event) {
    log.info(String.format("Updated Entry with Key (%1$s) in Region (%2$s) from (%3$s) to (%4$s)",
      event.getKey(), event.getRegion().getFullPath(), event.getOldValue(), event.getNewValue()));
  }

}
