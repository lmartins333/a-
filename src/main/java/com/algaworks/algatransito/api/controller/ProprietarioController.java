package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.domain.model.Proprietario;
import com.algaworks.algatransito.domain.repository.repository.ProprietarioRepository;
import com.algaworks.algatransito.domain.service.RegistroProprietarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/proprietarios")
public class ProprietarioController {

    private final RegistroProprietarioService registroProprietarioService;
    private final ProprietarioRepository proprietarioRepository;

    @GetMapping
    public List<Proprietario> listar() {
//        return proprietarioRepository.findByNomeContaining("a");

        return proprietarioRepository.findAll();

//        return entityManager.createQuery("from Proprietario", Proprietario.class).getResultList();

//        TypedQuery<Proprietario> fromProprietario = entityManager.createQuery("from Proprietario", Proprietario.class);
//
//        return fromProprietario.getResultList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proprietario> buscar(@PathVariable Long id) {
        return proprietarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

//        Optional<Proprietario> proprietarioOptional = proprietarioRepository.findById(id);
//
//        if (proprietarioOptional.isPresent()) {
//            return ResponseEntity
//                    .ok(proprietarioOptional.get());
//        }
//
//        return ResponseEntity
//                .notFound()
//                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Proprietario adicionar(@RequestBody @Valid Proprietario proprietario) {
        return registroProprietarioService.salvar(proprietario);
    }

    @PutMapping("/{proprietarioId}")
    public ResponseEntity<Proprietario> atualizar(
            @PathVariable Long proprietarioId,
            @RequestBody @Valid Proprietario proprietario
    ) {
        if (!proprietarioRepository.existsById(proprietarioId)) {
            return ResponseEntity.notFound().build();
        }

        proprietario.setId(proprietarioId);
        Proprietario proprietarioAtualizado = registroProprietarioService.salvar(proprietario);

        return ResponseEntity.ok(proprietarioAtualizado);
    }

    @DeleteMapping("/{proprietarioId}")
    public ResponseEntity<Void> remover(@PathVariable Long proprietarioId) {
        if (!proprietarioRepository.existsById(proprietarioId)) {
            return ResponseEntity.notFound().build();
        }

        registroProprietarioService.excluir(proprietarioId);

        return ResponseEntity.noContent().build();
    }

}
