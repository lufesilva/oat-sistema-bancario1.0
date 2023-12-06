package br.com.unincor.web.controller;

import br.com.unincor.web.model.dao.ClienteDao;
import br.com.unincor.web.model.dao.ContaDao;
import br.com.unincor.web.model.domain.Cliente;
import br.com.unincor.web.model.domain.Conta;
import br.com.unincor.web.view.utils.Criptografar;
import br.com.unincor.web.view.utils.Mensagens;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

@ManagedBean
@ViewScoped
@Getter
@Setter
public class BeanCliente extends AbstractBean<Cliente> {

    private List<Cliente> clientes = new ArrayList<>();
    private Cliente cliente;
    private String senhaLogin;
    private String emailLogin;
    private String confirmarSenha;
    private List<Conta> contas;
    private Cliente clienteTemplate;

    public BeanCliente() {
        super(new ClienteDao());
    }

//    @Override
//    void init() {
//        this.buscar();
//    }
    @PostConstruct
    public void init() {
        //this.cliente = new Cliente();
        buscar();
    }

    @Override
    public void novo() {
        this.cliente = new Cliente();
    }

    public void buscar() {
        clientes = new ClienteDao().buscaClienteAtivo();
    }

    public void editar(Cliente cliente) {

        this.cliente = cliente;
    }

    public void cancelar() {
        cliente = null;
    }
    
    @Override
    public void remover(Cliente cliente) {
        cliente.setEnable(Boolean.FALSE);
        new ClienteDao().save(cliente);
        buscar();

    }
    
    
    
    
    

    public void salvar() {
        if (!cliente.getSenha().equals(confirmarSenha)) {
            Mensagens.erro(FacesContext.getCurrentInstance(), "As senhas informadas não conferem!");
            return;
        }
        var senha = cliente.getSenha();
        var senhaCriptografada = Criptografar.encryp(senha);
        cliente.setSenha(senhaCriptografada);
        cliente.setEnable(Boolean.TRUE);
        new ClienteDao().save(cliente);
        cliente = new Cliente();
        buscar();
        cancelar();
        //PrimeFaces.current().executeScript("PF('dlg3').hide()");//fechar o dialog 
    }

    public void verificaSenha() {
        var clienteLogado = new ClienteDao().buscarClientePorLogin(emailLogin);
        if (!clienteLogado.getSenha().equals(Criptografar.encryp(senhaLogin))) {
            Mensagens.erro(FacesContext.getCurrentInstance(), "Login e/ou senha incorretos!");
            return;
        }

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
        request.getSession(true).setAttribute("clienteLogado", clienteLogado);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.setAttribute("clienteId", clienteLogado.getId());
        clienteTemplate = clienteLogado;

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath() + "/pages/cliente/inicio_cliente.jsf");
        } catch (IOException ex) {
            Logger.getLogger(BeanCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void logout() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
        HttpSession session = request.getSession(true);
        session.removeAttribute("clienteLogado");
        session.invalidate();

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath() + "/pages/login_cliente.jsf");
        } catch (IOException ex) {
            Logger.getLogger(BeanCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscaContaPorCliente(Cliente cliente) {
        this.contas = new ContaDao().buscaContaCliente(cliente);
    }

}
