package com.toripizi.farmhub.controller.filter;

import com.toripizi.farmhub.controller.servlet.ApiServlet;
import com.toripizi.farmhub.controller.servlet.exception.HttpRequestException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Web filter with mechanism for catching exceptions and rewriting them to appropriate HTTP response statutes.
 */
@WebFilter(urlPatterns = {
        ApiServlet.API_PATH + "/*"
})
public class ExceptionFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            super.doFilter(request, response, chain);
        } catch (HttpRequestException ex) {
            response.sendError(ex.getResponseCode());
        }
    }

}
