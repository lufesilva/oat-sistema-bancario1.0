/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.unincor.web.core;


import br.com.unincor.web.model.domain.Agencia;
import br.com.unincor.web.model.domain.Cliente;
import br.com.unincor.web.model.domain.Conta;
import br.com.unincor.web.model.domain.ContaCorrente;
import br.com.unincor.web.model.domain.ContaPoupanca;
import br.com.unincor.web.model.domain.Emprestimo;
import br.com.unincor.web.model.domain.Endereco;
import br.com.unincor.web.model.domain.Gerente;
import br.com.unincor.web.model.domain.Transferencia;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 *
 * @author alunos
 */
public class HibernateManager {

    private static Session session;

    public static Session getSession() {
        if (session == null) {
            StandardServiceRegistry ssr = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml").build();
            //se tiver mais classer só colocar .adde ir adicionadno
            Metadata md = new MetadataSources(ssr)
                    .addAnnotatedClass(Agencia.class)
                    .addAnnotatedClass(Cliente.class)
                    .addAnnotatedClass(Conta.class)
                    .addAnnotatedClass(ContaCorrente.class)
                    .addAnnotatedClass(ContaPoupanca.class)
                    .addAnnotatedClass(Emprestimo.class)
                    .addAnnotatedClass(Endereco.class)
                    .addAnnotatedClass(Gerente.class)
                    .addAnnotatedClass(Transferencia.class)
                    .getMetadataBuilder().build();
            SessionFactory sessionFactory = md.getSessionFactoryBuilder().build();
            session = sessionFactory.getCurrentSession();
        }
        return session;
    }

    public static EntityManager getEntityManager() {
        Session mySession = getSession();
        if (!mySession.getTransaction().isActive()) {
            mySession.beginTransaction();
        }
        return mySession.getEntityManagerFactory().createEntityManager();
    }

}
