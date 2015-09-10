package sample.spring.boot.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.gemstone.gemfire.cache.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import sample.core.domain.Person;
import sample.core.lang.support.IdentifierSequence;

/**
 * The ExampleGemFireRepositorySpringBootApplication class is Spring Boot application testing
 * the Spring Data GemFire Repositories feature.
 *
 * @author John Blum
 * @see org.springframework.boot.CommandLineRunner
 * @see org.springframework.boot.SpringApplication
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 * @see org.springframework.context.annotation.Bean
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.data.gemfire.repository.GemfireRepository
 * @see com.gemstone.gemfire.cache.Cache
 * @since 1.0.0
 */
@SpringBootApplication
@SuppressWarnings("unused")
public class ExampleGemFireRepositorySpringBootApplication implements CommandLineRunner {

  public static void main(final String[] args) {
    SpringApplication.run(ExampleGemFireRepositorySpringBootApplication.class, args);
  }

  private Person jonDoe;
  private Person janeDoe;
  private Person cookieDoe;
  private Person froDoe;
  private Person pieDoe;
  private Person sourDoe;
  private Person jackBlack;
  private Person jackHandy;
  private Person sandyHandy;

  @Autowired
  private PersonRepository personRepository;

  protected Person createPerson(final String firstName, final String lastName) {
    Person person = new Person(firstName, lastName);
    IdentifierSequence.INSTANCE.setSequentialLongId(person);
    return person;
  }

  protected <T> T log(T object, String message) {
    System.err.printf("%1$s (%2$s)%n", message, object);
    return object;
  }

  protected Person put(final Person person) {
    return personRepository.save(person);
  }

  protected void setupPeople() {
    jonDoe = put(createPerson("Jon", "Doe"));
    janeDoe = put(createPerson("Jane", "Doe"));
    cookieDoe = put(createPerson("Cookie", "Doe"));
    froDoe = put(createPerson("Fro", "Doe"));
    pieDoe = put(createPerson("Pie", "Doe"));
    sourDoe = put(createPerson("Sour", "Doe"));
    jackBlack = put(createPerson("Jack", "Black"));
    jackHandy = put(createPerson("Jack", "Handy"));
    sandyHandy = put(createPerson("Sandy", "Handy"));
  }

  protected List<String> toNames(List<Person> people) {
    List<String> names = new ArrayList<>(people.size());

    for (Person person : people) {
      names.add(person.getName());
    }

    return names;
  }

  @Override
  public void run(final String... args) throws Exception {
    setupPeople();

    long peopleCount = log(personRepository.count(), "Number of people");

    Assert.isTrue(peopleCount == 9, String.format("Expected 9; but was (%1$d)", peopleCount));

    List<Person> actualDoeFamily = personRepository.findByLastName("Doe");
    List<Person> expectedDoeFamily = Arrays.asList(jonDoe, janeDoe, cookieDoe, froDoe, pieDoe, sourDoe);

    log(toNames(actualDoeFamily), "Actual 'Doe' family is");

    Assert.notNull(actualDoeFamily, "Doe family must not be null");
    Assert.isTrue(actualDoeFamily.containsAll(expectedDoeFamily), String.format("Expected (%1$s); but was (%2$s)",
      expectedDoeFamily, actualDoeFamily));
    Assert.isTrue(actualDoeFamily.size() == expectedDoeFamily.size(), String.format(
      "Expected (%1$d); but was (%2$d); additional people include (%3$s)",
        expectedDoeFamily.size(), actualDoeFamily.size(), actualDoeFamily.removeAll(expectedDoeFamily)));
  }

}

@Repository
interface PersonRepository extends GemfireRepository<Person, Long> {

  List<Person> findByLastName(String lastName);

}

@Configuration
@EnableGemfireRepositories
@SuppressWarnings("unused")
class ApplicationConfiguration {

  @Bean
  public Properties applicationSettings() {
    Properties applicationSettings = new Properties();

    applicationSettings.setProperty("app.gemfire.default.region.initial-capacity", "51");
    applicationSettings.setProperty("app.gemfire.default.region.load-factor", "0.85");

    return applicationSettings;
  }

  @Bean
  public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
    PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();

    propertyPlaceholderConfigurer.setProperties(applicationSettings());

    return propertyPlaceholderConfigurer;
  }
}

@Configuration
@SuppressWarnings("unused")
class GemFireConfiguration {

  @Bean
  public Properties gemfireProperties() {
    Properties gemfireProperties = new Properties();

    gemfireProperties.setProperty("name", ExampleGemFireRepositorySpringBootApplication.class.getSimpleName());
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

  @Bean(name = "People")
  public ReplicatedRegionFactoryBean<Long, Person> people(Cache gemfireCache) {
    ReplicatedRegionFactoryBean<Long, Person> regionFactoryBean = new ReplicatedRegionFactoryBean<>();

    regionFactoryBean.setCache(gemfireCache);
    regionFactoryBean.setName("People");
    regionFactoryBean.setPersistent(false);

    return regionFactoryBean;
  }

}
