package br.com.unincor.web.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter("/pages/cliente/*")
public class FiltroRequisicaoCliente implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      // System.out.println("Requisição Filtrada");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession httpSession = httpServletRequest.getSession(true);
        
        
        if(httpSession.getAttribute("clienteLogado") == null) {
            
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/pages/login_cliente.jsf");
            return;
        }

        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        
    }
    
}
