package br.com.hireit.projetohireIt.controller;

import br.com.hireit.projetohireIt.entity.Empresa;
import br.com.hireit.projetohireIt.entity.UsuarioLogin;
import br.com.hireit.projetohireIt.repository.ContratoRepository;
import br.com.hireit.projetohireIt.repository.UsuarioRepository;
import br.com.hireit.projetohireIt.tables.UsuariosTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    public List<UsuariosTable> usuariosLogados = new ArrayList<>();
    private Empresa empresa = new Empresa();

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/")
    public ResponseEntity postUsuario(@RequestBody UsuariosTable usuario){
        System.out.println(usuario.toString());
        try{
            usuario.setClassificacao(0.0);
            usuarioRepository.save(usuario);
        }catch (Exception e){
            return ResponseEntity.status(400).body("Falha ao cadastrar usuário!");
        }

        return ResponseEntity.status(201).body("Usuário cadastrado com sucesso!");
    }

    @GetMapping("/")
    public ResponseEntity getUsuarios(){
        return ResponseEntity.status(200).body(usuarioRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getUsuario(@PathVariable int id){
        if(usuarioRepository.existsById(id)){
            return ResponseEntity.status(200).body(usuarioRepository.findById(id));
        }else{
            return ResponseEntity.status(204).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity putUsuario(@PathVariable int id, @RequestBody UsuariosTable usuario){
        try{
            return ResponseEntity.status(204).body(usuarioRepository.findById(id)
                    .map(record -> {
                                record.setNome(usuario.getNome());
                                record.setEmail(usuario.getEmail());
                                record.setDescricao(usuario.getDescricao());
                                record.setClassificacao(usuario.getClassificacao());
                                record.setTelefone(usuario.getTelefone());
                                record.setLocalizacao(usuario.getLocalizacao());
                                UsuariosTable updated = usuarioRepository.save(record);
                                return "Dados do usuário alterados com sucesso!";
                            }));
        }catch (Exception e){
            return ResponseEntity.status(400).body("Falha ao atualizar os dados do usuário!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUsuario(@PathVariable int id){
        try{
            usuarioRepository.deleteById(id);
        }catch (Exception e){
            return ResponseEntity.status(400).body("Falha ao deletar usuário!");
        }

        return ResponseEntity.status(204).body("Usuário deletado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UsuarioLogin usuario){
        UsuariosTable usuarios = usuarioRepository.findByEmailAndSenha(usuario.getEmail(),usuario.getSenha());
        String respostaLogin = empresa.logar(usuarios, usuariosLogados);
        if(respostaLogin.equals("Usuário logado com sucesso!")){
            return ResponseEntity.status(200).body("Usuário logado com sucesso");
        }else{
            return ResponseEntity.status(400).body("Usuário ou Senha incorretos");
        }
    }

    @GetMapping("/email")
    public ResponseEntity getDadosUsuarios(@RequestParam String email){
        return ResponseEntity.status(200).body(usuarioRepository.findUsuarioByEmail(email));
    }

}
