/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.unincor.web.model.dao;

import br.com.unincor.web.model.domain.Cliente;
import br.com.unincor.web.model.domain.Conta;
import jakarta.persistence.Query;
import java.util.List;

/**
 *
 * @author Luiz
 */
public class ContaDao extends GenericDao<Conta, Long>{
    public List<Conta> buscaContaCliente(Cliente cliente){
        String sql = "from Conta c where c.cliente = :cliente";
        
        Query query = getEntityManager().createQuery(sql, Cliente.class)
                .setParameter("cliente", cliente);
        return query.getResultList();

    }
}
