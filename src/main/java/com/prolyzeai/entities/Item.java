package com.prolyzeai.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "items")
public class Item extends BaseEntity
{

    @ManyToOne
    Category category;
    @ManyToOne
    Project project;
    String description;
    Double unitPrice;
    Integer quantity;
    Double totalPrice;


    //Her update olduğunda otomatik totalPrice hesaplar.
    @PrePersist
    @PreUpdate
    private void calculateTotalPrice() {
        if (unitPrice != null && quantity != null) {
            totalPrice = unitPrice * quantity;
        } else {
            totalPrice = 0.0;
        }
    }
}
