package br.com.hireit.projetohireIt.controller;

import br.com.hireit.projetohireIt.repository.*;
import br.com.hireit.projetohireIt.tables.ContratosTable;
import br.com.hireit.projetohireIt.tables.PropostasTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contrato")
public class ContratoController {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private PropostaRepository propostaRepository;

    @PostMapping
    public ResponseEntity postContrato(@RequestBody ContratosTable contrato){
        contrato.setDataInicio(java.time.LocalDateTime.now());
        contratoRepository.save(contrato);

        int fkDemanda = contrato.getDemandas().getIdDemanda();

        List<PropostasTable> proposta = propostaRepository.findProposta(
                fkDemanda
        );

        propostaRepository.deleteAll(proposta);

        return ResponseEntity.status(201).build();
    }
}
