//package Configurations;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DataBaseConfig {
//
//    @Bean
//    public DataSource dataSource() {
////        DriverManagerDataSource dataSource = new DriverManagerDataSource();
////        dataSource.setDriverClassName("org.postgresql.Driver");
////        dataSource.setUrl("jdbc:postgresql://localhost:5432/ToDoList");
////        dataSource.setUsername("postgres");
////        dataSource.setPassword("postgres");
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl("jdbc:postgresql://localhost:5432/ToDoList");
//        config.setUsername("postgres");
//        config.setPassword("postgres");
//        config.setDriverClassName("org.postgresql.Driver");
//
//        return new HikariDataSource(config);
//    }
//}
