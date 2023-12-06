
package br.com.unincor.web.model.dao;

import br.com.unincor.web.model.domain.Cliente;
import jakarta.persistence.Query;
import java.util.List;


public class ClienteDao extends GenericDao<Cliente, Long>{
      public Cliente buscarClientePorLogin(String login) {
        String sql = "from Cliente a where a.email = :email";
        
        Query query = getEntityManager().createQuery(sql, Cliente.class)
                .setParameter("email", login);
        var resultado = query.getResultList();
        return resultado.isEmpty() ? null : (Cliente) resultado.get(0);
    }
      
        public List<Cliente> buscaClienteAtivo(){
            String sql = "from Cliente c where c.enable = true";
        
        Query query = getEntityManager().createQuery(sql, Cliente.class);
        return query.getResultList();
        }
    
}
