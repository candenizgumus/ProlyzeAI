package com.prolyzeai.entities;

import com.prolyzeai.entities.enums.ECurrency;
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
@Table(name = "companies")
public class Company extends BaseEntity
{

    String name;
    String city;
    String address;
    @Enumerated(EnumType.STRING)
    ECurrency selectedCurrency;



}
