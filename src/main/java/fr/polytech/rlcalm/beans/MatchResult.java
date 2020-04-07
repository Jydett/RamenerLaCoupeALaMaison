package fr.polytech.rlcalm.beans;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class MatchResult {

    private Integer score;

    private Integer winner;
}
