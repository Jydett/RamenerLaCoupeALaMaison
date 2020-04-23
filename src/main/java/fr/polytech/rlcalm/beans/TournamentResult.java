package fr.polytech.rlcalm.beans;

import fr.polytech.rlcalm.dao.Identifiable;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    //TODO si optionel double contrainte ?
    @ManyToOne(optional = false)
    private Club club;

    private Integer year;

    private String persistentName;

    @Setter
    private Integer placement;

    public TournamentResult(Club club, Integer year, Integer placement) {
        this.id = null;
        this.club = club;
        this.year = year;
        this.placement = placement;
        this.persistentName = getPersistentName(club);
    }

    //TODO
    private String getPersistentName(Club club) {
        return club.getName() + club.getCountry().getIcon();
    }
}
