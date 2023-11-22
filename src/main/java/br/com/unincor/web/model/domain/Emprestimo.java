package br.com.unincor.web.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
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
@Table(name = "emprestimos")
@EqualsAndHashCode(of = "id")
public class Emprestimo implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double valor;
    @Column(name = "data_final")
    private LocalDate dataFinal;
    @Column(name = "quantidade_parcelas")
    private Integer quantidadeParcelas;
    @Column(name = "valor_final")
    private Double valorFinal;
    
    @ManyToOne
    @JoinColumn(name = "id_conta")
    private Conta conta;
}
