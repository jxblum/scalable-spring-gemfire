package sample.startupquotes.dao;

import java.util.Collections;

import com.gemstone.gemfire.management.internal.cli.util.spring.StringUtils;

import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import sample.startupquotes.domain.Quote;

/**
 * StartupQuoteDao is a Spring DaoSupport class implementation that makes HTTP requests to fetch famous quotes
 * by startup founders and entrepreneurs.
 *
 * @author John Blum
 * @see org.springframework.dao.support.DaoSupport
 * @see org.springframework.stereotype.Repository
 * @see org.springframework.web.client.RestTemplate
 * @since 1.0.0
 */
@Repository
@SuppressWarnings("unused")
public class StartupQuoteDao extends DaoSupport {

  protected static final String DEFAULT_AUTHOR_STARTUP_QUOTE_API_URL =
    "https://wisdomapi.herokuapp.com/v1/author/{author}/random";

  protected static final String DEFAULT_STARTUP_QUOTE_API_URL =
    "https://wisdomapi.herokuapp.com/v1/random";

  private RestTemplate restTemplate = new RestTemplate();

  private String authorStartupQuoteApiUrl;
  private String startupQuoteApiUrl;

  public void setAuthorStartupQuoteApiUrl(final String authorStartupQuoteApiUrl) {
    this.authorStartupQuoteApiUrl = authorStartupQuoteApiUrl;
  }

  protected String getAuthorStartupQuoteApiUrl() {
    return (StringUtils.hasText(authorStartupQuoteApiUrl) ? authorStartupQuoteApiUrl
      : DEFAULT_AUTHOR_STARTUP_QUOTE_API_URL);
  }

  public void setStartupQuoteApiUrl(final String startupQuoteApiUrl) {
    this.startupQuoteApiUrl = startupQuoteApiUrl;
  }

  protected String getStartupQuoteApiUrl() {
    return (StringUtils.hasText(startupQuoteApiUrl) ? startupQuoteApiUrl
      : DEFAULT_STARTUP_QUOTE_API_URL);
  }

  @Override
  protected void checkDaoConfig() throws IllegalArgumentException {
  }

  public Quote random() {
    return restTemplate.getForObject(getStartupQuoteApiUrl(), Quote.class);
  }

  public Quote random(String twitterHandle) {
    return restTemplate.getForObject(getAuthorStartupQuoteApiUrl(), Quote.class,
      Collections.singletonMap("author", twitterHandle));
  }

}
