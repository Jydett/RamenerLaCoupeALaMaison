package fr.polytech.rlcalm.beans;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Tournament {
    @Id
    private Long id;

    private Integer year;

    private String host;
}

