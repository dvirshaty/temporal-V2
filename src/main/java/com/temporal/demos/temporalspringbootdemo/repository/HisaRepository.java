package com.temporal.demos.temporalspringbootdemo.repository;

import com.temporal.demos.temporalspringbootdemo.repository.model.Hsia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HisaRepository extends JpaRepository<Hsia, Long> {

    List<Hsia> findByUuid(String uuid);
}
