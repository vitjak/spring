package com.example.taxation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Outgoing {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal possibleReturnAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal possibleReturnAmountBefTax;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal possibleReturnAmountAfterTax;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal taxRate;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal taxAmount;


}