package fr.polytech.rlcalm.beans;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Match {
    @Id
    private Long id;

    @Setter
    private String city;

    @Setter
    private String stadium;

    @Setter
    private Integer date;

    @Setter
    private Tournament tournament;
}
