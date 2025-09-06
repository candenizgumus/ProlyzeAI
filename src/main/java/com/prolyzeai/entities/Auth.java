package com.prolyzeai.entities;


import com.prolyzeai.entities.enums.EUserType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "auths")
public class Auth extends BaseEntity
{

    String email;
    String password;
    @Builder.Default
    LocalDate subscriptionStartDate = LocalDate.now();
    //1 Month Demo
    @Builder.Default
    LocalDate subscriptionEndDate = LocalDate.now().plusMonths(1);
    @Enumerated(EnumType.STRING)
    EUserType userType;


}
