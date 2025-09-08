package com.prolyzeai.repository;


import com.prolyzeai.repository.View.AdminResponseView;
import com.prolyzeai.entities.Admin;
import com.prolyzeai.entities.Auth;
import com.prolyzeai.entities.enums.EStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID>
{

    List<AdminResponseView> findAllByNameContainingIgnoreCaseAndAuth_StatusIsNotOrderByNameAsc(String name, EStatus status, PageRequest pageRequest);

    Optional<AdminResponseView> findByAuth(Auth auth);
    Optional<AdminResponseView> findViewById(UUID id);
}
