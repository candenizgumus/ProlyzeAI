package com.prolyzeai.repository;


import com.prolyzeai.entities.Auth;
import com.prolyzeai.entities.Manager;
import com.prolyzeai.repository.View.ManagerResponseView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID>
{

    Optional<Manager> findByAuth(Auth auth);
    Optional<ManagerResponseView> findViewById(UUID id);

    Optional<ManagerResponseView> findViewByAuth(Auth clinicManager);
}
