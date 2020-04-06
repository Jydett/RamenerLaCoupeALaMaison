package fr.polytech.rlcalm.beans;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Player {
    @Id
    private Long id;

    private String name;

    private Integer averageScore;

    private Club club;

    private Role role;
}
