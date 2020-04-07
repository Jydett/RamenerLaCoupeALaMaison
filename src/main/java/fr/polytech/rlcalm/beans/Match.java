package fr.polytech.rlcalm.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Match {
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
            if (result.getWinner() == 1) {
                return "<span class='winner'>" + player1.getName() + " " + player1.getCountry().getIcon() + result.getScore1() + "</span> - <span class='looser'>" + result.getScore2() + player2.getCountry().getIcon() + " " + player2.getName() + "</span>";
            } else {
                return "<span class='looser'>" + player1.getName() + " " + player1.getCountry().getIcon() + result.getScore1() + "</span> - <span class='winner'>" + result.getScore2() + player2.getCountry().getIcon() + " " + player2.getName() + "</span>";
            }
        }
    }

    public String getFormattedDate() {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
    }
}
