package com.prolyzeai.entities;

import com.prolyzeai.entities.enums.ECurrency;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "companies")
public class Company extends BaseEntity
{

    String name;
    String city;
    String address;
    @Builder.Default
    Integer monthlyProjectLimit = 20;
    @Enumerated(EnumType.STRING)
    ECurrency selectedCurrency;



}
