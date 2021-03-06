package br.com.hireit.projetohireIt.controller;

import br.com.hireit.projetohireIt.auxiliar.FilaObj;
import br.com.hireit.projetohireIt.auxiliar.PilhaObj;
import br.com.hireit.projetohireIt.repository.DemandaRepository;
import br.com.hireit.projetohireIt.repository.OfertaRepository;
import br.com.hireit.projetohireIt.repository.UsuarioRepository;
import br.com.hireit.projetohireIt.tables.DemandasTable;
import br.com.hireit.projetohireIt.tables.OfertasTable;
import br.com.hireit.projetohireIt.tables.UsuariosTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/import")
public class ImportacaoODController {

    PilhaObj<OfertasTable> ofertasPilha = new PilhaObj<>(100);
    PilhaObj<DemandasTable> demandasPilha = new PilhaObj<>(100);

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private DemandaRepository demandaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping()
    public ResponseEntity postAnexo(@RequestParam MultipartFile arquivo) throws IOException {
        String registro;
        String tipoRegistro;

        int contOfertas = 0;
        int contDemandas = 0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        BufferedReader entrada = new BufferedReader(
                new InputStreamReader(arquivo.getInputStream(), "UTF-8"));

        try {
            registro = entrada.readLine();

            while (registro != null) {
                tipoRegistro = registro.substring(0, 2);
                if (tipoRegistro.equals("00")) {
                    System.out.println("Header encontrado!");
                    System.out.println(LocalDateTime.now());

                } else if (tipoRegistro.equals("01")) {
                    boolean deuRuim = false;
                    int qtdOfertas = Integer.parseInt(registro.substring(2, 12));
                    int qtdDemandas = Integer.parseInt(registro.substring(12, 22));

                    if (qtdOfertas == contOfertas) {
                        System.out.println("Quantidade de ofertas gravadas compat??vel com quantidade lida");
                    } else {
                        System.out.println("Quantidade de ofertas gravadas n??o confere com quantidade lida");

                        while(!ofertasPilha.isEmpty()){
                            ofertaRepository.delete(ofertasPilha.pop());
                        }

                        deuRuim = true;
                    }

                    if (qtdDemandas == contDemandas) {
                        System.out.println("Quantidade de demandas gravadas compat??vel com quantidade lida");
                    } else {
                        System.out.println("Quantidade de demandas gravadas n??o confere com quantidade lida");

                        while(!demandasPilha.isEmpty()){
                            demandaRepository.delete(demandasPilha.pop());
                        }

                        deuRuim = true;
                    }
                    if(deuRuim){
                        return ResponseEntity.status(400).build();
                    }

                } else if (tipoRegistro.equals("02")) {
                    OfertasTable novaOferta = new OfertasTable();

                    novaOferta.setDescricao(registro.substring(2, 302).trim());
                    novaOferta.setCreatedAt(LocalDateTime.parse(registro.substring(302, 321).replace('/', '-'), formatter));
                    novaOferta.setUsuario(usuarioRepository.findUsuarioByEmail(registro.substring(321, 366).trim()));

                    ofertaRepository.save(novaOferta);
                    ofertasPilha.push(novaOferta);

                    contOfertas++;

                } else if (tipoRegistro.equals("03")) {
                    DemandasTable novaDemanda = new DemandasTable();

                    novaDemanda.setTitulo(registro.substring(2, 47).trim());
                    novaDemanda.setDescricao(registro.substring(47, 348).trim());
                    novaDemanda.setCreatedAt(LocalDateTime.parse(registro.substring(348, 368).replace('/', '-'), formatter));
                    novaDemanda.setSalario(Double.parseDouble(registro.substring(368, 375)));
                    novaDemanda.setUsuario(usuarioRepository.findUsuarioByEmail(registro.substring(52, 59).trim()));

                    demandaRepository.save(novaDemanda);
                    demandasPilha.push(novaDemanda);

                    contDemandas++;

                } else {
                    System.out.println("Tipo de registro inv??lido");

                    return ResponseEntity.status(400).build();
                }

                registro = entrada.readLine();
            }

            entrada.close();
        } catch (IOException e) {
            System.err.printf("Erro ao ler arquivo: %s.\n", e.getMessage());
        }

        return ResponseEntity.status(201).build();
    }

}
