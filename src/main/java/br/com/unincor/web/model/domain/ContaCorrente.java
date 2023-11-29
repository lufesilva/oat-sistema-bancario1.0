package br.com.unincor.web.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "contas_corrente")
public class ContaCorrente extends Conta implements Serializable {

  //  private Double valor;
    private Double limite;
 //   private String numero;
//    
//    @ManyToOne
//    @JoinColumn(name = "id_conta")
//    private Conta conta;
}
