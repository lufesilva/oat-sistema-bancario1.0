package br.com.unincor.web.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "clientes")
@EqualsAndHashCode(of = "id")
public class Cliente implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String senha;
    
    @OneToMany(mappedBy = "cliente")
    private Set<Conta> contas = new HashSet<>();
    
    
    @ManyToMany
    @JoinTable
            (
                    name = "clientes_enderecos",
                    joinColumns = @JoinColumn(name = "cliente_id"),
                    inverseJoinColumns = @JoinColumn(name = "endereco_id")
            )
    private Set<Endereco> enderecos = new HashSet<>();
}
