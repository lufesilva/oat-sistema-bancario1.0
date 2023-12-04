package br.com.unincor.web.controller;

import br.com.unincor.web.model.dao.AgenciaDao;
import br.com.unincor.web.model.domain.Agencia;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@ViewScoped
@Getter
@Setter
public class BeanAgencia extends AbstractBean<Agencia> {

    private List<Agencia> agencias = new ArrayList<>();
    private Agencia agencia;

    public BeanAgencia() {
        super(new AgenciaDao());
    }

//    @Override
//    void init() {
//        this.buscar();
//    }
    @PostConstruct
    public void init() {
        buscar();
    }

    @Override
    public void novo() {
        this.agencia = new Agencia();
    }

    public void buscar() {
        agencias = new AgenciaDao().findAll();
    }

    public void salvar() {

        new AgenciaDao().save(agencia);
        agencia = new Agencia();
        buscar();
        cancelar();
        

    }

    public void editar(Agencia agencia) {

        this.agencia = agencia;
    }

}
