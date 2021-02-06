package com.example.taxation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor
@Data
@Builder
public class Incoming {

    private Long traderId;
    private BigDecimal playedAmount;
    private BigDecimal odd;

    public Incoming(Long traderId, BigDecimal playedAmount, BigDecimal odd) {
        this.traderId = traderId;
        this.playedAmount = playedAmount;
        this.odd = odd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Incoming that = (Incoming) o;
        return traderId.equals(that.traderId) && playedAmount.equals(that.playedAmount) && odd.equals(that.odd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(traderId, playedAmount, odd);
    }

    @Override
    public String toString() {
        return "Incoming{" +
                "traderId=" + traderId +
                ", playedAmount=" + playedAmount +
                ", odd=" + odd +
                '}';
    }
}