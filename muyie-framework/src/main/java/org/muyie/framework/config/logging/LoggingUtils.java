package org.muyie.framework.config.logging;

import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.composite.ContextJsonProvider;
import net.logstash.logback.composite.GlobalCustomFieldsJsonProvider;
import net.logstash.logback.composite.loggingevent.ArgumentsJsonProvider;
import net.logstash.logback.composite.loggingevent.LogLevelJsonProvider;
import net.logstash.logback.composite.loggingevent.LoggerNameJsonProvider;
import net.logstash.logback.composite.loggingevent.LoggingEventFormattedTimestampJsonProvider;
import net.logstash.logback.composite.loggingevent.LoggingEventJsonProviders;
import net.logstash.logback.composite.loggingevent.LoggingEventPatternJsonProvider;
import net.logstash.logback.composite.loggingevent.LoggingEventThreadNameJsonProvider;
import net.logstash.logback.composite.loggingevent.MdcJsonProvider;
import net.logstash.logback.composite.loggingevent.MessageJsonProvider;
import net.logstash.logback.composite.loggingevent.StackTraceJsonProvider;
import net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder;
import net.logstash.logback.encoder.LogstashEncoder;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;

import org.muyie.framework.config.MuyieProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.boolex.OnMarkerEvaluator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.filter.EvaluatorFilter;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.FilterReply;

/**
 * Utility methods to add appenders to a {@link LoggerContext}.
 *
 * @author larry.qi
 */
public final class LoggingUtils {

  private static final Logger log = LoggerFactory.getLogger(LoggingUtils.class);

  private static final String CONSOLE_APPENDER_NAME = "CONSOLE";
  private static final String LOGSTASH_APPENDER_NAME = "LOGSTASH";
  private static final String ASYNC_LOGSTASH_APPENDER_NAME = "ASYNC_LOGSTASH";

  private LoggingUtils() {
  }

  /**
   * <p>
   * addJsonConsoleAppender.
   * </p>
   *
   * @param context      a {@link LoggerContext} object.
   * @param customFields a {@link String} object.
   */
  public static void addJsonConsoleAppender(final LoggerContext context, final String customFields) {
    log.info("Initializing Console loggingProperties");

    // More documentation is available at:
    // https://github.com/logstash/logstash-logback-encoder
    final ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
    consoleAppender.setContext(context);
    consoleAppender.setEncoder(compositeJsonEncoder(context, customFields));
    consoleAppender.setName(CONSOLE_APPENDER_NAME);
    consoleAppender.start();

    context.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME).detachAppender(CONSOLE_APPENDER_NAME);
    context.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME).addAppender(consoleAppender);
  }

  /**
   * <p>
   * addLogstashTcpSocketAppender.
   * </p>
   *
   * @param context            a {@link LoggerContext} object.
   * @param customFields       a {@link String} object.
   * @param logstashProperties a {@link MuyieProperties.Logging.Logstash} object.
   */
  public static void addLogstashTcpSocketAppender(final LoggerContext context, final String customFields,
                                                  final MuyieProperties.Logging.Logstash logstashProperties) {
    log.info("Initializing Logstash loggingProperties");

    // More documentation is available at:
    // https://github.com/logstash/logstash-logback-encoder
    final LogstashTcpSocketAppender logstashAppender = new LogstashTcpSocketAppender();
    logstashAppender.addDestinations(new InetSocketAddress(logstashProperties.getHost(), logstashProperties.getPort()));
    logstashAppender.setContext(context);
    logstashAppender.setEncoder(logstashEncoder(customFields));
    logstashAppender.setName(ASYNC_LOGSTASH_APPENDER_NAME);
    logstashAppender.setQueueSize(logstashProperties.getQueueSize());
    logstashAppender.start();

    context.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME).addAppender(logstashAppender);
  }

  /**
   * <p>
   * addContextListener.
   * </p>
   *
   * @param context      a {@link LoggerContext} object.
   * @param customFields a {@link String} object.
   * @param properties   a {@link MuyieProperties.Logging} object.
   */
  public static void addContextListener(final LoggerContext context, final String customFields,
                                        final MuyieProperties.Logging properties) {
    final LogbackLoggerContextListener loggerContextListener = new LogbackLoggerContextListener(properties,
      customFields);
    loggerContextListener.setContext(context);
    context.addListener(loggerContextListener);
  }

  /**
   * Configure a log filter to remove "metrics" logs from all appenders except the "LOGSTASH"
   * appender
   *
   * @param context       the logger context
   * @param useJsonFormat whether to use JSON format
   */
  public static void setMetricsMarkerLogbackFilter(final LoggerContext context, final boolean useJsonFormat) {
    log.info("Filtering metrics logs from all appenders except the {} appender", LOGSTASH_APPENDER_NAME);
    final OnMarkerEvaluator onMarkerMetricsEvaluator = new OnMarkerEvaluator();
    onMarkerMetricsEvaluator.setContext(context);
    onMarkerMetricsEvaluator.addMarker("metrics");
    onMarkerMetricsEvaluator.start();
    final EvaluatorFilter<ILoggingEvent> metricsFilter = new EvaluatorFilter<>();
    metricsFilter.setContext(context);
    metricsFilter.setEvaluator(onMarkerMetricsEvaluator);
    metricsFilter.setOnMatch(FilterReply.DENY);
    metricsFilter.start();

    context.getLoggerList().forEach(logger -> logger.iteratorForAppenders().forEachRemaining(appender -> {
      if (!appender.getName().equals(ASYNC_LOGSTASH_APPENDER_NAME)
        && !(appender.getName().equals(CONSOLE_APPENDER_NAME) && useJsonFormat)) {
        log.debug("Filter metrics logs from the {} appender", appender.getName());
        appender.setContext(context);
        appender.addFilter(metricsFilter);
        appender.start();
      }
    }));
  }

  private static LoggingEventCompositeJsonEncoder compositeJsonEncoder(final LoggerContext context,
                                                                       final String customFields) {
    final LoggingEventCompositeJsonEncoder compositeJsonEncoder = new LoggingEventCompositeJsonEncoder();
    compositeJsonEncoder.setContext(context);
    compositeJsonEncoder.setProviders(jsonProviders(context, customFields));
    compositeJsonEncoder.start();
    return compositeJsonEncoder;
  }

  private static LogstashEncoder logstashEncoder(final String customFields) {
    final LogstashEncoder logstashEncoder = new LogstashEncoder();
    logstashEncoder.setThrowableConverter(throwableConverter());
    logstashEncoder.setCustomFields(customFields);
    return logstashEncoder;
  }

  private static LoggingEventJsonProviders jsonProviders(final LoggerContext context, final String customFields) {
    final LoggingEventJsonProviders jsonProviders = new LoggingEventJsonProviders();
    jsonProviders.addArguments(new ArgumentsJsonProvider());
    jsonProviders.addContext(new ContextJsonProvider<>());
    jsonProviders.addGlobalCustomFields(customFieldsJsonProvider(customFields));
    jsonProviders.addLogLevel(new LogLevelJsonProvider());
    jsonProviders.addLoggerName(loggerNameJsonProvider());
    jsonProviders.addMdc(new MdcJsonProvider());
    jsonProviders.addMessage(new MessageJsonProvider());
    jsonProviders.addPattern(new LoggingEventPatternJsonProvider());
    jsonProviders.addStackTrace(stackTraceJsonProvider());
    jsonProviders.addThreadName(new LoggingEventThreadNameJsonProvider());
    jsonProviders.addTimestamp(timestampJsonProvider());
    jsonProviders.setContext(context);
    return jsonProviders;
  }

  private static GlobalCustomFieldsJsonProvider<ILoggingEvent> customFieldsJsonProvider(final String customFields) {
    final GlobalCustomFieldsJsonProvider<ILoggingEvent> customFieldsJsonProvider = new GlobalCustomFieldsJsonProvider<>();
    customFieldsJsonProvider.setCustomFields(customFields);
    return customFieldsJsonProvider;
  }

  private static LoggerNameJsonProvider loggerNameJsonProvider() {
    final LoggerNameJsonProvider loggerNameJsonProvider = new LoggerNameJsonProvider();
    loggerNameJsonProvider.setShortenedLoggerNameLength(20);
    return loggerNameJsonProvider;
  }

  private static StackTraceJsonProvider stackTraceJsonProvider() {
    final StackTraceJsonProvider stackTraceJsonProvider = new StackTraceJsonProvider();
    stackTraceJsonProvider.setThrowableConverter(throwableConverter());
    return stackTraceJsonProvider;
  }

  private static ShortenedThrowableConverter throwableConverter() {
    final ShortenedThrowableConverter throwableConverter = new ShortenedThrowableConverter();
    throwableConverter.setRootCauseFirst(true);
    return throwableConverter;
  }

  private static LoggingEventFormattedTimestampJsonProvider timestampJsonProvider() {
    final LoggingEventFormattedTimestampJsonProvider timestampJsonProvider = new LoggingEventFormattedTimestampJsonProvider();
    timestampJsonProvider.setTimeZone("UTC");
    timestampJsonProvider.setFieldName("timestamp");
    return timestampJsonProvider;
  }

  /**
   * Logback configuration is achieved by configuration file and API. When configuration file change
   * is detected, the configuration is reset. This listener ensures that the programmatic
   * configuration is also re-applied after reset.
   */
  private static class LogbackLoggerContextListener extends ContextAwareBase implements LoggerContextListener {
    private final MuyieProperties.Logging loggingProperties;
    private final String customFields;

    private LogbackLoggerContextListener(final MuyieProperties.Logging loggingProperties, final String customFields) {
      this.loggingProperties = loggingProperties;
      this.customFields = customFields;
    }

    @Override
    public boolean isResetResistant() {
      return true;
    }

    @Override
    public void onStart(final LoggerContext context) {
      if (this.loggingProperties.isUseJsonFormat()) {
        addJsonConsoleAppender(context, customFields);
      }
      if (this.loggingProperties.getLogstash().isEnabled()) {
        addLogstashTcpSocketAppender(context, customFields, loggingProperties.getLogstash());
      }
    }

    @Override
    public void onReset(final LoggerContext context) {
      if (this.loggingProperties.isUseJsonFormat()) {
        addJsonConsoleAppender(context, customFields);
      }
      if (this.loggingProperties.getLogstash().isEnabled()) {
        addLogstashTcpSocketAppender(context, customFields, loggingProperties.getLogstash());
      }
    }

    @Override
    public void onStop(final LoggerContext context) {
      // Nothing to do.
    }

    @Override
    public void onLevelChange(final ch.qos.logback.classic.Logger logger, final Level level) {
      // Nothing to do.
    }
  }
}
