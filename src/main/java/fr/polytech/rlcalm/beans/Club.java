package fr.polytech.rlcalm.beans;

import fr.polytech.rlcalm.dao.Identifiable;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Club implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Country country;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club", cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Player> players = new ArrayList<>();

    public Club(String name, Country country) {
        this.id = null;
        this.name = name;
        this.country = country;
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.setClub(this);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }
}
