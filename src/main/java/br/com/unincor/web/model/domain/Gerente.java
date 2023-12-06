/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.unincor.web.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gerentes")
@EqualsAndHashCode(of = "id")
public class Gerente implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String senha;
    private Boolean enable;
    
    @ManyToMany
    @JoinTable
            (
                    name = "gerentes_enderecos",
                    joinColumns = @JoinColumn(name = "gerente_id"),
                    inverseJoinColumns = @JoinColumn(name = "endereco_id")
            )
    private Set<Endereco> enderecos = new HashSet<>();
    
    @OneToMany(mappedBy = "gerente")
    private Set<Conta> contas = new HashSet<>();
}

