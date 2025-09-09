package com.prolyzeai.repository;


import com.prolyzeai.entities.CashFlow;
import com.prolyzeai.entities.enums.ECashFlowType;
import com.prolyzeai.entities.enums.EStatus;
import com.prolyzeai.repository.View.CashFlowResponseView;
import com.prolyzeai.repository.View.CategoryResponseView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CashFlowRepository extends JpaRepository<CashFlow, UUID>
{

    List<CashFlowResponseView> findAllByDescriptionContainingIgnoreCaseAndStatusIsNotOrderByDateDesc(String s, EStatus eStatus, PageRequest of);
    Optional<CashFlowResponseView> findViewById(UUID id);

    @Query("SELECT SUM(c.amount) " +
            "FROM CashFlow c " +
            "WHERE c.category.company.id = :companyId " +
            "AND c.cashFlowType = :type")
    Double sumAmountByCompanyIdAndType(UUID companyId, ECashFlowType type);
}
