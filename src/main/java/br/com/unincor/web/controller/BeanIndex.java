package br.com.unincor.web.controller;

import br.com.unincor.web.model.dao.ContaCorrenteDao;
import br.com.unincor.web.model.dao.ContaDao;
import br.com.unincor.web.model.dao.ContaPoupancaDao;
import br.com.unincor.web.model.domain.Cliente;
import br.com.unincor.web.model.domain.Conta;
import br.com.unincor.web.model.domain.ContaCorrente;
import br.com.unincor.web.model.domain.ContaPoupanca;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ManagedBean
@ViewScoped
public class BeanIndex implements Serializable{
     Cliente cliente;
     ContaCorrente contaCorrente;
     ContaPoupanca contaPoupanca;
    
    @PostConstruct
    public void init() {
        HttpServletRequest request = (HttpServletRequest)
                FacesContext.getCurrentInstance().getExternalContext().getRequest();
        cliente = (Cliente) request.getSession().getAttribute("clienteLogado");
         contaCorrente = new ContaCorrenteDao().buscaContaCorrentePorCliente(cliente);
         contaPoupanca =  new ContaPoupancaDao().buscaContaPoupancaPorCliente(cliente);
    }
}
