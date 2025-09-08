package com.prolyzeai.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "projects")
public class Project extends BaseEntity
{

    @ManyToOne
    Company company;
    String name;
    String description;
    Double agreedPrice;
    LocalDate startDate;
    LocalDate endDate;
    @Builder.Default
    Boolean isCompleted = false;




}
