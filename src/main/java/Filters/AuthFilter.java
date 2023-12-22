package Filters;

import com.sun.org.apache.xpath.internal.operations.Bool;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//public class AuthFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        HttpSession session = request.getSession();
//
//        Boolean isAuthenticated = false;
//        Boolean sessionExists = session != null;
//        Boolean isLoginPage = request.getRequestURI().equals("/login");
//        Boolean isLogoutURI = request.getRequestURI().equals("/logout");
//
//        if (sessionExists) {
//            isAuthenticated = (Boolean) session.getAttribute("authenticated");
//            if (isAuthenticated == null) {
//                isAuthenticated = false;
//            }
//        }
//
//        if (isAuthenticated && !isLoginPage || !isAuthenticated && isLoginPage) {
//            filterChain.doFilter(request, response);
//        } else if (isAuthenticated && isLoginPage) {
//            response.sendRedirect("/home");
//        } else {
//            response.sendRedirect("/login");
//        }
//
//
//
//        if (isLogoutURI) {
//            if (sessionExists) {
//                session.invalidate();
//            }
//            response.sendRedirect("/login");
//            return;
//        }
//
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}

//    String url = ((HttpServletRequest) request).getRequestURI();
//        if ("/signUp".equals(url)) {
//                chain.doFilter(request, response); // Allow access to the registration page
//                return;
//                }
//
//                // For other requests, continue with the filter chain
//                chain.doFilter(request, response);
