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
@Table(name = "managers")
public class Manager extends BaseEntity
{

    @ManyToOne
    Auth auth;
    String name;
    String surname;
    String companyName;
    @Enumerated(EnumType.STRING)
    ECurrency currency;





}
