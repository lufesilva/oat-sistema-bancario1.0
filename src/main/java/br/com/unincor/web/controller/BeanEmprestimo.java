package br.com.unincor.web.controller;

import br.com.unincor.web.model.dao.ClienteDao;
import br.com.unincor.web.model.dao.ContaDao;
import br.com.unincor.web.model.dao.EmprestimoDao;
import br.com.unincor.web.model.domain.Emprestimo;
import br.com.unincor.web.model.domain.Status;
import br.com.unincor.web.view.utils.Mensagens;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
        // emprestimo = new Emprestimo();
        novo();
//        this.buscar();
        buscarEmprestimos();

    }

    @Override
    public void novo() {
        this.emprestimo = new Emprestimo();
    }

    public void buscarEmprestimos() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        var clienteLogado = new ClienteDao().findById((Long) session.getAttribute("clienteId"));

        var conta = new ContaDao().buscaContaCorrentePorCliente(clienteLogado);

        this.emprestimos = new ContaDao().buscaEmprestimo(conta);
    }

    @Override
    public void salvar() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        var clienteLogado = new ClienteDao().findById((Long) session.getAttribute("clienteId"));

        var conta = new ContaDao().buscaContaCorrentePorCliente(clienteLogado);

        if (emprestimo.getQuantidadeParcelas() > 48 || conta.getLimite() < emprestimo.getValorFinal() || verificaEmprestimo()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível realizar o empréstimo!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            System.out.println("Não foi possível realizar o empréstimo!");
            
        } else {
            conta.setSaldo(conta.getSaldo() + emprestimo.getValorFinal());
            for (int i = 0; i < emprestimo.getQuantidadeParcelas(); i++) {
                emprestimo.setValorFinal(emprestimo.getValorFinal() * 1.0339);

            }

            emprestimo.setValor(emprestimo.getValorFinal() / emprestimo.getQuantidadeParcelas());
            for (int i = 1; i <= emprestimo.getQuantidadeParcelas(); i++) {
                if (i == 1) {
                    emprestimo.setDataFinal(LocalDate.now().plusMonths(i));
                } else {
                    emprestimo.setDataFinal(emprestimo.getDataFinal().plusMonths(i));
                }

                emprestimo.setConta(conta);
                emprestimo.setParcela(i);
                emprestimo.setStatus(Status.Pendente);
                new EmprestimoDao().save(emprestimo);
                buscar();
                cancelar();
            }
        }

        buscar();
        cancelar();

    }

    public Boolean verificaEmprestimo() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        var clienteLogado = new ClienteDao().findById((Long) session.getAttribute("clienteId"));

        var conta = new ContaDao().buscaContaCorrentePorCliente(clienteLogado);
        var emprestimosPendentes = new ContaDao().buscaEmprestimoPorCliente(conta);

        if (emprestimosPendentes.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void pagarParcela(Emprestimo emprestimo) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        var clienteLogado = new ClienteDao().findById((Long) session.getAttribute("clienteId"));
        var conta = new ContaDao().buscaContaCorrentePorCliente(clienteLogado);
       
        if (conta.getSaldo() >= emprestimo.getValor()) {
            
            emprestimo.setStatus(Status.Pago);
            conta.setSaldo(conta.getSaldo() - emprestimo.getValor());
            new EmprestimoDao().save(emprestimo);
            new ContaDao().save(conta);
        } else {
           
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Saldo Insuficiente", null);
            FacesContext.getCurrentInstance().addMessage(null, message);

            System.out.println("Saldo insuficiente");
        }

    }

}
