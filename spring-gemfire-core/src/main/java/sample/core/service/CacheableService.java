package sample.core.service;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * CacheableService is an abstract class that supports Cacheable services and keeping state related to caching.
 *
 * @author John Blum
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class CacheableService {

  private final AtomicBoolean cacheMiss = new AtomicBoolean(false);

  public boolean isCacheHit() {
    return !isCacheMiss();
  }

  public boolean isCacheMiss() {
    return cacheMiss.getAndSet(false);
  }

  protected void setCacheMiss() {
    cacheMiss.set(true);
  }

}
