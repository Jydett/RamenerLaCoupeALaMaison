package fr.polytech.rlcalm.beans;

import fr.polytech.rlcalm.dao.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Participation implements Identifiable<Long> {
    @Id
    private Long id;

    @ManyToOne
    private Player player;

    private Integer goals;

    @ManyToOne
    private Match match;
}
