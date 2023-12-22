//package Listener;
//
//import Repositories.Account.AccountRepository;
//import Repositories.Account.AccountRepositoryJdbclmpl;
//import org.postgresql.Driver;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//import java.sql.DriverManager;
//
//@WebListener
//public class CustomServletContextListener implements ServletContextListener {
//
//    private static final String DB_USER = "postgres";
//    private static final String DB_PASSWORD = "postgres";
//    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ToDoList";
//    private static final String DB_DRIVER = "org.postgresql.Driver";
//
//
//    @Override
//    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(DB_DRIVER);
//        dataSource.setUsername(DB_USER);
//        dataSource.setPassword(DB_PASSWORD);
//        dataSource.setUrl(DB_URL);
//
//        ServletContext servletContext = servletContextEvent.getServletContext();
//        AccountRepository accountRepository = new AccountRepositoryJdbclmpl(dataSource);
//        servletContext.setAttribute("accountRep", accountRepository);
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//
//    }
//}
