package com.prolyzeai.repository;


import com.prolyzeai.entities.Item;
import com.prolyzeai.entities.enums.EStatus;
import com.prolyzeai.repository.View.ItemResponseView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID>
{

    List<ItemResponseView> findAllByDescriptionContainingIgnoreCaseAndStatusIsNotOrderByDescriptionAsc(String s, EStatus eStatus, PageRequest of);
    ItemResponseView findViewById(UUID id);
    @Query("""
    SELECT COALESCE(SUM(i.totalPrice), 0)
    FROM Item i
    WHERE i.project.id = :projectId
""")
    Double findTotalItemCostByProjectId(UUID projectId);

}
