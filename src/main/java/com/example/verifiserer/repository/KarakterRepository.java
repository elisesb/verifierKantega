package com.example.verifiserer.repository;

import com.example.verifiserer.model.Karakter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KarakterRepository extends JpaRepository<Karakter, Long> {
    Optional<Karakter> findById(Long id);

    @Override
    List<Karakter> findAllById(Iterable<Long> longs);
    //Long findVitnemalId(Long id);
}
