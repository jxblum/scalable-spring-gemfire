package sample.startupquotes.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sample.startupquotes.app.context.config.StartupQuotesServiceConfiguration;
import sample.startupquotes.service.StartupQuoteService;

/**
 * StartupQuotesWebApplication class is a Spring Boot application class for launching the Startup Quotes Service.
 *
 * @author John Blum
 * @see org.springframework.boot.SpringApplication
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 * @see org.springframework.stereotype.Controller
 * @see sample.startupquotes.app.context.config.StartupQuotesServiceConfiguration
 * @see sample.startupquotes.service.StartupQuoteService
 * @link https://github.com/gophergala/wisdom
 * @link http://startupquote.com/
 * @since 1.0.0
 */
@SpringBootApplication
@Import(StartupQuotesServiceConfiguration.class)
@Controller
@RequestMapping("/quotes")
@SuppressWarnings("unused")
public class StartupQuotesWebApplication {

  protected static final String CACHE_MISS_ATTRIBUTE_NAME = "cacheMiss";
  protected static final String QUOTE_VIEW_NAME = "quote";
  protected static final String PING_RESPONSE = "Pong!";
  protected static final String TEST_VIEW_NAME = "test";

  @Autowired
  private StartupQuoteService quoteService;

  public static void main(final String[] args) {
    SpringApplication.run(StartupQuotesWebApplication.class, args);
  }

  protected StartupQuoteService quoteService() {
    Assert.state(quoteService != null, "'quoteService' was not properly initialized");
    return quoteService;
  }

  @RequestMapping("/ping")
  @ResponseBody
  public String ping() {
    return PING_RESPONSE;
  }

  @RequestMapping("/test")
  public String test() {
    return TEST_VIEW_NAME;
  }

  @RequestMapping("/random")
  public String randomQuote(ModelMap modelMap) {
    modelMap.addAttribute(quoteService().randomQuote());

    return QUOTE_VIEW_NAME;
  }

  @RequestMapping("/author/{author}/random")
  public String randomQuoteByAuthor(@PathVariable("author") String author, ModelMap modelMap) {
    modelMap.addAttribute(quoteService().randomQuote(author));
    modelMap.addAttribute(CACHE_MISS_ATTRIBUTE_NAME, quoteService().isCacheMiss());

    return QUOTE_VIEW_NAME;
  }

}
