package com.example.frete.repository;

import com.example.frete.model.TableFreightModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TableFreightRepository extends JpaRepository<TableFreightModel, Long> {

    @Query(value ="select * from tabela_frete p where :cep between p.faixa_cep_inicio and p.faixa_cep_fim;", nativeQuery = true)
    List<Optional<TableFreightModel>> findCepIfExists (@Param("cep") String cep);

    @Query(value = "select * from tabela_frete p where p.faixa_peso = :weight and :cep between p.faixa_cep_inicio and p.faixa_cep_fim;", nativeQuery = true)
    TableFreightModel findFinalPrice (@Param("cep") String cep, @Param("weight") String weight);

}





