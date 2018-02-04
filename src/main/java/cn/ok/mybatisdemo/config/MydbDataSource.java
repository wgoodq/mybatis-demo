package cn.ok.mybatisdemo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author kyou on 2018/2/4 15:58
 */
@Configuration
@MapperScan(basePackages = "cn.ok.mybatisdemo.mapper.mydb", sqlSessionTemplateRef = "MydbSqlSessionTemplate")
public class MydbDataSource {

    /**
     * 指定的 Bean name 跟方法名不能一样
     *
     * @return 数据源对象
     */
    @Bean(name = "MydbDataSource")
    @ConfigurationProperties(prefix = "datasource.mysql.mydb")
    @Primary
    public DataSource mydbDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "MydbSqlSessionFactory")
    @Primary
    public SqlSessionFactory mydbSqlSessionFactory(@Qualifier("MydbDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // 下划线(数据库字段) 转 驼峰(业务对象)
        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return bean.getObject();
    }

    @Bean(name = "MydbTransactionManager")
    @Primary
    public DataSourceTransactionManager mydbTransactionManager(@Qualifier("MydbDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "MydbSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate mydbSqlSessionTemplate(
            @Qualifier("MydbSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
