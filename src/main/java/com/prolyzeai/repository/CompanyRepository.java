package com.prolyzeai.repository;


import com.prolyzeai.entities.Company;
import com.prolyzeai.repository.View.CompanyResponseView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID>
{
    Optional<CompanyResponseView> findViewById(UUID id);

}
