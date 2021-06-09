package br.com.hireit.projetohireIt.repository;

import br.com.hireit.projetohireIt.tables.ContratosTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoRepository extends JpaRepository<ContratosTable, Integer> {

}
