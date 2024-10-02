package com.example.demo.repository;

import com.example.demo.model.Genera_recuso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneraRecursoRepository extends JpaRepository<Genera_recuso, Long> {
}
