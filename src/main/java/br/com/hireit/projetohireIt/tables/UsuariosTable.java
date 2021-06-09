package br.com.hireit.projetohireIt.tables;

import javax.persistence.*;

@Entity
@Table(name = "Usuarios")
public class UsuariosTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    protected int idUsuario;

    @Column(name = "nome")
    protected String nome;

    @Column(name = "email")
    protected String email;

    @Column(name = "senha")
    protected String senha;

    @Column(name = "descricao")
    protected String descricao;

    @Column(name = "classificacao")
    protected double classificacao;

    @Column(name = "telefone")
    protected String telefone;

    @ManyToOne
    @JoinColumn(name = "fk_localizacao")
    protected LocalizacoesTable localizacao;

    public UsuariosTable() {
    }

    public UsuariosTable(
            int idUsuario,
            String nome,
            String email,
            String senha,
            String descricao,
            double classificacao,
            String telefone,
            LocalizacoesTable localizacao
    ) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.descricao = descricao;
        this.classificacao = classificacao;
        this.telefone = telefone;
        this.localizacao = localizacao;
    }

    @Override
    public String toString() {
        return "UsuariosTable{" +
                "idUsuario=" + idUsuario +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", descricao='" + descricao + '\'' +
                ", classificacao=" + classificacao +
                ", telefone='" + telefone + '\'' +
                ", localizacao=" + localizacao +
                '}';
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(double classificacao) {
        this.classificacao = classificacao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalizacoesTable getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(LocalizacoesTable localizacao) {
        this.localizacao = localizacao;
    }
}
