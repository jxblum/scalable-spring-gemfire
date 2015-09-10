package sample.spring.cache.service;

import java.math.BigInteger;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import sample.core.service.CacheableService;

/**
 * CalculatorService is a Spring @Service bean component class encapsulating CPU expensive operations applicable
 * to caching.
 *
 * @author John Blum
 * @see org.springframework.cache.annotation.Cacheable
 * @see org.springframework.stereotype.Service
 * @see sample.core.service.CacheableService
 * @since 1.0.0
 */
@Service
@SuppressWarnings("unused")
public class CalculatorService extends CacheableService {

  protected static final BigInteger NEGATIVE_ONE = BigInteger.ONE.negate();
  protected static final BigInteger TWO = BigInteger.valueOf(2);

  protected static final String NUMBER_LESS_THAN_ZERO_ERROR_MESSAGE = "number (%1$d) must be greater than equal to zero";

  @Cacheable("Calculations")
  public BigInteger factorial(BigInteger number) {
    try {
      Assert.notNull(number, "number must not be null");
      Assert.isTrue(number.compareTo(BigInteger.ZERO) >= 0, String.format(NUMBER_LESS_THAN_ZERO_ERROR_MESSAGE, number));

      if (number.compareTo(TWO) <= 0) {
        return (number.equals(TWO) ? TWO : BigInteger.ONE);
      }

      BigInteger result = number;

      for (number = result.add(NEGATIVE_ONE) ; number.compareTo(BigInteger.ONE) > 0; number = number.add(NEGATIVE_ONE)) {
        result = result.multiply(number);
      }

      return result;
    }
    finally {
      setCacheMiss();
    }
  }

}
