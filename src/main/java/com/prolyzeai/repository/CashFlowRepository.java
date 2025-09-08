package com.prolyzeai.repository;


import com.prolyzeai.entities.CashFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CashFlowRepository extends JpaRepository<CashFlow, UUID>
{

}
