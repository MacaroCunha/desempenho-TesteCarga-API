package com.example.frete.repository;

import com.example.frete.model.TableFreightModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableFreightRepository extends JpaRepository<TableFreightModel, Long> {

    TableFreightModel findByCepStartLessThanEqualAndCepEndGreaterThanEqual(
            String cepStart, String cepEnd);
}





