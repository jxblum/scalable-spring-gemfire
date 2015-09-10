package sample.core.domain;

import java.util.Arrays;

/**
 * The Gender enumerated type specifies a person's gender.
 *
 * @author John Blum
 * @see java.lang.Enum
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public enum Gender {
  FEMALE("F"),
  MALE("M");

  private final String abbreviation;

  Gender(final String abbreviation) {
    this.abbreviation = abbreviation;
  }

  public static Gender valueOfAbbreviation(String abbreviation) {
    for (Gender gender : values()) {
      if (gender.getAbbreviation().equalsIgnoreCase(abbreviation)) {
        return gender;
      }
    }

    throw new IllegalArgumentException(String.format(
      "the argument for abbreviation (%1$s) does not correspond to a valid Gender (%2$s)",
        abbreviation, Arrays.toString(Gender.values())));
  }

  public String getAbbreviation() {
    return abbreviation;
  }

}
