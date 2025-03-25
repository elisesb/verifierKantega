package com.example.verifiserer.repository;

import com.example.verifiserer.model.Karakter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KarakterRepository extends JpaRepository<Karakter, Long> {
    Optional<Karakter> findById(Long id);
}
