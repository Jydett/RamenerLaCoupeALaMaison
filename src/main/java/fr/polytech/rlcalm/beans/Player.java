package fr.polytech.rlcalm.beans;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Player {
    @Id
    private Long id;

    @Setter
    private String name;

    @Setter
    private Integer averageScore;

    @Setter
    private Club club;

    @Setter
    private Role role;
}
