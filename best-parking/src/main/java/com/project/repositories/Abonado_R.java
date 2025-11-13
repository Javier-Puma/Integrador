package com.project.repositories;

import com.project.models.Abonado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Abonado_R extends JpaRepository<Abonado, Long> {
}