package com.example.verifiserer.repository;

import com.example.verifiserer.model.Applicant;
import com.example.verifiserer.model.Vitnemal;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VitnemalRepository extends JpaRepository<Vitnemal, Long> {
    Optional<Vitnemal> findById(Long id);
}
