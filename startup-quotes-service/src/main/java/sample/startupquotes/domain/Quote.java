package sample.startupquotes.domain;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.Region;
import org.springframework.data.gemfire.support.TimeToLiveExpiration;

import sample.core.lang.ObjectUtils;

/**
 * Quote is an abstract data type and application domain object encapsulating a quote.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see com.fasterxml.jackson.annotation.JsonProperty
 * @see com.fasterxml.jackson.databind.ObjectMapper
 * @see org.springframework.data.annotation.Id
 * @see org.springframework.data.gemfire.mapping.Region
 * @see org.springframework.data.gemfire.support.TimeToLiveExpiration
 * @since 1.7.0
 */
@Region("Quotes")
@SuppressWarnings("unused")
@TimeToLiveExpiration(timeout = "${app.quote.expiration.ttl.timeout}", action = "${app.quote.expiration.ttl.action}")
public class Quote implements Serializable {

  private Author author;

  private List<Tag> tags;

  @Id
  private Long id;

  private String content;

  @JsonProperty("post_id")
  private String postId;

  private URL permalink;
  private URL pictureUrl;

  public static Quote from(String json) {
    try {
      return new ObjectMapper().readValue(json, Quote.class);
    }
    catch (IOException e) {
      throw new IllegalArgumentException(String.format("Failed to convert JSON (%1$s) into an object of type (%2$s)",
        json, Quote.class.getName()));
    }
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(final Author author) {
    this.author = author;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(final String content) {
    this.content = content;
  }

  public String getPostId() {
    return postId;
  }

  public void setPostId(final String postId) {
    this.postId = postId;
  }

  public URL getPermalink() {
    return permalink;
  }

  public void setPermalink(final URL permalink) {
    this.permalink = permalink;
  }

  public URL getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(final URL pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(final List<Tag> tags) {
    this.tags = tags;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }

    if (!(obj instanceof Quote)) {
      return false;
    }

    Quote that = (Quote) obj;

    return ObjectUtils.equalsIgnoreNull(this.getId(), that.getId())
      && ObjectUtils.equalsIgnoreNull(this.getPostId(), that.getPostId())
      && ObjectUtils.nullSafeEquals(this.getAuthor(), that.getAuthor())
      && ObjectUtils.nullSafeEquals(this.getContent(), that.getContent());
  }

  @Override
  public int hashCode() {
    int hashValue = 17;
    hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(getId());
    hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(getPostId());
    hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(getAuthor());
    hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(getContent());
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
    return String.format("{ @type = %1$s, id = %2$d, postId = %3$s, author = %4$s, content = \"%5$s\""
        + ", permalink = %6$s, pictureUrl = %7$s, tags = %8$s }",
      getClass().getName(), getId(), getPostId(), getAuthor(), getContent(), getPermalink(), getPictureUrl(), getTags());
  }

}
