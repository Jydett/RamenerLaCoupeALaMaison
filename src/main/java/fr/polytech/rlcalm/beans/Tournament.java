package fr.polytech.rlcalm.beans;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
//TODO à delete ?
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//
//    private Integer year;
//
//    @ManyToOne
//    private Country host;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tournament")
//    private List<Match> matches;

//    @Embedded
//    private TournamentResult result;
}

