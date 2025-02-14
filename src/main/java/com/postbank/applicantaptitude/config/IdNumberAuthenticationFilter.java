//package com.postbank.applicantaptitude.config;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//public class IdNumberAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final AuthenticationManager authenticationManager;
//
//    public IdNumberAuthenticationFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//        setFilterProcessesUrl("/api/auth"); // URL for ID number login
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        try {
//            String idNumber = request.getParameter("idNumber");
//
//            if (idNumber == null || idNumber.isEmpty()) {
//                throw new BadCredentialsException("ID Number is required");
//            }
//
//            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(idNumber, null);
//            return authenticationManager.authenticate(authRequest);
//        } catch (AuthenticationException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new AuthenticationServiceException("Authentication failed", e);
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(authResult);
//
//        // Store authentication in session
//        HttpSession session = request.getSession(true);
//        session.setAttribute("AUTHENTICATED_USER", authResult.getName());  // Store ID Number
//
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write("{\"message\": \"Login successful\", \"idNumber\": \"" + authResult.getName() + "\"}");
//    }
//
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setContentType("application/json");
//        response.getWriter().write("{\"error\": \"" + failed.getMessage() + "\"}");
//    }
//}