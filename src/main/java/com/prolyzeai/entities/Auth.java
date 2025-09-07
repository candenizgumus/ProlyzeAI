package com.prolyzeai.entities;


import com.prolyzeai.entities.enums.EUserType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "auths")
public class Auth extends BaseEntity implements UserDetails
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userType.name()));
        return authorities;
    }

    @Override
    public String getUsername()
    {
        return email;
    }
}
