package fr.polytech.rlcalm.beans;

import fr.polytech.rlcalm.dao.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Player implements Identifiable<Long> {
    @Id
    private Long id;

    private String name;

    private Integer mediaRating;

    @ManyToOne
    private Club club;

    @Enumerated(EnumType.STRING)
    private Role role;
}
