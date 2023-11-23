/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.unincor.web.filters;

import com.mycompany.gerenciamentoalunos.model.domain.Usuario;
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

/**
 *
 * @author alunos
 */
@WebFilter("/pages/cliente/*")
public class FiltroRequisicaoGerente implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      // System.out.println("Requisição Filtrada");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession httpSession = httpServletRequest.getSession(true);
        
        
        if(httpSession.getAttribute("gerenteLogado") == null) {
            
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/pages/login_gerente.jsf");
            return;
        }

        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        
    }
    
}