package com.chf.conf;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
@EnableJpaRepositories("com.chf.repository")
@EnableTransactionManagement
public class DataBaseConfig implements EnvironmentAware {

	private RelaxedPropertyResolver liquiBasePropertyResolver;

	private RelaxedPropertyResolver dataSourcePropertyResolver;
	
	private Environment env;
	
	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;

        this.liquiBasePropertyResolver = new RelaxedPropertyResolver(env, "liquiBase.");
		this.dataSourcePropertyResolver = new RelaxedPropertyResolver(environment,
				"spring.datasource.");
	}

	@Bean
	public DataSource dataSource() {
		if (dataSourcePropertyResolver.getProperty("url") == null
				&& dataSourcePropertyResolver.getProperty("databaseName") == null) {

			throw new ApplicationContextException(
					"Database connection pool is not configured correctly");
		}
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName(
				dataSourcePropertyResolver.getProperty("dataSourceClassName"));
		if (dataSourcePropertyResolver.getProperty("url") == null
				|| "".equals(dataSourcePropertyResolver.getProperty("url"))) {
			config.addDataSourceProperty("databaseName",
					dataSourcePropertyResolver.getProperty("databaseName"));
			config.addDataSourceProperty("serverName",
					dataSourcePropertyResolver.getProperty("serverName"));
		} else {
			config.addDataSourceProperty("url",
					dataSourcePropertyResolver.getProperty("url"));
		}
		config.addDataSourceProperty("user",
				dataSourcePropertyResolver.getProperty("username"));
		config.addDataSourceProperty("password",
				dataSourcePropertyResolver.getProperty("password"));

		// MySQL optimizations, see
		// https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
		if ("com.mysql.jdbc.jdbc2.optional.MysqlDataSource"
				.equals(dataSourcePropertyResolver.getProperty("dataSourceClassName"))) {
			config.addDataSourceProperty("cachePrepStmts",
					dataSourcePropertyResolver.getProperty("cachePrepStmts", "true"));
			config.addDataSourceProperty("prepStmtCacheSize",
					dataSourcePropertyResolver.getProperty("prepStmtCacheSize", "250"));
			config.addDataSourceProperty("prepStmtCacheSqlLimit",
					dataSourcePropertyResolver.getProperty("prepStmtCacheSqlLimit",
							"2048"));
			config.addDataSourceProperty("useServerPrepStmts",
					dataSourcePropertyResolver.getProperty("useServerPrepStmts", "true"));
		}
		return new HikariDataSource(config);
	}

	@Bean(name = {
			"org.springframework.boot.autoconfigure.AutoConfigurationUtils.basePackages" })
	public List<String> getBasePackages() {
		List<String> basePackages = new ArrayList<>();
		basePackages.add("com.chf.domain");
		return basePackages;
	}

	@Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:config/liquibase/master.xml");
        liquibase.setContexts(liquiBasePropertyResolver.getProperty("context"));
        return liquibase;
    }
	
	@Bean
	public Hibernate4Module hibernate4Module() {
		return new Hibernate4Module();
	}
}
