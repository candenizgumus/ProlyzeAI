package com.prolyzeai.repository;


import com.prolyzeai.entities.Category;
import com.prolyzeai.entities.enums.EStatus;
import com.prolyzeai.repository.View.CategoryResponseView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>
{

    List<CategoryResponseView> findAllByNameContainingIgnoreCaseAndStatusIsNotOrderByNameAsc(String s, EStatus eStatus, PageRequest of);
    Optional<CategoryResponseView> findViewById(UUID id);
}
