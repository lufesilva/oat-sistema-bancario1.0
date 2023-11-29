package br.com.unincor.web.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contas")
@EqualsAndHashCode(of = "id")
public class Conta implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numero;
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    private Boolean enable;
  //  private Double limite;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    
//    @OneToMany(mappedBy = "conta")
//    private List<ContaCorrente> contaCorrente;
//    
//    @OneToMany(mappedBy = "conta")
//    private List<ContaPoupanca> contaPoupanca;
    
    @ManyToOne
    @JoinColumn(name = "id_agencia")
    private Agencia agencia;
    
    @OneToMany(mappedBy = "conta")
    private Set<Emprestimo> emprestimos = new HashSet<>();
    
    @OneToMany(mappedBy = "destinatario")
    private Set<Transferencia> transferenciasD = new HashSet<>();
    
    @OneToMany(mappedBy = "remetente")
    private Set<Transferencia> transferenciasT = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name = "id_gerente")
    private Gerente gerente;
    
}
