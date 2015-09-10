package sample.spring.cache.app.context.config;

import java.util.Properties;

import com.gemstone.gemfire.cache.Cache;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.gemfire.support.GemfireCacheManager;

import sample.spring.cache.service.CalculatorService;

/**
 * CalculatorServiceConfiguration class is a Spring @Configuration class for configuring
 * the Calculator Service application.
 *
 * @author John Blum
 * @see org.springframework.context.annotation.Bean
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.ImportResource
 * @see sample.spring.cache.app.context.config.GemFireConfiguration
 * @since 1.0.0
 */
@Configuration
@EnableCaching
@Import(GemFireConfiguration.class)
@SuppressWarnings("unused")
public class CalculatorServiceConfiguration {

  @Bean
  public Properties applicationSettings() {
    Properties applicationSettings = new Properties();

    applicationSettings.setProperty("app.gemfire.default.region.initial-capacity", "101");
    applicationSettings.setProperty("app.gemfire.default.region.load-factor", "0.75");

    return applicationSettings;
  }

  @Bean
  public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
    PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();

    propertyPlaceholderConfigurer.setProperties(applicationSettings());

    return propertyPlaceholderConfigurer;
  }

  @Bean
  public GemfireCacheManager cacheManager(Cache gemfireCache) {
    GemfireCacheManager cacheManager = new GemfireCacheManager();
    cacheManager.setCache(gemfireCache);
    return cacheManager;
  }

  @Bean
  public CalculatorService calculatorService() {
    return new CalculatorService();
  }

}
