package br.com.unincor.web.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contas_poupanca")
public class ContaPoupanca extends Conta implements Serializable {

    private Double renda;
    //private String numero;
    
//    @ManyToOne
//    @JoinColumn(name = "id_conta")
//    private Conta conta;
}
