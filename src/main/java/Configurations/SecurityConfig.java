//package Configurations;
//
//import Filters.AuthFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//import javax.servlet.Filter;
//import javax.sql.DataSource;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public FilterRegistrationBean<Filter> myFilter() {
//        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
//        filterRegistrationBean.setFilter(new AuthFilter());
//        filterRegistrationBean.addUrlPatterns("/*");
//        return filterRegistrationBean;
//    }
//
//
//    @Override
//    protected void configure (AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("select email, password FROM users WHERE email=?") //I should then add here the column enabled that I do not have because no email is being sent.
//                .authoritiesByUsernameQuery(
//                        "SELECT u.email, r.role_name " +
//                        "FROM users u " +
//                        "JOIN user_roles ur ON u.user_id = ur.user_id " +
//                        "JOIN roles r ON ur.role_id = r.role_id " +
//                        "WHERE u.email=?");
//    }
//
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/user/**").hasRole("USER")
//                .antMatchers("/css/**","/js/**","/images/**").permitAll()
//                .antMatchers("/", "/index.jsp").permitAll()
//                .anyRequest().authenticated()
//                .and()
////              .formLogin()
////                .loginPage("/login")
////                .permitAll()
////                .and()
//              .logout()
//                .permitAll()
//                .and()
//                .csrf().disable();
//        http
//                .antMatcher("/login/**")
//                .authorizeRequests()
//                .anyRequest().permitAll()
//                .and()
//                .csrf().disable();
//
//        http
//                .antMatcher("/signUp/**")
//                .authorizeRequests()
//                .anyRequest().permitAll()
//                .and()
//                .csrf().disable();
//
//    }
//
//
//}
