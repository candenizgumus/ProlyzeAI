package com.prolyzeai.repository;


import com.prolyzeai.entities.Company;
import com.prolyzeai.entities.Item;
import com.prolyzeai.entities.enums.EStatus;
import com.prolyzeai.repository.View.ItemResponseView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID>
{

    List<ItemResponseView> findAllByDescriptionContainingIgnoreCaseAndStatusIsNotAndCategory_CompanyOrderByDescriptionAsc(String s, EStatus eStatus, Company company, PageRequest of);
    List<ItemResponseView> findAllByDescriptionContainingIgnoreCaseAndStatusIsNotAndProject_IdOrderByDescriptionAsc(String s, EStatus eStatus, UUID id, PageRequest of);
    ItemResponseView findViewById(UUID id);
    @Query("""
    SELECT COALESCE(SUM(i.totalPrice), 0)
    FROM Item i
    WHERE i.project.id = :projectId
""")
    Double findTotalItemCostByProjectId(UUID projectId);


    @Query("""
    SELECT COALESCE(SUM(i.totalPrice), 0)
    FROM Item i
    WHERE i.project.id IN :projectIds
      AND i.status <> :eStatus
      AND i.category.company = :company
""")
    Double findTotalItemCostByProjectIds(
            List<UUID> projectIds,
            EStatus eStatus,
            Company company
    );

    @Query("""
    SELECT i.category.name, COALESCE(SUM(i.totalPrice), 0)
    FROM Item i
    WHERE i.status <> :eStatus
      AND i.category.company = :company
      AND i.createdAt BETWEEN :startDate AND :endDate
      AND i.project.isCompleted = :isCompleted
    GROUP BY i.category.name
    ORDER BY SUM(i.totalPrice) DESC
""")
    List<Object[]> findTopCategoryExpensesForYear(
            EStatus eStatus,
            Company company,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Boolean isCompleted,
            PageRequest pageRequest
    );




}
