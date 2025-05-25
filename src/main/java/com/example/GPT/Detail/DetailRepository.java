package com.example.GPT.Detail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DetailRepository extends JpaRepository<DetailSupport, Long> {

    Optional<DetailSupport> findById(Long id);
    DetailSupport findByTarget(String target);
}

