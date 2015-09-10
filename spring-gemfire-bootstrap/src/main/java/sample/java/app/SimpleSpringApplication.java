package sample.java.app;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * SimpleSpringApplication is a Java application class that configures and bootstraps a Spring ApplicationContext.
 *
 * @author John Blum
 * @see org.springframework.context.ConfigurableApplicationContext
 * @see org.springframework.context.support.ClassPathXmlApplicationContext
 * @since 1.0.0
 */
public class SimpleSpringApplication {

  public static void main(final String[] args) {
    ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext(args);
    applicationContext.registerShutdownHook();
  }

}
