package tacs.wololo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.zaxxer.hikari.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import javax.sql.DataSource;

@SpringBootApplication
@EnableScheduling
public class WololoApplication {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    public static void main(String[] args)
    {
        SpringApplication.run(WololoApplication.class, args);
    }


    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        return new HikariDataSource(config);
    }

}
