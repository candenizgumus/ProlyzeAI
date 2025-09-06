package com.prolyzeai.entities;

import com.prolyzeai.entities.enums.EStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@MappedSuperclass
public abstract class BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    EStatus status = EStatus.ACTIVE;
}
