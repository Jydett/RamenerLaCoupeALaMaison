package fr.polytech.rlcalm.beans;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class TournamentResult {
    @Id
    private Long id;

    private Integer score;

    private Tournament tournament;

    private Club champions;
}
