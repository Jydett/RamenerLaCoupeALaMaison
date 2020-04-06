package fr.polytech.rlcalm.beans;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Club {
    @Id
    private Long id;

    private String name;

    private String country;
}
