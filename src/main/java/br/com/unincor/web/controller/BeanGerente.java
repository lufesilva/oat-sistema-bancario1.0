package br.com.unincor.web.controller;

import br.com.unincor.web.model.dao.GerenteDao;
import br.com.unincor.web.model.domain.Gerente;
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

@ManagedBean
@ViewScoped
@Getter
@Setter
public class BeanGerente extends AbstractBean<Gerente> {

    private String senhaLogin;
    private String confirmarSenha;
    private String emailLogin;
    private Gerente gerente;
    private List<Gerente> gerentes = new ArrayList<>();

    public BeanGerente() {
        super(new GerenteDao());
    }

//    @Override
//    void init() {
//        this.buscar();
//    }
    
    @PostConstruct 
    public void init() {
        buscar();
    }


    @Override
    public void novo() {
        this.value = new Gerente();
    }

    public void verificaSenha() {
        var gerenteLogado = new GerenteDao().buscarGerentePorLogin(emailLogin);
        if (!gerenteLogado.getSenha().equals(Criptografar.encryp(senhaLogin))) {
            Mensagens.erro(FacesContext.getCurrentInstance(), "Login e/ou senha incorretos!");
            return;
        }

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
        request.getSession(true).setAttribute("gerenteLogado", gerenteLogado);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.setAttribute("gerenteId", gerenteLogado.getId());

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath() + "/pages/gerente/inicio_gerente.jsf");
        } catch (IOException ex) {
            Logger.getLogger(BeanGerente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void logout() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
        HttpSession session = request.getSession(true);
        session.removeAttribute("gerenteLogado");
        session.invalidate();

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath() + "/pages/login_gerente.jsf");
        } catch (IOException ex) {
            Logger.getLogger(BeanGerente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void salvar() {
        if (!value.getSenha().equals(confirmarSenha)) {
            Mensagens.erro(FacesContext.getCurrentInstance(), "As senhas informadas não conferem!");
            return;
        }
        var senha = value.getSenha();
        var senhaCriptografada = Criptografar.encryp(senha);
        value.setSenha(senhaCriptografada);
        new GerenteDao().save(value);
        value = new Gerente();
        buscar();
        //PrimeFaces.current().executeScript("PF('dlg3').hide()");//fechar o dialog 
    }
    
    @Override
    public void buscar(){
        this.gerentes = new GerenteDao().findAll();
    }

}
