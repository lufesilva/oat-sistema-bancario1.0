package br.com.unincor.web.controller;

import br.com.unincor.web.model.dao.AgenciaDao;
import br.com.unincor.web.model.dao.ContaCorrenteDao;
import br.com.unincor.web.model.dao.ContaDao;
import br.com.unincor.web.model.dao.ContaPoupancaDao;
import br.com.unincor.web.model.dao.GerenteDao;
import br.com.unincor.web.model.domain.Agencia;
import br.com.unincor.web.model.domain.Cliente;
import br.com.unincor.web.model.domain.Conta;
import br.com.unincor.web.model.domain.ContaCorrente;
import br.com.unincor.web.model.domain.ContaPoupanca;
import br.com.unincor.web.model.domain.Gerente;
import br.com.unincor.web.model.domain.Tipo;
import java.util.ArrayList;
import java.util.Arrays;
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
public class BeanConta extends AbstractBean<Conta> {

    private List<Gerente> gerentes = new ArrayList<>();
    private List<Conta> contas = new ArrayList<>();
    private List<ContaCorrente> contasCorrente = new ArrayList<>();
    private List<ContaPoupanca> contasPoupanca = new ArrayList<>();
    private Conta conta;
    private ContaCorrente contaCorrente;
    private ContaPoupanca contaPoupanca;
    private Agencia agenciaSelecionada;
    private Integer numero;
    private Double valor;
    private Double sa;

    public BeanConta() {
        super(new ContaDao());
    }

    @Override
    void init() {
        conta = new Conta();
        this.buscar();

    }

    @Override
    public void novo() {
        this.conta = new Conta();
        this.contaCorrente = new ContaCorrente();
        this.contaPoupanca = new ContaPoupanca();

    }

    @Override
    public void salvar() {
        buscaContaPorNumero();
        valor += sa;
        conta.setSaldo(valor);
        new ContaDao().save(conta);
        buscar();
        cancelar();
        //PrimeFaces.current().executeScript("PF('dlg3').hide()");//fechar o dialog 
    }

    public void buscarContaCorrente(Cliente cliente) {
        this.contasCorrente = new ContaCorrenteDao().buscaContaCliente(cliente);

    }

    public void buscarContaPoupanca(Cliente cliente) {
        this.contasPoupanca = new ContaPoupancaDao().buscaContaCliente(cliente);

    }

    public void salvarContaCorrente(Cliente cliente) {
        var contas = new ContaPoupancaDao().buscaContaCliente(cliente);
        if (contas.size() >= 1) {
            cancelar();
        } else {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            var gerenteLogado = new GerenteDao().findById((Long) session.getAttribute("gerenteId"));
            contaCorrente.setAgencia(agenciaSelecionada);
            contaCorrente.setEnable(Boolean.TRUE);
            contaCorrente.setCliente(cliente);
            contaCorrente.setGerente(gerenteLogado);
            contaCorrente.setTipo(Tipo.CORRENTE);
            new ContaDao().save(contaCorrente);
            buscar();
            cancelar();
        }

    }

    public void salvarContaPoupanca(Cliente cliente) {
        var contas = new ContaCorrenteDao().buscaContaCliente(cliente);
        if (contas.size() >= 1) {
            cancelar();
        } else {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            var gerenteLogado = new GerenteDao().findById((Long) session.getAttribute("gerenteId"));
            contaPoupanca.setAgencia(agenciaSelecionada);
            contaPoupanca.setEnable(Boolean.TRUE);
            contaPoupanca.setCliente(cliente);
            contaPoupanca.setGerente(gerenteLogado);
            contaPoupanca.setTipo(Tipo.POUPANCA);
            new ContaDao().save(contaPoupanca);
            buscar();
            cancelar();
        }

    }

    public List<Tipo> getTipos() {
        return Arrays.asList(Tipo.values());
    }

    public List<Agencia> getAgencias() {
        return new AgenciaDao().findAll();
    }

    public void buscaContaPorNumero() {
        conta = new ContaDao().buscaContaPorNumero(numero);
        sa = conta.getSaldo();
        System.out.println(sa);
    }

}
