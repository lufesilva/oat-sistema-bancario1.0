package br.com.unincor.web.controller;

import br.com.unincor.web.model.dao.ClienteDao;
import br.com.unincor.web.model.dao.ContaDao;
import br.com.unincor.web.model.dao.EmprestimoDao;
import br.com.unincor.web.model.dao.GerenteDao;
import br.com.unincor.web.model.domain.Emprestimo;
import br.com.unincor.web.model.domain.Status;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class BeanEmprestimo extends AbstractBean<Emprestimo> {

    private Emprestimo emprestimo;
    private List<Emprestimo> emprestimos = new ArrayList<>();

    public BeanEmprestimo() {
        super(new EmprestimoDao());
    }

    @PostConstruct
    void init() {
        novo();
        this.buscar();
    }

    @Override
    public void novo() {
        this.emprestimo = new Emprestimo();
    }

    @Override
    public void salvar() {
        if (emprestimo.getQuantidadeParcelas() > 48) {
            cancelar();
        }
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        var clienteLogado = new ClienteDao().findById((Long) session.getAttribute("clienteId"));
        
        var conta = new ContaDao().buscaContaCorrentePorCliente(clienteLogado);
        
        if(conta.getLimite() < emprestimo.getValorFinal()){
            cancelar();
        } else {
        }
        
        for (int i = 0; i < emprestimo.getQuantidadeParcelas(); i++) {
                emprestimo.setValorFinal(emprestimo.getValorFinal() * 1.0339);
                
        }
        
        emprestimo.setValor(emprestimo.getValorFinal() / emprestimo.getQuantidadeParcelas());
        for(int i = 0; i < emprestimo.getQuantidadeParcelas(); i++){
            if(i == 0){
                emprestimo.setDataFinal(LocalDate.now().plusMonths(i));
            }else{
                emprestimo.setDataFinal(emprestimo.getDataFinal().plusMonths(i));
            }
            
            emprestimo.setParcela(i);
            emprestimo.setStatus(Status.Pendente);
            new EmprestimoDao().save(emprestimo);
        }
        
        buscar();
        cancelar();

    }
}