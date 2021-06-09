package br.com.hireit.projetohireIt.tables;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Contratos")
public class ContratosTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contrato")
    private Integer idContrato;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    @Column(name = "valor_hora")
    private double valorHora;

    @ManyToOne
    @JoinColumn(name = "fk_oferta")
    private OfertasTable ofertas;

    @ManyToOne
    @JoinColumn(name = "fk_demanda")
    private DemandasTable demandas;

    public ContratosTable(Integer idContrato, LocalDateTime dataInicio, LocalDateTime dataFim, double valorHora, OfertasTable ofertas, DemandasTable demandas) {
        this.idContrato = idContrato;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorHora = valorHora;
        this.ofertas = ofertas;
        this.demandas = demandas;
    }

    public ContratosTable() {
    }

    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Integer idContrato) {
        this.idContrato = idContrato;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public double getValorHora() {
        return valorHora;
    }

    public void setValorHora(double valorHora) {
        this.valorHora = valorHora;
    }

    public OfertasTable getOfertas() {
        return ofertas;
    }

    public void setOfertas(OfertasTable ofertas) {
        this.ofertas = ofertas;
    }

    public DemandasTable getDemandas() {
        return demandas;
    }

    public void setDemandas(DemandasTable demandas) {
        this.demandas = demandas;
    }

    @Override
    public String toString() {
        return "ContratosTable{" +
                "idContrato=" + idContrato +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", valorHora=" + valorHora +
                ", ofertas=" + ofertas +
                ", demandas=" + demandas +
                '}';
    }
}
