package fr.polytech.rlcalm.beans;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer year;

    private String host;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tournament")
    private List<Match> matches;

    @Embedded
    private TournamentResult result;
}

