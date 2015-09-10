package sample.gemfire.cache.support;

import java.io.IOException;
import java.net.URI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gemstone.gemfire.cache.CacheLoader;
import com.gemstone.gemfire.cache.CacheLoaderException;
import com.gemstone.gemfire.cache.LoaderHelper;

import org.springframework.web.client.RestTemplate;

/**
 * RandomYoMommaJokesClassLoader is a GemFire ClassLoader that makes a web service api call to get
 * a random, "yo momma" joke.
 *
 * @author John Blum
 * @see java.net.URI
 * @see org.springframework.web.client.RestTemplate
 * @see com.gemstone.gemfire.cache.CacheLoader
 * @see com.fasterxml.jackson.databind.ObjectMapper
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class RandomYoMommaJokesClassLoader<K> implements CacheLoader<K, String> {

  private static final String JOKE_API_URL = "http://api.yomomma.info/";

  private RestTemplate restTemplate = new RestTemplate();

  public static void main(final String[] args) {
    System.out.printf("Joker says, \"%1$s\"%n", new RandomYoMommaJokesClassLoader<>().tellJoke());
  }

  protected Joke tellJoke() {
    URI jokeApiUri = URI.create(JOKE_API_URL);
    // HTTP response is "text/html"; HTML body contains JSON
    String htmlBody = restTemplate.getForObject(jokeApiUri, String.class);
    return Joke.from(htmlBody);
  }

  @Override
  public String load(final LoaderHelper<K, String> loaderHelper) throws CacheLoaderException {
    return String.valueOf(tellJoke());
  }

  @Override
  public void close() {
  }

  public static class Joke {

    public static final Joke DEFAULT = new Joke("I don't know any good jokes right now!");

    private String joke;

    public static Joke from(String json) {
      try {
        return new ObjectMapper().readValue(json, Joke.class);
      }
      catch (IOException e) {
        throw new RuntimeException(String.format("Failed to convert JSON (%1$s) to an instance of (%2$s)",
          json, Joke.class.getName()));
      }
    }

    public Joke() {
    }

    public Joke(final String joke) {
      setJoke(joke);
    }

    public final void setJoke(final String joke) {
      this.joke = joke;
    }

    protected String getJoke() {
      return joke;
    }

    public String toJson() {
      try {
        return new ObjectMapper().writeValueAsString(this);
      }
      catch (JsonProcessingException e) {
        throw new RuntimeException(String.format("Failed to write Joke (%1$s) as JSON", this), e);
      }
    }

    @Override
    public String toString() {
      return String.valueOf(getJoke());
    }
  }

}
