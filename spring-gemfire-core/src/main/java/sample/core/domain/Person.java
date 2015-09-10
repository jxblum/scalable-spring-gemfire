package sample.core.domain;

import java.util.Calendar;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.Region;

import sample.core.lang.DateTimeUtils;
import sample.core.lang.Identifiable;

/**
 * The Person class is an abstract data type modeling a person.
 *
 * @author John Blum
 * @see org.springframework.data.annotation.Id
 * @see org.springframework.data.gemfire.mapping.Region
 * @see sample.core.lang.Identifiable
 * @since 1.0.0
 */
@Region("People")
@SuppressWarnings("unused")
public class Person implements Identifiable<Long> {

  private Gender gender;

  @Id
  private Long id;

  private Long birthDate;

  private String firstName;
  private String lastName;

  public Person() {
  }

  public Person(final Long id) {
    this.id = id;
  }

  public Person(final String firstName, final String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public boolean isBirthDateKnown() {
    return (birthDate != null);
  }

  public Long getBirthDate() {
    return birthDate;
  }

  public Calendar getBirthDateAsCalendar() {
    if (isBirthDateKnown()) {
      Calendar birthDate = Calendar.getInstance();
      birthDate.clear();
      birthDate.setTimeInMillis(getBirthDate());
      return birthDate;
    }

    return null;
  }

  public Date getBirthDateAsDate() {
    return DateTimeUtils.asDate(getBirthDateAsCalendar());
  }

  public void setBirthDate(final Calendar birthDate) {
    setBirthDate(birthDate.getTimeInMillis());
  }

  public void setBirthDate(final Date birthDate) {
    setBirthDate(birthDate.getTime());
  }

  public void setBirthDate(final Long birthDate) {
    this.birthDate = birthDate;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public String getName() {
    return String.format("%1$s %2$s", getFirstName(), getLastName());
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(final Gender gender) {
    this.gender = gender;
  }

  @Override
  public String toString() {
    return String.format("{ @type = %1$s, id = %2$d, firstName = %3$s, lastName = %4$s, birthDate = %5$s, gender = %6$s }",
      getClass().getName(), getId(), getFirstName(), getLastName(), DateTimeUtils.format(getBirthDateAsDate(),
        DateTimeUtils.BIRTH_DATE_FORMAT), getGender());
  }
}
