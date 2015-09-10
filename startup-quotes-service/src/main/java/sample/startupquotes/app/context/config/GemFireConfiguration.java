package sample.startupquotes.app.context.config;

import java.util.Properties;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.RegionAttributes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.RegionAttributesFactoryBean;
import org.springframework.data.gemfire.support.AnnotationBasedExpiration;

import sample.startupquotes.domain.Quote;

/**
 * GemFireConfiguration is a Spring @Configuration class for configuring and bootstrapping GemFire.
 *
 * @author John Blum
 * @see org.springframework.context.annotation.Bean
 * @see org.springframework.context.annotation.Configuration
 * @see com.gemstone.gemfire.cache.Cache
 * @since 1.0.0
 */
@Configuration
@SuppressWarnings("unused")
public class GemFireConfiguration {

  @Bean
  public Properties gemfireProperties() {
    Properties gemfireProperties = new Properties();

    gemfireProperties.setProperty("name", "StartupQuotesGemFireService");
    gemfireProperties.setProperty("mcast-port", "0");
    gemfireProperties.setProperty("log-level", "warning");
//    gemfireProperties.setProperty("locators", "localhost[11235]");
//    gemfireProperties.setProperty("jmx-manager", "true");
//    gemfireProperties.setProperty("jmx-manager-port", "1199");
//    gemfireProperties.setProperty("jmx-manager-start", "true");
//    gemfireProperties.setProperty("start-locator", "localhost[11235]");

    return gemfireProperties;
  }

  @Bean
  public CacheFactoryBean gemfireCache() {
    CacheFactoryBean cacheFactoryBean = new CacheFactoryBean();

    cacheFactoryBean.setProperties(gemfireProperties());

    return cacheFactoryBean;
  }

  @Bean(name = "Quotes")
  public PartitionedRegionFactoryBean<Object, Quote> quotes(Cache gemfireCache,
      RegionAttributes<Object, Quote> quotesRegionAttributes) {

    PartitionedRegionFactoryBean<Object, Quote> regionFactoryBean = new PartitionedRegionFactoryBean<>();

    regionFactoryBean.setAttributes(quotesRegionAttributes);
    regionFactoryBean.setCache(gemfireCache);
    regionFactoryBean.setName("Quotes");

    return regionFactoryBean;
  }

  @Bean
  @SuppressWarnings("unchecked")
  public RegionAttributesFactoryBean quotesRegionAttributes(
      @Value("${app.gemfire.default.region.initial-capacity:0.75}") int initialCapacity,
      @Value("${app.gemfire.default.region.load-factor:101}") float loadFactor) {

    RegionAttributesFactoryBean quotesRegionAttributes = new RegionAttributesFactoryBean();

    quotesRegionAttributes.setCustomEntryTimeToLive(AnnotationBasedExpiration.forTimeToLive());
    quotesRegionAttributes.setInitialCapacity(initialCapacity);
    quotesRegionAttributes.setLoadFactor(loadFactor);
    quotesRegionAttributes.setKeyConstraint(Object.class);
    quotesRegionAttributes.setValueConstraint(Quote.class);

    return quotesRegionAttributes;
  }

}
