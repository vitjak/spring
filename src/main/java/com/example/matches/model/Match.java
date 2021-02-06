package com.example.matches.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class Match implements Comparable<Match>{

    @Id @GeneratedValue
    private Long id;
    private Long data_insert;

    private String matchId;
    private Long marketId;
    private String outcomeId;
    private String specifiers;

    public Match(String matchId, Long marketId, String outcomeId, String specifiers) {
        this.matchId = matchId;
        this.marketId = marketId;
        this.outcomeId = outcomeId;
        this.specifiers = specifiers;
    }

    @Override
    public int compareTo(final Match o) {
        final int matchIdComparison = this.matchId.compareTo(o.getMatchId());

        if (matchIdComparison != 0) {
            return matchIdComparison;
        }

        final int marketIdComparison = this.marketId.compareTo(o.getMarketId());

        if (marketIdComparison != 0) {
            return marketIdComparison;
        }

        return this.outcomeId.compareTo(o.getOutcomeId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return Objects.equals(id, match.id) && Objects.equals(matchId, match.matchId) && Objects.equals(marketId, match.marketId) && Objects.equals(outcomeId, match.outcomeId) && Objects.equals(specifiers, match.specifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, matchId, marketId, outcomeId, specifiers);
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", matchId='" + matchId + '\'' +
                ", marketId=" + marketId +
                ", outcomeId='" + outcomeId + '\'' +
                ", specifiers='" + specifiers + '\'' +
                '}';
    }
}
