package com.github.diogenes.bighouse.api.repository;

import com.github.diogenes.bighouse.api.model.Idolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdoloRepository extends JpaRepository<Idolo , Long> {

}
