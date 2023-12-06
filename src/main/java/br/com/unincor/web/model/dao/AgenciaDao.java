/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.unincor.web.model.dao;

import br.com.unincor.web.model.domain.Agencia;
import jakarta.persistence.Query;
import java.util.List;

/**
 *
 * @author Luiz
 */
public class AgenciaDao extends GenericDao<Agencia, Long>{
    public List<Agencia> buscarAgenciaAtiva() {
        String sql = "from Agencia a where a.enable = true";
        
          Query query = getEntityManager().createQuery(sql, Agencia.class);
        return query.getResultList();
    }
}
