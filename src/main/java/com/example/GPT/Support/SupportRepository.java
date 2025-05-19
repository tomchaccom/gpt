package com.example.GPT.Support;

import jakarta.persistence.EntityManager;
import org.hibernate.internal.SessionImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportRepository extends JpaRepository<SupportProgram, Long> {



}
