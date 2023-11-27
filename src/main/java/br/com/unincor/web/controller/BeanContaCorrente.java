
package br.com.unincor.web.controller;

import br.com.unincor.web.model.dao.ContaDao;
import br.com.unincor.web.model.dao.GerenteDao;
import br.com.unincor.web.model.domain.Conta;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@ViewScoped
@Getter
@Setter
public class BeanContaCorrente extends AbstractBean<Conta>{
      private List<Conta> contas = new ArrayList<>();
    private Conta conta;

    public BeanContaCorrente() {
        super(new ContaDao());
    }

  

    @Override
    void init() {
        this.buscar();

    }

    @Override
    public void novo() {
        this.value = new Conta();

    }
    
    
       public void criaContaCorrente() {
           conta = new Conta();
            
       
    }
    
    

    @Override
    public void salvar() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        var gerenteLogado = new GerenteDao().findById((Long) session.getAttribute("gerenteId"));
        this.value.setGerente(gerenteLogado);

        new ContaDao().save(value);
        buscar();
        cancelar();
        //PrimeFaces.current().executeScript("PF('dlg3').hide()");//fechar o dialog 
    }
}
