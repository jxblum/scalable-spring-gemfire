package sample.gemfire.cache.support;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.gemstone.gemfire.cache.CacheLoader;
import com.gemstone.gemfire.cache.CacheLoaderException;
import com.gemstone.gemfire.cache.LoaderHelper;
import com.gemstone.gemfire.management.internal.cli.util.spring.StringUtils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * TimestampCacheLoader is a GemFire CacheLoader class that generates a timestamp in the configured format the moment
 * a cache miss occurs and this CacheLoader is triggered.
 *
 * @author John Blum
 * @see java.text.DateFormat
 * @see java.util.Calendar
 * @see java.util.Date
 * @see org.springframework.beans.factory.InitializingBean
 * @see com.gemstone.gemfire.cache.CacheLoader
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class TimestampCacheLoader<K> implements CacheLoader<K, String>, InitializingBean {

  protected static final String DEFAULT_TIMESTAMP_FORMAT_PATTERN = "YYYYMMDDHHmmss";

  private DateFormat timestampFormat;

  private String timestampFormatPattern;

  public void setTimestampFormatPattern(final String timestampFormatPattern) {
    this.timestampFormatPattern = timestampFormatPattern;
  }

  protected String getTimestampFormatPattern() {
    return (StringUtils.hasText(timestampFormatPattern) ? timestampFormatPattern : DEFAULT_TIMESTAMP_FORMAT_PATTERN);
  }

  protected DateFormat getTimestampFormat() {
    Assert.state(timestampFormat != null, "'timestampFormat' was not properly initiailzed");
    return timestampFormat;
  }

  @Override
  public String load(final LoaderHelper<K, String> loaderHelper) throws CacheLoaderException {
    return format(Calendar.getInstance().getTime());
  }

  protected String format(Date timestamp) {
    return getTimestampFormat().format(timestamp);
  }

  @Override
  public void close() {
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    timestampFormat = new SimpleDateFormat(getTimestampFormatPattern());
  }

}
