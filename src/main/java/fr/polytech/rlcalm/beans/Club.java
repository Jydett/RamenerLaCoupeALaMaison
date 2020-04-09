package fr.polytech.rlcalm.beans;

import fr.polytech.rlcalm.dao.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Club implements Identifiable<Long> {
    @Id
    private Long id;

    private String name;

    @ManyToOne
    private Country country;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club")
    private List<Player> players;
}
