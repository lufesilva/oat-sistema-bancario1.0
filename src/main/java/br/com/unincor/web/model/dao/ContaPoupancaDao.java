
package br.com.unincor.web.model.dao;

import br.com.unincor.web.model.domain.Cliente;
import br.com.unincor.web.model.domain.Conta;
import br.com.unincor.web.model.domain.ContaPoupanca;
import jakarta.persistence.Query;
import java.util.List;


public class ContaPoupancaDao extends GenericDao<ContaPoupanca, Long>{
      public List<ContaPoupanca> buscaContaCliente(Cliente cliente){
        String sql = "from Conta c where c.cliente = :cliente and c.tipo = POUPANCA and c.enable = true";
        
        Query query = getEntityManager().createQuery(sql, Cliente.class)
                .setParameter("cliente", cliente);
        return query.getResultList();

    }
      
      public ContaPoupanca buscaContaPoupancaPorCliente(Cliente cliente){
        String sql = "from Conta c where c.cliente = :cliente and c.tipo = POUPANCA";
        
        Query query = getEntityManager().createQuery(sql, Cliente.class)
                .setParameter("cliente", cliente);
         var resultado = query.getResultList();
        return resultado.isEmpty() ? null : (ContaPoupanca) resultado.get(0);
    }
    
}
