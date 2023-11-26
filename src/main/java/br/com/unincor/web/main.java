package br.com.unincor.web;

import br.com.unincor.web.model.dao.ClienteDao;
import br.com.unincor.web.model.dao.GerenteDao;
import br.com.unincor.web.model.domain.Cliente;
import br.com.unincor.web.model.domain.ContaCorrente;
import br.com.unincor.web.model.domain.Gerente;
import br.com.unincor.web.view.utils.Criptografar;


public class main {
    public static void main(String[] args) {
    Cliente cliente = new Cliente(null, "Matheus", "matheus@gmail.com", "5555555", Criptografar.encryp("123"), null, null);
        ClienteDao clienteDao = new ClienteDao();
        
        clienteDao.save(cliente);

        Gerente g = new Gerente(null, "teste", "teste", "111", Criptografar.encryp("123"), null, null);
        GerenteDao gDao =  new GerenteDao();
        gDao.save(g);
        
    }
}
