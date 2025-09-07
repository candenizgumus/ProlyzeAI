package com.prolyzeai.repository;


import com.prolyzeai.entities.Auth;
import com.prolyzeai.entities.enums.EStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthRepository extends JpaRepository<Auth, UUID>
{
    Boolean existsByEmail(String email);

    List<Auth> findAllByEmailContainingIgnoreCaseAndStatusIsNotOrderByEmailAsc(String name, EStatus status, PageRequest pageRequest);

    Optional<Auth> findByEmail(String email);
}
