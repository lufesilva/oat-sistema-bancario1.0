package br.com.unincor.web.controller;

import br.com.unincor.web.model.dao.EmprestimoDao;
import br.com.unincor.web.model.domain.Emprestimo;
import java.util.ArrayList;
import java.util.List;
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
    private List<Emprestimo> emprestimos = new ArrayList<>();

    public BeanEmprestimo() {
        super(new EmprestimoDao());
    }

    @Override
    void init() {
        emprestimo = new Emprestimo();
        this.buscar();
    }

    @Override
    public void novo() {
         this.emprestimo = new Emprestimo();
    }
    
    @Override
    public void salvar() {
       
        new EmprestimoDao().save(emprestimo);
        buscar();
        cancelar();
       
    }
}
