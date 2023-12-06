
package br.com.unincor.web.model.dao;

import br.com.unincor.web.model.domain.Gerente;
import jakarta.persistence.Query;
import java.util.List;


public class GerenteDao extends GenericDao<Gerente, Long>{
      public Gerente buscarGerentePorLogin(String login) {
        String sql = "from Gerente a where a.email = :email";
        
          Query query = getEntityManager().createQuery(sql, Gerente.class)
                .setParameter("email", login);
        var resultado = query.getResultList();
        return resultado.isEmpty() ? null : (Gerente) resultado.get(0);
    }
      
      public List<Gerente> buscaGerenteAtivo() {
        String sql = "from Gerente a where a.enable = true";
        
          Query query = getEntityManager().createQuery(sql, Gerente.class);
        return query.getResultList();
    }
}
