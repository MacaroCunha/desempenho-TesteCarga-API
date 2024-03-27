package com.example.frete.repository;

import com.example.frete.model.TableFreightModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TableFreightRepository extends JpaRepository<TableFreightModel, Long> {
    List<TableFreightModel> findAllByCepStartLessThanEqualAndCepEndGreaterThanEqual(
            String cepStart, String cepEnd);
}





