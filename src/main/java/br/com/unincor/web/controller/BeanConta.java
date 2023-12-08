package br.com.unincor.web.controller;

import br.com.unincor.web.model.dao.AgenciaDao;
import br.com.unincor.web.model.dao.ClienteDao;
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
import br.com.unincor.web.view.utils.Mensagens;
import java.util.ArrayList;
import java.util.Arrays;
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
    private Conta contaDestino;

    public BeanConta() {
        super(new ContaDao());
    }

    @PostConstruct
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
        buscaContaComNumero();
        valor += sa;
        conta.setSaldo(valor);
        conta.setEnable(Boolean.TRUE);
        new ContaDao().save(conta);
        buscar();
        cancelar();
        //PrimeFaces.current().executeScript("PF('dlg3').hide()");//fechar o dialog 
    }

//    @Override
//    public void salvar() {
//        buscaContaPorNumero();
//
//        valor += sa;
//        contaDestino.setSaldo(valor);
//        conta.setSaldo(conta.getSaldo() - valor);
//        new ContaDao().save(conta);
//        buscar();
//        cancelar();
//
//    }
    public void salvarTransContaPoupanca() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        var clienteLogado = new ClienteDao().findById((Long) session.getAttribute("clienteId"));

        var conta = new ContaDao().buscaContaPoupancaPorCliente(clienteLogado);
        buscaContaPorNumero();
        
        if(conta.getSaldo() >= valor){
            valor += sa;
        contaDestino.setSaldo(contaDestino.getSaldo() + valor);
        conta.setSaldo(conta.getSaldo() - valor);
        contaDestino.setEnable(Boolean.TRUE);
        new ContaDao().save(conta);
        new ContaDao().save(contaDestino);
        
        buscar();
        cancelar();
        }else{
            Mensagens.erro(facesContext, "Saldo Insuficiente");
        }
        
        

    }

    public void salvarTransContaCorrente() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        var clienteLogado = new ClienteDao().findById((Long) session.getAttribute("clienteId"));

        var conta = new ContaDao().buscaContaCorrentePorCliente(clienteLogado);
        buscaContaPorNumero();

        valor += sa;
        if(conta.getSaldo() >= valor){
            contaDestino.setSaldo(contaDestino.getSaldo() + valor);
        conta.setSaldo(conta.getSaldo() - valor);
        contaDestino.setEnable(Boolean.TRUE);
        new ContaDao().save(conta);
        new ContaDao().save(contaDestino);
        buscar();
        cancelar();
        }else {
            Mensagens.erro(facesContext,"Saldo Insuficiente" );
        }
        

    }

    public void buscarContaCorrente(Cliente cliente) {
        this.contasCorrente = new ContaCorrenteDao().buscaContaCliente(cliente);

    }

    public void buscarContaPoupanca(Cliente cliente) {
        this.contasPoupanca = new ContaPoupancaDao().buscaContaCliente(cliente);

    }

    public void salvarContaCorrente(Cliente cliente) {
        var contas = new ContaCorrenteDao().buscaContaCliente(cliente);
        if (contas.size() >= 1) {
            cancelar();
        } else {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            var gerenteLogado = new GerenteDao().findById((Long) session.getAttribute("gerenteId"));
            contaCorrente.setSaldo(0.0);
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
        var contas = new ContaPoupancaDao().buscaContaCliente(cliente);
        if (contas.size() >= 1) {
            cancelar();
        } else {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            var gerenteLogado = new GerenteDao().findById((Long) session.getAttribute("gerenteId"));
            contaPoupanca.setSaldo(0.0);
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
        contaDestino = new ContaDao().buscaContaPorNumero(numero);
        sa = contaDestino.getSaldo();
        System.out.println(sa);
    }

    public void buscaContaComNumero() {
        conta = new ContaDao().buscaContaPorNumero(numero);
        sa = conta.getSaldo();
        System.out.println(sa);
    }
    
    @Override 
    public void remover(Conta conta){
        conta.setEnable(Boolean.FALSE);
        new ContaDao().save(conta);
    }
    
    @Override
    public void buscar(){
        values = new ContaDao().buscaContaAtiva();
    }
}
