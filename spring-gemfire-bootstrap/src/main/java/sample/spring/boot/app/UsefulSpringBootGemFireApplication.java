package sample.spring.boot.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import sample.java.config.UsefulSpringBasedGemFireConfiguration;

/**
 * UsefulSpringBootGemFireApplication is a SpringBootApplication class used to configure and bootstrap GemFire.
 *
 * @author John Blum
 * @see org.springframework.boot.SpringApplication
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 * @see sample.java.config.UsefulSpringBasedGemFireConfiguration
 * @since 1.0.0
 */
@SpringBootApplication
@Import(UsefulSpringBasedGemFireConfiguration.class)
//@ImportResource("/simple-spring-gemfire-cache-context.xml")
//@ImportResource("/useful-spring-gemfire-cache-context.xml")
public class UsefulSpringBootGemFireApplication {

  public static void main(final String[] args) {
    SpringApplication.run(UsefulSpringBootGemFireApplication.class, args);
  }

}
