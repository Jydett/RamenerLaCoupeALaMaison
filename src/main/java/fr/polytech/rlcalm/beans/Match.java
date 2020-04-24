package fr.polytech.rlcalm.beans;

import fr.polytech.rlcalm.dao.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "matchfoot")
public class Match implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    private String stadium;

    private Instant instant;

    @ManyToOne
    private Club team1;

    @ManyToOne
    private Club team2;

    @Embedded
    private MatchResult result;

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm", new Locale("fr", "FR"));
    private static final DateTimeFormatter DATE_UNIQUE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_HOUR_FORMAT = DateTimeFormatter.ofPattern("hh:mm");

    public Club getWinner(int winnerNumber) {
        switch (winnerNumber) {
            case 1 : return team1;
            case 2 : return team2;
            default: return null;
        }
    }

    public String getStringView() {
        String team1Link = "<a href=\"clubs?id=" + team1.getId() + "\" title=\"Voir cette équipe\">" + team1.getName() + "</a>";
        String team2Link = "<a href=\"clubs?id=" + team2.getId() + "\" title=\"Voir cette équipe\">" + team2.getName() + "</a>";
        if (result == null) {
            return team1Link + team1.getCountry().getIcon() + " - " + team2.getCountry().getIcon() + team2Link;
        } else {
            if (result.getScore1().equals(result.getScore2())) {
                return "<span class='tie'>" + team1Link + " " + team1.getCountry().getIcon() + result.getScore1() + "</span> - <span class='tie'>" + result.getScore2() + team2.getCountry().getIcon() + " " + team2Link + "</span>";
            } else
            if (result.getScore1() > result.getScore2()) {
                return "<span class='winner'>" + team1Link + " " + team1.getCountry().getIcon() + result.getScore1() + "</span> - <span class='looser'>" + result.getScore2() + team2.getCountry().getIcon() + " " + team2Link + "</span>";
            } else {
                return "<span class='looser'>" + team1Link + " " + team1.getCountry().getIcon() + result.getScore1() + "</span> - <span class='winner'>" + result.getScore2() + team2.getCountry().getIcon() + " " + team2Link + "</span>";
            }
        }
    }

    public String getFormattedDate() {
        if (instant == null) return null;
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC).format(DATE_FORMAT);
    }

    public String getDate() {
        if (instant == null) return "";
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC).format(DATE_UNIQUE_FORMAT);
    }

    public String getDateh() {
        if (instant == null) return "";
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC).format(DATE_HOUR_FORMAT);
    }
}
