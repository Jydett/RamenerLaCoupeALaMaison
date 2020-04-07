package fr.polytech.rlcalm.beans;

import lombok.Data;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Match {
    @Id
    private Long id;

    private String city;

    private String stadium;

    private Integer date;

    @ManyToOne
    private Tournament tournament;

    @ManyToOne
    private Club player1;

    @ManyToOne
    private Club player2;

    @Embedded
    private MatchResult result;


    public Club getWinner(int winnerNumber) {
        switch (winnerNumber) {
            case 1 : return player1;
            case 2 : return player2;
            default: return null;
        }
    }
}
