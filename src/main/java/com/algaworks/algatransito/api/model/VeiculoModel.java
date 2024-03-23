package com.algaworks.algatransito.api.model;

import com.algaworks.algatransito.domain.model.StatusVeiculoEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class VeiculoModel {

    private Long id;
    private ProprietarioResumoModel proprietario;
    private String marca;
    private String modelo;
    private String numeroPlaca;
    private StatusVeiculoEnum status;
    private OffsetDateTime dataCadastro;
    private OffsetDateTime dataApreensao;

}
