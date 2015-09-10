package sample.startupquotes.domain;

import java.io.Serializable;

/**
 * The Tag class...
 *
 * @author John Blum
 * @see java.io.Serializable
 * @since 1.7.0
 */
@SuppressWarnings("unused")
public class Tag implements Serializable {

  private Long id;

  private String label;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(final String label) {
    this.label = label;
  }

  @Override
  public String toString() {
    return String.format("{ @type = %1$s, id = %2$d, label = %3$s }", getClass().getName(), getId(), getLabel());
  }

}
