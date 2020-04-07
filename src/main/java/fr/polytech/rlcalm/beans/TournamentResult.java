package fr.polytech.rlcalm.beans;

import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable
public class TournamentResult {

    private Integer score; //TODO score pour le tournois ?

    @ManyToOne
    private Club winner;//TODO pas terrible comme resultat ?
}
