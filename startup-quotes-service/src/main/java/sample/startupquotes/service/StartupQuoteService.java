package sample.startupquotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import sample.core.service.CacheableService;
import sample.startupquotes.dao.StartupQuoteDao;
import sample.startupquotes.domain.Quote;

/**
 * StartupQuoteService is a Spring bean @Service component that sends for the (startup) quote of the day.
 *
 * @author John Blum
 * @see org.springframework.stereotype.Service
 * @see org.springframework.web.client.RestTemplate
 * @see sample.core.service.CacheableService
 * @since 1.0.0
 */
@Service
@SuppressWarnings("unused")
public class StartupQuoteService extends CacheableService {

  @Autowired
  private StartupQuoteDao quoteDao;

  public StartupQuoteService() {
  }

  public StartupQuoteService(final StartupQuoteDao quoteDao) {
    setQuoteDao(quoteDao);
  }

  public final void setQuoteDao(final StartupQuoteDao quoteDao) {
    this.quoteDao = quoteDao;
  }

  protected StartupQuoteDao quoteDao() {
    Assert.state(quoteDao != null, "'quoteDao' was not properly initialized");
    return quoteDao;
  }

  public Quote randomQuote() {
    return quoteDao().random();
  }

  @Cacheable("Quotes")
  public Quote randomQuote(String twitterHandle) {
    try {
      return quoteDao().random(twitterHandle);
    }
    finally {
      setCacheMiss();
    }
  }

  public static void main(final String[] args) {
    StartupQuoteService quoteService = new StartupQuoteService();

    quoteService.setQuoteDao(new StartupQuoteDao());

    System.out.printf("Startup Quote of the Day... %1$s%n", quoteService.randomQuote());
    System.out.printf("Paul Graham Startup Quote of the Day... %1$s%n", quoteService.randomQuote("paulg"));
  }

}
