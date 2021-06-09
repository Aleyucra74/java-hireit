package br.com.hireit.projetohireIt.controller;

import br.com.hireit.projetohireIt.repository.LocalizacoesRepository;
import br.com.hireit.projetohireIt.tables.LocalizacoesTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/localizacoes")
public class LocalizacaoController {

    private LocalizacoesTable localizacao = new LocalizacoesTable();

    @Autowired
    private LocalizacoesRepository localizacoesRepository;

    @GetMapping("/")
    public ResponseEntity getLocalizacao(){
        return ResponseEntity.status(200).body(localizacoesRepository.findAll());
    }

    @PostMapping("/")
    public ResponseEntity postLocalizacao(@RequestBody LocalizacoesTable localizacao) {
        LocalizacoesTable localizacaoTable;
        try {
            localizacaoTable = localizacoesRepository.save(localizacao);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Falha ao cadastrar localização!");
        }

        return ResponseEntity.status(201).body(localizacaoTable);
    }

    @PutMapping("/{id}")
    public ResponseEntity putLocalizacao(@PathVariable int id, @RequestBody LocalizacoesTable localizacao){
        try{
            return ResponseEntity.status(204).body(localizacoesRepository.findById(id)
                    .map(record -> {
                        record.setUf(localizacao.getUf());
                        record.setCidade(localizacao.getCidade());
                        record.setCep(localizacao.getCep());
                        LocalizacoesTable updated = localizacoesRepository.save(record);
                        return "Dados do localização alterados com sucesso!";
                    }));
        }catch (Exception e){
            return ResponseEntity.status(500).body("Falha ao atualizar os dados da localização!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteLocalizacao(@PathVariable int id){
        try{
            localizacoesRepository.deleteById(id);
        }catch (Exception e){
            return ResponseEntity.status(400).body("Falha ao deletar localização!");
        }

        return ResponseEntity.status(204).body("Localização deletada com sucesso!");
    }

    @GetMapping("/cep")
    public ResponseEntity getCepByCep(@RequestParam String cep){
        Optional<LocalizacoesTable> localizacoesTable = localizacoesRepository.findByCep(cep);
        if (localizacoesTable.isPresent()){
            return ResponseEntity.status(200).body(localizacoesTable);
        }else {
            return ResponseEntity.status(400).build();
        }
    }

}
