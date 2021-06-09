package br.com.hireit.projetohireIt.controller;

import br.com.hireit.projetohireIt.repository.TecnologiaOfertaRepository;
import br.com.hireit.projetohireIt.repository.TecnologiaRepository;
import br.com.hireit.projetohireIt.tables.TecnologiaOfertaTable;
import br.com.hireit.projetohireIt.tables.TecnologiasTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tecnologias")
public class TecnologiaController {

    @Autowired
    private TecnologiaRepository tecnologiaRepository;

    @Autowired
    private TecnologiaOfertaRepository tecnologiaOfertaRepository;

    @GetMapping
    public ResponseEntity getTecnologias(){
        List<TecnologiasTable> listaTecnologias = tecnologiaRepository.findAll();

        if(listaTecnologias.isEmpty()){
            return ResponseEntity.status(204).build();
        }else{
            return ResponseEntity.status(200).body(listaTecnologias);
        }
    }

    @GetMapping("/oferta/{idOferta}/")
    public ResponseEntity getTecnologiasOferta(@PathVariable int idOferta){
        List<TecnologiaOfertaTable> listaTecnologias = tecnologiaOfertaRepository.findByFkoferta(idOferta);

        if(listaTecnologias.isEmpty()){
            return ResponseEntity.status(204).build();
        }else{
            return ResponseEntity.status(200).body(listaTecnologias);
        }
    }

    @PostMapping("/oferta")
    public ResponseEntity postTecnologiasOferta(@RequestBody TecnologiaOfertaTable tecnologia){
            tecnologiaOfertaRepository.save(tecnologia);

        return ResponseEntity.status(201).build();
    }
}
