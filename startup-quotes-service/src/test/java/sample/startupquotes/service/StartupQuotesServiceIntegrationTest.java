package sample.startupquotes.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import sample.startupquotes.app.context.config.StartupQuotesServiceConfiguration;
import sample.startupquotes.domain.Quote;

/**
 * The StartupQuotesServiceIntegrationTest class is a test suite of test cases testing the contract and functionality
 * of the StartupQuoteService and it's caching behavior.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.springframework.test.context.ContextConfiguration
 * @see org.springframework.test.context.junit4.SpringJUnit4ClassRunner
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StartupQuotesServiceConfiguration.class)
@SuppressWarnings("unused")
public class StartupQuotesServiceIntegrationTest {

  @Autowired
  private StartupQuoteService quoteService;

  protected StartupQuoteService quoteService() {
    Assert.state(quoteService != null, "'quoteService' was not properly initialized");
    return quoteService;
  }

  protected Quote log(Quote quote) {
    System.err.printf("\"%1$s\" - %2$s (@%3$s)%n", quote.getContent(), quote.getAuthor().getName(),
      quote.getAuthor().getTwitterHandle());

    return quote;
  }

  @Test
  public void quoteCacheHitsAndMisses() {
    Quote paulGrahamQuote = log(quoteService().randomQuote("paulg"));

    assertThat(paulGrahamQuote, is(notNullValue()));
    assertThat(quoteService().isCacheMiss(), is(true));

    String expectedPaulGrahamQuoteText = paulGrahamQuote.getContent();

    Quote anotherPaulGraphQuote = log(quoteService().randomQuote("paulg"));

    assertThat(paulGrahamQuote, is(notNullValue()));
    assertThat(quoteService().isCacheHit(), is(true));
    assertThat(anotherPaulGraphQuote.getContent(), is(equalTo(expectedPaulGrahamQuoteText)));

    Quote marissaMayerQuote = log(quoteService().randomQuote("marissamayer"));

    assertThat(marissaMayerQuote, is(notNullValue()));
    assertThat(quoteService().isCacheMiss(), is(true));
  }

}
