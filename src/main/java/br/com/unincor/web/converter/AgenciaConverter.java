/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.unincor.web.converter;

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
        if (value != null && !value.isBlank()){
            var agencia = new AgenciaDao().findById(Long.valueOf(value));
            return agencia;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value instanceof Agencia agencia) {
            return agencia.getId().toString();
        }
        return null;
    }

}
