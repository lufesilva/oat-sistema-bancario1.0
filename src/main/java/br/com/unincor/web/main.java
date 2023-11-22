/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.unincor.web;

import br.com.unincor.web.model.dao.ClienteDao;
import br.com.unincor.web.model.domain.Cliente;
import br.com.unincor.web.model.domain.ContaCorrente;

/**
 *
 * @author Luiz
 */
public class main {
    public static void main(String[] args) {
        Cliente cliente = new Cliente(null, "Teste", null, null, null, null, null);
        ClienteDao clienteDao = new ClienteDao();
        
        clienteDao.save(cliente);
        
    }
}
