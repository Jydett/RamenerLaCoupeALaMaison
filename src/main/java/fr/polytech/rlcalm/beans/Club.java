package fr.polytech.rlcalm.beans;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Club {
    @Id
    private Long id;

    @Setter
    private String name;

    @Setter
    private String country;
}
