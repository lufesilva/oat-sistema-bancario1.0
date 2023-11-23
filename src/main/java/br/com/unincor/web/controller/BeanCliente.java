/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.unincor.web.controller;

import br.com.unincor.web.model.dao.ClienteDao;
import br.com.unincor.web.model.dao.GenericDao;
import br.com.unincor.web.model.domain.Cliente;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class BeanCliente extends AbstractBean<Cliente>{

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
    
}
