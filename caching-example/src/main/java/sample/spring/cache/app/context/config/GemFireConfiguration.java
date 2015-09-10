package sample.spring.cache.app.context.config;

import java.math.BigInteger;
import java.util.Properties;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.RegionAttributes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.RegionAttributesFactoryBean;

/**
 * GemFireConfiguration is a Spring @Configuration class used to configure and bootstrap Pivotal GemFire
 * or Apache Geode.
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

    gemfireProperties.setProperty("name", "CalculatorGemFireService");
    gemfireProperties.setProperty("mcast-port", "0");
    gemfireProperties.setProperty("log-level", "warning");

    return gemfireProperties;
  }

  @Bean
  public CacheFactoryBean gemfireCache() {
    CacheFactoryBean cacheFactoryBean = new CacheFactoryBean();

    cacheFactoryBean.setProperties(gemfireProperties());

    return cacheFactoryBean;
  }

  @Bean(name = "Calculations")
  @SuppressWarnings("unused")
  public PartitionedRegionFactoryBean calculations(Cache gemfireCache,
      RegionAttributes<BigInteger, BigInteger> calculationsRegionAttributes) {

    PartitionedRegionFactoryBean<BigInteger, BigInteger> regionFactoryBean = new PartitionedRegionFactoryBean<>();

    regionFactoryBean.setAttributes(calculationsRegionAttributes);
    regionFactoryBean.setCache(gemfireCache);
    regionFactoryBean.setName("Calculations");
    regionFactoryBean.setPersistent(false);

    return regionFactoryBean;
  }

  @Bean
  @SuppressWarnings("unchecked")
  public RegionAttributesFactoryBean calculationsRegionAttributes(
      @Value("${app.gemfire.default.region.initial-capacity:51}") int initialCapacity,
      @Value("${app.gemfire.default.region.load-factor:0.85}") float loadFactor) {

    RegionAttributesFactoryBean calculationRegionAttributes = new RegionAttributesFactoryBean();

    calculationRegionAttributes.setInitialCapacity(initialCapacity);
    calculationRegionAttributes.setLoadFactor(loadFactor);
    calculationRegionAttributes.setKeyConstraint(BigInteger.class);
    calculationRegionAttributes.setValueConstraint(BigInteger.class);

    return calculationRegionAttributes;
  }

}
