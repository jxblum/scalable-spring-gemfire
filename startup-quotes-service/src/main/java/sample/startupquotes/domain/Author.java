package sample.startupquotes.domain;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.Region;

import sample.core.lang.ObjectUtils;

/**
 * The Author class...
 *
 * @author John Blum
 * @see java.io.Serializable
 * @since 1.0.0
 */
@Region("Authors")
@SuppressWarnings("unused")
public class Author implements Serializable {

  @Id
  private Long id;

  private String company;
  private String name;

  @JsonProperty("twitter_username")
  private String twitterHandle;

  private URL avatarUrl;

  public static Author from(String json) {
    try {
      return new ObjectMapper().readValue(json, Author.class);
    }
    catch (IOException e) {
      throw new IllegalArgumentException(String.format("Failed to convert JSON 9(%1$s) into an object of type (%2$s)",
        json, Author.class.getName()));
    }
  }

  public Author() {
  }

  public Author(final Long id) {
    this.id = id;
  }

  public Author(final String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public URL getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(final URL avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(final String company) {
    this.company = company;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getTwitterHandle() {
    return twitterHandle;
  }

  public void setTwitterHandle(final String twitterHandle) {
    this.twitterHandle = twitterHandle;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }

    if (!(obj instanceof Author)) {
      return false;
    }

    Author that = (Author) obj;

    return ObjectUtils.equalsIgnoreNull(this.getId(), that.getId())
      && ObjectUtils.nullSafeEquals(this.getName(), that.getName())
      && ObjectUtils.nullSafeEquals(this.getCompany(), that.getCompany())
      && ObjectUtils.nullSafeEquals(this.getTwitterHandle(), that.getTwitterHandle());
  }

  @Override
  public int hashCode() {
    int hashValue = 17;
    hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(getId());
    hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(getName());
    hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(getCompany());
    hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(getTwitterHandle());
    return hashValue;
  }

  public String toJson() {
    try {
      return new ObjectMapper().writeValueAsString(this);
    }
    catch (JsonProcessingException e) {
      return e.getMessage();
    }
  }

  @Override
  public String toString() {
    return String.format("{ @type = %1$s, id = %2$d, name = %3$s, twitterHandle = %4$s, company = %5$s, avatarUrl = %6$s }",
      getClass().getName(), getId(), getName(), getTwitterHandle(), getCompany(), getAvatarUrl());
  }

}
