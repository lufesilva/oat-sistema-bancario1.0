
package br.com.unincor.web.model.dao;

import br.com.unincor.web.model.domain.Gerente;
import jakarta.persistence.Query;


public class GerenteDao extends GenericDao<Gerente, Long>{
      public Gerente buscarGerentePorLogin(String login) {
        String sql = "from Gerente a where a.email = :email";
        
          Query query = getEntityManager().createQuery(sql, Gerente.class)
                .setParameter("email", login);
        var resultado = query.getResultList();
        return resultado.isEmpty() ? null : (Gerente) resultado.get(0);
    }
}
