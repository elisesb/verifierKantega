package com.example.verifiserer.repository;

import com.example.verifiserer.model.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvRepository extends JpaRepository<Cv, Long> {
    void deleteByCvPath(String cvPath);
}
