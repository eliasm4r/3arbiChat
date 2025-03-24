package MVC.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest; // Assurez-vous d'importer cette classe
import jakarta.servlet.ServletResponse; // Assurez-vous d'importer cette classe
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
    
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            res.sendRedirect("login.jsp"); // Redirige vers login.jsp si non connecté
        } else {
            chain.doFilter(request, response); // Continue la chaîne si connecté
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}