package fr.polytech.rlcalm.beans;

import fr.polytech.rlcalm.dao.Identifiable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = {"year", "club_id"}),
    indexes = {@Index(columnList = "year")}
)
@Getter
public class TournamentResult implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @ManyToOne(optional = false)
    private Club club;

    private Integer year;

    @Setter
    private Integer placement;

    public TournamentResult(Club club, Integer year, Integer placement) {
        this.id = null;
        this.club = club;
        this.year = year;
        this.placement = placement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((Identifiable<Long>) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
