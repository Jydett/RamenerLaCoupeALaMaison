package fr.polytech.rlcalm.beans;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class MatchResult {
    @Id
    private Long id;

    @Setter
    private Club winner;

    @Setter
    private Club looser;

    @Setter
    private Integer score;

    @Setter
    private Match match;

}
