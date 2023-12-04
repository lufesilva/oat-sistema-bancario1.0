
package br.com.unincor.web.model.dao;

import br.com.unincor.web.model.domain.Cliente;
import br.com.unincor.web.model.domain.Conta;
import br.com.unincor.web.model.domain.ContaCorrente;
import jakarta.persistence.Query;
import java.util.List;


public class ContaCorrenteDao extends GenericDao<ContaCorrente, Object>{
     public List<ContaCorrente> buscaContaCliente(Cliente cliente){
        String sql = "from Conta c where c.cliente = :cliente and c.tipo = CORRENTE";
        
        Query query = getEntityManager().createQuery(sql, Cliente.class)
                .setParameter("cliente", cliente);
        return query.getResultList();

    }
     
      public ContaCorrente buscaContaCorrentePorCliente(Cliente cliente){
        String sql = "from Conta c where c.cliente = :cliente and c.tipo = CORRENTE";
        
        Query query = getEntityManager().createQuery(sql, Cliente.class)
                .setParameter("cliente", cliente);
         var resultado = query.getResultList();
        return resultado.isEmpty() ? null : (ContaCorrente) resultado.get(0);
    }
      }
