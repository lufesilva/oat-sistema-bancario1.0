/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.unincor.web.controller;

import br.com.unincor.web.model.dao.AgenciaDao;
import br.com.unincor.web.model.dao.ContaDao;
import br.com.unincor.web.model.dao.GenericDao;
import br.com.unincor.web.model.dao.GerenteDao;
import br.com.unincor.web.model.domain.Agencia;
import br.com.unincor.web.model.domain.Cliente;
import br.com.unincor.web.model.domain.Conta;
import br.com.unincor.web.model.domain.Gerente;
import br.com.unincor.web.model.domain.Tipo;
import br.com.unincor.web.view.utils.Criptografar;
import br.com.unincor.web.view.utils.Mensagens;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class BeanConta extends AbstractBean<Conta> {

    private String senhaLogin;
    private String confirmarSenha;
    private String emailLogin;
    private List<Gerente> gerentes = new ArrayList<>();

    public BeanConta(GenericDao genericDao) {
        super(genericDao);
    }

    @Override
    void init() {
        this.buscar();

    }

    @Override
    public void novo() {
        this.value = new Conta();

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

    public List<Tipo> getTipos() {
        return Arrays.asList(Tipo.values());
    }

    public List<Agencia> getAgencias() {
        return new AgenciaDao().findAll();
    }

}
