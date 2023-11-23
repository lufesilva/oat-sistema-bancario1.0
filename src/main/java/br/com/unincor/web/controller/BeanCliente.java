/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.unincor.web.controller;

import br.com.unincor.web.model.dao.ClienteDao;
import br.com.unincor.web.model.domain.Cliente;
import br.com.unincor.web.view.utils.Criptografar;
import br.com.unincor.web.view.utils.Mensagens;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class BeanCliente extends AbstractBean<Cliente> {

    private String senhaLogin;
    private String emailLogin;

    public BeanCliente() {
        super(new ClienteDao());
    }

    @Override
    void init() {
        this.buscar();
    }

    @Override
    public void novo() {
        this.value = new Cliente();
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

}
