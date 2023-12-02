
package br.com.unincor.web.controller;

import br.com.unincor.web.model.dao.GenericDao;
import br.com.unincor.web.model.domain.Emprestimo;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@ViewScoped
@Getter
@Setter
public class BeanEmprestimo  extends AbstractBean<Emprestimo>  {
    
    private Emprestimo emprestimo;

    public BeanEmprestimo(GenericDao genericDao) {
        super(genericDao);
    }

    @Override
    void init() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void novo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
