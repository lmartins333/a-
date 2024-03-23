package com.algaworks.algatransito.domain.model;

import com.algaworks.algatransito.domain.exception.NegocioException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    //@JoinColumn(name = "proprietario_id")
    // O Jakarta resolve automaticamente para o Id do Proprietario
    private Proprietario proprietario;

    private String marca;
    private String modelo;
    private String placa;

    @Enumerated(EnumType.STRING)
    private StatusVeiculoEnum status;

    private OffsetDateTime dataCadastro;
    private OffsetDateTime dataApreensao;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL)
    private List<Autuacao> autuacoes = new ArrayList<>();

    public Autuacao adicionarAutuacao(Autuacao autuacao) {
        autuacao.setDataOcorrencia(OffsetDateTime.now());
        autuacao.setVeiculo(this);
        getAutuacoes().add(autuacao);

        return autuacao;
    }

    public void apreender() {
        if (estaApreendido()) {
            throw new NegocioException("O Veiculo já se encontra apreendido");
        }

        setStatus(StatusVeiculoEnum.APREENDDIO);
        setDataApreensao(OffsetDateTime.now());
    }

    public void removerApreensao() {
        if (naoEstaApreendido()) {
            throw new NegocioException("Veículo não está apreendido");
        }

        setStatus(StatusVeiculoEnum.REGULAR);
        setDataApreensao(null);
    }

    public boolean estaApreendido() {
        return StatusVeiculoEnum.APREENDDIO.equals(getStatus());
    }

    public boolean naoEstaApreendido() {
        return !estaApreendido();
    }

}
