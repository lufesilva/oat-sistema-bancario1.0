/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.unincor.web.controller;

import br.com.unincor.web.model.dao.GenericDao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.ManagedBean;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractBean<T extends Serializable> 
        implements Serializable{
    
    protected List<T> values;
    protected T value;
    private final GenericDao genericDao;

    public AbstractBean(GenericDao genericDao) {
        this.genericDao = genericDao;
    }
    
    abstract void init();
    
    public void buscar() {
        this.values = genericDao.findAll();
    }
    
    public abstract void novo();
    
    public void cancelar() {
        this.value = null;
    }
    
    public void salvar() {
        genericDao.save(value);
        buscar();
        cancelar();
    }
    
    public void editar(T value) {
        this.value = value;
    }
    
    public void remover(T value) {
        genericDao.delete(value);
        buscar();
    }
}
