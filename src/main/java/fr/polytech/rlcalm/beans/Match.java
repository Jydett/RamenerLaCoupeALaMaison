package fr.polytech.rlcalm.beans;

import fr.polytech.rlcalm.dao.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Match implements Identifiable<Long> {
    @Id
    private Long id;

    private String city;

    private String stadium;

    private Instant instant;

    @ManyToOne
    private Tournament tournament;

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
        if (result == null) {
            return player1.getName() + player1.getCountry().getIcon() + " - " + player2.getCountry().getIcon() + player2.getName();
        } else {
            if (result.getScore1().equals(result.getScore2())) {
                return "<span class='tie'>" + player1.getName() + " " + player1.getCountry().getIcon() + result.getScore1() + "</span> - <span class='tie'>" + result.getScore2() + player2.getCountry().getIcon() + " " + player2.getName() + "</span>";
            } else
            if (result.getScore1() > result.getScore2()) {
                return "<span class='winner'>" + player1.getName() + " " + player1.getCountry().getIcon() + result.getScore1() + "</span> - <span class='looser'>" + result.getScore2() + player2.getCountry().getIcon() + " " + player2.getName() + "</span>";
            } else {
                return "<span class='looser'>" + player1.getName() + " " + player1.getCountry().getIcon() + result.getScore1() + "</span> - <span class='winner'>" + result.getScore2() + player2.getCountry().getIcon() + " " + player2.getName() + "</span>";
            }
        }
    }

    public String getFormattedDate() {//TODO retourner null et tester dans le jsp
        if (instant == null) return "Pas encore plannifié";
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
