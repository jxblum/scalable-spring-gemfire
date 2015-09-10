package sample.startupquotes.app.context.config;

import java.util.Properties;

import com.gemstone.gemfire.cache.Cache;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.gemfire.support.GemfireCacheManager;

import sample.startupquotes.dao.StartupQuoteDao;
import sample.startupquotes.service.StartupQuoteService;

/**
 * StartupQuotesServiceConfiguration is a Spring @Configuration class for configuring the Startup Quotes Service
 * web application.
 *
 * @author John Blum
 * @see org.springframework.cache.annotation.EnableCaching
 * @see org.springframework.context.annotation.Bean
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.Import
 * @see org.springframework.data.gemfire.support.GemfireCacheManager
 * @see sample.startupquotes.dao.StartupQuoteDao
 * @see sample.startupquotes.service.StartupQuoteService
 * @since 1.0.0
 */
@Configuration
@Import(GemFireConfiguration.class)
@EnableCaching
@SuppressWarnings("unused")
public class StartupQuotesServiceConfiguration {

  @Bean
  public Properties startupQuotesServiceSettings() {
    Properties startupQuoteServiceSettings = new Properties();

    startupQuoteServiceSettings.setProperty("app.quote.expiration.ttl.action", "DESTROY");
    startupQuoteServiceSettings.setProperty("app.quote.expiration.ttl.timeout", "60");
    startupQuoteServiceSettings.setProperty("app.gemfire.default.region.initial-capacity", "51");
    startupQuoteServiceSettings.setProperty("app.gemfire.default.region.load-factor", "0.85");

    return startupQuoteServiceSettings;
  }

  @Bean
  public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer(
      @Qualifier("startupQuotesServiceSettings") Properties startupQuotesServiceSettings) {

    PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();

    propertyPlaceholderConfigurer.setProperties(startupQuotesServiceSettings);

    return propertyPlaceholderConfigurer;
  }

  @Bean
  public GemfireCacheManager cacheManager(Cache gemfireCache) {
    GemfireCacheManager cacheManager = new GemfireCacheManager();
    cacheManager.setCache(gemfireCache);
    return cacheManager;
  }

  @Bean
  public StartupQuoteService startupQuoteService() {
    return new StartupQuoteService(startupQuoteDao());
  }

  @Bean
  public StartupQuoteDao startupQuoteDao() {
    return new StartupQuoteDao();
  }

}
