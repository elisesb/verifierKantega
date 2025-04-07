package com.example.verifiserer.repository;

import com.example.verifiserer.model.Applicant;
import com.example.verifiserer.model.Vitnemal;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VitnemalRepository extends JpaRepository<Vitnemal, Long> {
    Optional<Vitnemal> findById(Long id);
    Vitnemal findByFodselsnummer(String fodselsnummer);
}
