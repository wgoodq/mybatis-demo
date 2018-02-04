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
 * 通过 basePackages 属性不同的数据源扫描不同包下的 Mapper.
 *
 * @author kyou on 2018/2/4 15:58
 */
@Configuration
@MapperScan(basePackages = "cn.ok.mybatisdemo.mapper.smp", sqlSessionTemplateRef = "SmpSqlSessionTemplate")
public class SmpDataSource {

    /**
     * 指定的 Bean name 跟方法名不能一样
     *
     * @return 数据源对象
     */
    @Bean(name = "SmpDataSource")
    @ConfigurationProperties(prefix = "datasource.mysql.smp")
    public DataSource smpDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "SmpSqlSessionFactory")
    public SqlSessionFactory smpSqlSessionFactory(@Qualifier("SmpDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // 下划线(数据库字段) 转 驼峰(业务对象)
        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return bean.getObject();
    }

    @Bean(name = "SmpTransactionManager")
    @Primary
    public DataSourceTransactionManager smpTransactionManager(@Qualifier("SmpDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "SmpSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate smpSqlSessionTemplate(
            @Qualifier("SmpSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}