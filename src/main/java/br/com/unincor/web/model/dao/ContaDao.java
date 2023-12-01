package br.com.unincor.web.model.dao;

import br.com.unincor.web.model.domain.Cliente;
import br.com.unincor.web.model.domain.Conta;
import jakarta.persistence.Query;
import java.util.List;


public class ContaDao extends GenericDao<Conta, Long>{
    public List<Conta> buscaContaCliente(Cliente cliente){
        String sql = "from Conta c where c.cliente = :cliente";
        
        Query query = getEntityManager().createQuery(sql, Cliente.class)
                .setParameter("cliente", cliente);
        return query.getResultList();

    }
}
