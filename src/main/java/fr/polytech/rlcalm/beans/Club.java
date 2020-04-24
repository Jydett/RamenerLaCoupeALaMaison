package fr.polytech.rlcalm.beans;

import fr.polytech.rlcalm.dao.Identifiable;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((Club) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
