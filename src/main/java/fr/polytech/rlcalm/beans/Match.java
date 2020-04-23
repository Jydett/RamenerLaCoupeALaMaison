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
    private Club player1;

    @ManyToOne
    private Club player2;

    @Embedded
    private MatchResult result;

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm", new Locale("fr", "FR"));
    private static final DateTimeFormatter DATE_UNIQUE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_HOUR_FORMAT = DateTimeFormatter.ofPattern("hh:mm");

    public Club getWinner(int winnerNumber) {
        switch (winnerNumber) {
            case 1 : return player1;
            case 2 : return player2;
            default: return null;
        }
    }

    public String getStringView() {
        String player1Link = "<a href=\"clubs?id=" + player1.getId() + "\" title=\"Voir cette équipe\">" + player1.getName() + "</a>";
        String player2Link = "<a href=\"clubs?id=" + player2.getId() + "\" title=\"Voir cette équipe\">" + player2.getName() + "</a>";
        if (result == null) {
            return player1Link + player1.getCountry().getIcon() + " - " + player2.getCountry().getIcon() + player2Link;
        } else {
            if (result.getScore1().equals(result.getScore2())) {
                return "<span class='tie'>" + player1Link + " " + player1.getCountry().getIcon() + result.getScore1() + "</span> - <span class='tie'>" + result.getScore2() + player2.getCountry().getIcon() + " " + player2Link + "</span>";
            } else
            if (result.getScore1() > result.getScore2()) {
                return "<span class='winner'>" + player1Link + " " + player1.getCountry().getIcon() + result.getScore1() + "</span> - <span class='looser'>" + result.getScore2() + player2.getCountry().getIcon() + " " + player2Link + "</span>";
            } else {
                return "<span class='looser'>" + player1Link + " " + player1.getCountry().getIcon() + result.getScore1() + "</span> - <span class='winner'>" + result.getScore2() + player2.getCountry().getIcon() + " " + player2Link + "</span>";
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
