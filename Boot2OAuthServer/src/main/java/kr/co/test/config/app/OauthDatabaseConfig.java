package kr.co.test.config.app;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.flywaydb.core.Flyway;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * <pre>
 * 개정이력
 * -----------------------------------
 * 2021. 1. 21. kdk	최초작성
 * </pre>
 *
 *
 * @author kdk
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"kr.co.test.**.mapper"}, annotationClass = Mapper.class)
public class OauthDatabaseConfig {

	private static final String MYBATIS_CONFIG_LOCATION = "mybatis/configuration.xml";
	private static final String ACCOUNT_MAPPER_LOCATION = "classpath:mybatis/mappers/**/*.xml";

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.oauth")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}

	@Bean(name = "oauthDataSource")
	public DataSource dataSource() {
		return new HikariDataSource(this.hikariConfig());
	}

	@Bean
	public PlatformTransactionManager oauthTransactionManager(@Qualifier("oauthDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public Flyway flyway(@Qualifier("oauthDataSource") DataSource dataSource) {
		Flyway flyway = Flyway.configure()
			.dataSource(dataSource)
			.locations("classpath:db/migration")
			.baselineOnMigrate(true)
			.load();

		flyway.migrate();

		return flyway;
	}

	@Bean
	public SqlSessionFactory oauthSqlSessionFactory(@Qualifier("oauthDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG_LOCATION));
		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(ACCOUNT_MAPPER_LOCATION));
		return sqlSessionFactory.getObject();
	}

	@Bean
	public SqlSessionTemplate oauthSqlSessionTemplate(@Qualifier("oauthSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
