/** create by liuhua at 2018年7月2日 上午11:49:37 **/
package com.star.truffle.core.jdbc;

import static com.google.common.base.Preconditions.checkArgument;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;
import com.google.common.base.Strings;
import com.star.truffle.core.ConditionalOnMapProperty;
import com.star.truffle.core.StarBaseAutoConfiguration;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import net.sf.log4jdbc.log.slf4j.Slf4jSpyLogDelegator;
import net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy;

@Slf4j
@Configuration
@ConditionalOnMapProperty(value = MultiMysqlProperties.class)
@ConditionalOnClass({DataSource.class, HikariDataSource.class, DataSourceSpy.class})
@AutoConfigureAfter(name = "org.springframework.cloud.sleuth.autoconfig.TraceAutoConfiguration")
public class MysqlDataSourceConfiguration extends StarBaseAutoConfiguration implements BeanFactoryPostProcessor, EnvironmentAware, Ordered {

  private ConfigurableEnvironment environment;

  private static final String[] PROPERTIES_TO_COPY = {"log4jdbc.log4j2.properties.file", "log4jdbc.debug.stack.prefix", "log4jdbc.sqltiming.warn.threshold", "log4jdbc.sqltiming.error.threshold", "log4jdbc.dump.booleanastruefalse", "log4jdbc.dump.fulldebugstacktrace", "log4jdbc.dump.sql.maxlinelength", "log4jdbc.statement.warn", "log4jdbc.dump.sql.select", "log4jdbc.dump.sql.insert", "log4jdbc.dump.sql.update", "log4jdbc.dump.sql.delete", "log4jdbc.dump.sql.create",
      "log4jdbc.dump.sql.addsemicolon", "log4jdbc.auto.load.popular.drivers", "log4jdbc.drivers", "log4jdbc.trim.sql", "log4jdbc.trim.sql.extrablanklines", "log4jdbc.suppress.generated.keys.exception", "log4jdbc.log4j2.properties.file",};


  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    MultiMysqlProperties multiMysqlProperties = resolverSetting(MultiMysqlProperties.class, this.environment.getPropertySources());
    initLog4Jdbc();
    multiMysqlProperties.getStarMysql().forEach((name, properties) -> createBean(configurableListableBeanFactory, name, properties));
  }

  private void createBean(ConfigurableListableBeanFactory configurableListableBeanFactory, String prefixName, MysqlProperties mysqlProperties) {

    String url = mysqlProperties.getUrl();
    checkArgument(!Strings.isNullOrEmpty(url), prefixName + " url is null or empty");
    log.info("prefixName is {}, url is {}, jdbc properties is {}", prefixName, getJdbcUrl(url), mysqlProperties);

    HikariDataSource hikariDataSource = createHikariDataSource(mysqlProperties);
    DataSourceSpy dataSource = new DataSourceSpy(hikariDataSource);

    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
    AnnotationTransactionAspect.aspectOf().setTransactionManager(transactionManager);

    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    register(configurableListableBeanFactory, dataSource, prefixName + "DataSource", prefixName + "Ds");
    register(configurableListableBeanFactory, jdbcTemplate, prefixName + "JdbcTemplate", prefixName + "Jt");
    register(configurableListableBeanFactory, transactionManager, prefixName + "TransactionManager", prefixName + "Tx");
  }


  private HikariDataSource createHikariDataSource(MysqlProperties mysqlProperties) {
    HikariDataSource hikariDataSource = new HikariDataSource();
    hikariDataSource.setJdbcUrl(mysqlProperties.getUrl());
    hikariDataSource.setUsername(mysqlProperties.getUsername());
    hikariDataSource.setPassword(mysqlProperties.getPassword());

    JdbcPoolConfig jdbcPoolConfig = mysqlProperties.getPool();
    hikariDataSource.setAutoCommit(jdbcPoolConfig.isAutoCommit());
    hikariDataSource.setConnectionTimeout(jdbcPoolConfig.getConnectionTimeout());
    hikariDataSource.setIdleTimeout(jdbcPoolConfig.getIdleTimeout());
    hikariDataSource.setMaxLifetime(jdbcPoolConfig.getMaxLifetime());
    hikariDataSource.setMaximumPoolSize(jdbcPoolConfig.getMaximumPoolSize());
    hikariDataSource.setMinimumIdle(jdbcPoolConfig.getMinimumIdle());
    hikariDataSource.setInitializationFailTimeout(jdbcPoolConfig.getInitializationFailTimeout());
    hikariDataSource.setIsolateInternalQueries(jdbcPoolConfig.isIsolateInternalQueries());
    hikariDataSource.setReadOnly(jdbcPoolConfig.isReadOnly());
    hikariDataSource.setRegisterMbeans(jdbcPoolConfig.isRegisterMbeans());
    Optional.ofNullable(jdbcPoolConfig.getDriverClassName()).ifPresent(hikariDataSource::setDriverClassName);
    hikariDataSource.setValidationTimeout(jdbcPoolConfig.getValidationTimeout());
    hikariDataSource.setLeakDetectionThreshold(jdbcPoolConfig.getLeakDetectionThreshold());
    return hikariDataSource;
  }

  private String getJdbcUrl(String url) {
    int index = url.indexOf("?");
    if (index > 0) {
      return url.substring(0, index);
    }
    return url;
  }


  private void initLog4Jdbc() {
    for (final String property : PROPERTIES_TO_COPY) {
      if (this.environment.containsProperty(property)) {
        System.setProperty(property, this.environment.getProperty(property));
      }
    }
    System.setProperty("log4jdbc.spylogdelegator.name", this.environment.getProperty("log4jdbc.spylogdelegator.name", Slf4jSpyLogDelegator.class.getName()));
  }

  @Override
  public void setEnvironment(Environment environment) {
    this.environment = (ConfigurableEnvironment) environment;
  }

  @Override
  public int getOrder() {
    return Integer.MAX_VALUE - 1;
  }
}
