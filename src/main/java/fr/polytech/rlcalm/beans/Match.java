package fr.polytech.rlcalm.beans;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Match {
    @Id
    private Long id;

    private String city;

    private String stadium;

    private Integer date;

    private Tournament tournament;
}
