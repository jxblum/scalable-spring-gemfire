package sample.spring.cache.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import sample.spring.cache.app.context.config.CalculatorServiceConfiguration;

/**
 * The CalculatorServiceIntegrationTest class is a test suite of test cases testing the contract and functionality
 * of the CalculatorService class and Spring Cache Abstraction using Apache Geode as a caching provider.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.springframework.test.context.ContextConfiguration
 * @see org.springframework.test.context.junit4.SpringJUnit4ClassRunner
 * @see sample.spring.cache.app.context.config.CalculatorServiceConfiguration
 * @see sample.spring.cache.service.CalculatorService
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CalculatorServiceConfiguration.class)
@SuppressWarnings("unused")
public class CalculatorServiceIntegrationTest {

  @Autowired
  private CalculatorService calculatorService;

  protected CalculatorService calculatorService() {
    Assert.notNull(calculatorService, "'calculatorService' was not properly initialized");
    return calculatorService;
  }

  protected BigInteger log(BigInteger result, long number, boolean cacheHit) {
    System.err.printf("%1$d! == %2$s; Cache %3$s%n", number, result, (cacheHit ? "Hit" : "Miss"));
    return result;
  }

  @Test
  public void factorialCacheHitsAndMisses() {
    BigInteger four = new BigInteger("4");
    BigInteger twentyFour = calculatorService().factorial(four);

    boolean cacheHit = calculatorService().isCacheHit();

    log(twentyFour, 4, cacheHit);

    assertThat(twentyFour, is(notNullValue()));
    assertThat(twentyFour.intValue(), is(equalTo(24)));
    assertThat(cacheHit, is(false));

    twentyFour = calculatorService().factorial(four);
    cacheHit = calculatorService().isCacheHit();

    log(twentyFour, 4, cacheHit);

    assertThat(twentyFour, is(notNullValue()));
    assertThat(twentyFour.intValue(), is(equalTo(24)));
    assertThat(cacheHit, is(true));

    BigInteger oneHundredTwenty = calculatorService().factorial(new BigInteger("5"));

    cacheHit = calculatorService().isCacheHit();
    log(oneHundredTwenty, 5, cacheHit);

    assertThat(oneHundredTwenty, is(notNullValue()));
    assertThat(oneHundredTwenty.intValue(), is(equalTo(120)));
    assertThat(cacheHit, is(false));
  }

}
