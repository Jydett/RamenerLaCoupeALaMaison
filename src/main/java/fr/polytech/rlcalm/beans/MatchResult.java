package fr.polytech.rlcalm.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
@AllArgsConstructor
public class MatchResult {

    private Integer score1;

    private Integer score2;

    private Integer winner;
}
