package com.prolyzeai.entities;

import com.prolyzeai.entities.enums.ECashFlowType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cash_flows")
public class CashBalance extends BaseEntity
{

    @ManyToOne
    Manager manager;
    Double totalBalance;




}
