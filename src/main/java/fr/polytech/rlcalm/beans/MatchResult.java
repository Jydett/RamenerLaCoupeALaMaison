package fr.polytech.rlcalm.beans;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class MatchResult {
    @Id
    private Long id;

    private Club winner;

    private Club looser;

    private Integer score;

    private Match match;

}
