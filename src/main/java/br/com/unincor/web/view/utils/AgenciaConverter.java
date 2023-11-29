
package br.com.unincor.web.view.utils;

import br.com.unincor.web.model.dao.AgenciaDao;
import br.com.unincor.web.model.domain.Agencia;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter("agenciaConverter")
public class AgenciaConverter implements Converter{

  @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isBlank()) {
            var agencia = new AgenciaDao().findById(Long.valueOf(value));
            return agencia;

        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object velue) {
        if (velue instanceof Agencia) {
            Agencia agencia = (Agencia) velue;
            return agencia.getId().toString();
        }
        return null;
    }
    
}
