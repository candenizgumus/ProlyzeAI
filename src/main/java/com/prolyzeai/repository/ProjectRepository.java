package com.prolyzeai.repository;


import com.prolyzeai.entities.Company;
import com.prolyzeai.entities.Project;
import com.prolyzeai.entities.enums.EStatus;
import com.prolyzeai.repository.View.ProjectResponseView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID>
{

    @Query("""
    SELECT p 
    FROM Project p 
    WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :s, '%'))
      AND p.status <> :eStatus
      AND p.company = :company
    ORDER BY p.isCompleted ASC, p.name ASC
""")
    List<ProjectResponseView> findAllByNameContainingIgnoreCaseAndStatusIsNotAndCompanyOrderByNameAsc(
            @Param("s") String s,
            @Param("eStatus") EStatus eStatus,
            @Param("company") Company company,
            PageRequest pageable
    );

    Optional<ProjectResponseView> findViewById(UUID id);

    Integer countAllByStatusIsNotAndCreatedAtBetweenAndCompany(EStatus eStatus, LocalDateTime startDate, LocalDateTime endDate, Company company);

    @Query("""
    SELECT COALESCE(SUM(p.agreedPrice), 0)
    FROM Project p
    WHERE p.status <> :eStatus
      AND p.company = :company
      AND p.createdAt BETWEEN :startDate AND :endDate
""")
    Double sumAgreedPriceForYear(
            @Param("eStatus") EStatus eStatus,
            @Param("company") Company company,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );


    @Query("""
    SELECT p.id
    FROM Project p
    WHERE p.status <> :eStatus
      AND p.createdAt BETWEEN :startDate AND :endDate
      AND p.company = :company
""")
    List<UUID> findAllIdsByStatusIsNotAndCreatedAtBetweenAndCompany(
            EStatus eStatus,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Company company
    );


}
