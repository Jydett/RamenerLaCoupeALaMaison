package fr.polytech.rlcalm.beans;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Tournament {
    @Id
    private Long id;

    @Setter
    private Integer year;

    @Setter
    private String host;
}
