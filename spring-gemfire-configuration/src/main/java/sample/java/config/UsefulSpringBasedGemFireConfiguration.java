package sample.java.config;

import java.util.Properties;
import java.util.logging.Logger;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.RegionAttributes;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.RegionAttributesFactoryBean;
import org.springframework.data.gemfire.server.CacheServerFactoryBean;

import sample.gemfire.cache.support.LoggingCacheListener;
import sample.gemfire.cache.support.TimestampCacheLoader;

/**
 * UsefulSpringBasedGemFireConfiguration is a Spring @Configuration class, Spring bean component for configuring GemFire
 * using Spring's JavaConfig.
 *
 * @author John Blum
 * @see org.springframework.context.annotation.Configuration
 * @since 1.0.0
 */
@Configuration
@SuppressWarnings("unused")
public class UsefulSpringBasedGemFireConfiguration {

  protected final Logger log = Logger.getLogger(getClass().getName());

  @Bean
  public Properties gemfireApplicationSettings() {
    Properties gemfireApplicationSettings = new Properties();

    gemfireApplicationSettings.setProperty("app.gemfire.cache.critical-heap-percentage", "0.95");
    gemfireApplicationSettings.setProperty("app.gemfire.cache.eviction-heap-percentage", "0.85");
    gemfireApplicationSettings.setProperty("app.gemfire.cache.loader.timestamp-format-pattern", "YYYY-MM-dd-HH-mm-ss");
    gemfireApplicationSettings.setProperty("app.gemfire.cache.server.bind-address", "localhost");
    gemfireApplicationSettings.setProperty("app.gemfire.cache.server.hostname-for-clients", "localhost");
    gemfireApplicationSettings.setProperty("app.gemfire.cache.server.port", "12480");
    gemfireApplicationSettings.setProperty("app.gemfire.cache.server.max-connections", "5");
    gemfireApplicationSettings.setProperty("app.gemfire.region.initial-capacity", "101");
    gemfireApplicationSettings.setProperty("app.gemfire.region.load-factor", "0.75");

    return gemfireApplicationSettings;
  }

  @Bean
  public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer(
      @Qualifier("gemfireApplicationSettings") Properties gemfireApplicationSettings) {

    PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();

    propertyPlaceholderConfigurer.setProperties(gemfireApplicationSettings);

    return propertyPlaceholderConfigurer;
  }

  @Bean
  public Properties gemfireProperties() {
    Properties gemfireProperties = new Properties();

    gemfireProperties.setProperty("name", "UsefulSpringGemFirePeerCacheMemberApplication");
    gemfireProperties.setProperty("mcast-port", "0");
    gemfireProperties.setProperty("log-level", "config");
    gemfireProperties.setProperty("locators", "localhost[11235]");
    gemfireProperties.setProperty("jmx-manager", "true");
    gemfireProperties.setProperty("jmx-manager-port", "1199");
    gemfireProperties.setProperty("jmx-manager-start", "true");
    gemfireProperties.setProperty("start-locator", "localhost[11235]");

    return gemfireProperties;
  }

  @Bean
  public CacheFactoryBean gemfireCache(
      @Value("${app.gemfire.cache.critical-heap-percentage}") float criticalHeapPercentage,
      @Value("${app.gemfire.cache.eviction-heap-percentage}") float evictionHeapPercentage) {

    CacheFactoryBean cacheFactoryBean = new CacheFactoryBean();

    cacheFactoryBean.setCriticalHeapPercentage(criticalHeapPercentage);
    cacheFactoryBean.setEvictionHeapPercentage(evictionHeapPercentage);
    cacheFactoryBean.setProperties(gemfireProperties());

    return cacheFactoryBean;
  }

  @Bean
  public CacheServerFactoryBean gemfireCacheServer(Cache gemfireCache,
      @Value("${app.gemfire.cache.server.bind-address}") String bindAddress,
      @Value("${app.gemfire.cache.server.hostname-for-clients}") String hostnameForClients,
      @Value("${app.gemfire.cache.server.port}") int port,
      @Value("${app.gemfire.cache.server.max-connections}") int maxConnections) {

    CacheServerFactoryBean cacheServerFactoryBean = new CacheServerFactoryBean();

    cacheServerFactoryBean.setCache(gemfireCache);
    cacheServerFactoryBean.setAutoStartup(true);
    cacheServerFactoryBean.setBindAddress(bindAddress);
    cacheServerFactoryBean.setHostNameForClients(hostnameForClients);
    cacheServerFactoryBean.setMaxConnections(maxConnections);
    cacheServerFactoryBean.setPort(port);

    return cacheServerFactoryBean;
  }

  @Bean(name = "Example")
  @SuppressWarnings("unchecked")
  public PartitionedRegionFactoryBean example(Cache gemfireCache, RegionAttributes regionAttributes) {
    PartitionedRegionFactoryBean regionFactoryBean = new PartitionedRegionFactoryBean();

    regionFactoryBean.setAttributes(regionAttributes);
    regionFactoryBean.setCache(gemfireCache);
    regionFactoryBean.setName("Example");
    regionFactoryBean.setPersistent(false);

    return regionFactoryBean;
  }

  @Bean
  @SuppressWarnings("unchecked")
  public RegionAttributesFactoryBean exampleRegionAttributes(TimestampCacheLoader timestampCacheLoader,
      @Value("${app.gemfire.region.initial-capacity}") int initialCapacity,
      @Value("${app.gemfire.region.load-factor}") float loadFactor) {

    RegionAttributesFactoryBean regionAttributesFactoryBean = new RegionAttributesFactoryBean();

    regionAttributesFactoryBean.setCacheLoader(timestampCacheLoader);
    regionAttributesFactoryBean.setInitialCapacity(initialCapacity);
    regionAttributesFactoryBean.setLoadFactor(loadFactor);
    regionAttributesFactoryBean.addCacheListener(loggingCacheListener());

    return regionAttributesFactoryBean;
  }

  @Bean
  public LoggingCacheListener loggingCacheListener() {
    return new LoggingCacheListener();
  }

  @Bean
  public TimestampCacheLoader timestampCacheLoader(
      @Value("${app.gemfire.cache.loader.timestamp-format-pattern}") String timestampFormatPattern) {

    TimestampCacheLoader cacheLoader = new TimestampCacheLoader();

    cacheLoader.setTimestampFormatPattern(timestampFormatPattern);

    return cacheLoader;
  }

}
