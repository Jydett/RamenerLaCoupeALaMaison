package fr.polytech.rlcalm.form;

import fr.polytech.rlcalm.beans.Match;
import fr.polytech.rlcalm.utils.FormUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchUpdateForm {
    private String city;

    private String stadium;

    private Instant instant;

    private Long tournamentId;

    private Long playerId1;

    private Long playerId2;

    //Match result
    private Integer score1;

    private Integer score2;

    public static MatchUpdateForm fromRequest(HttpServletRequest req) throws IllegalArgumentException {
        String cityParam = FormUtils.notNull(req.getParameter("city"));
        String stadiumParam = FormUtils.notNull(req.getParameter("studium"));

        String dateParam = req.getParameter("date");
        String datehParam = req.getParameter("date-h");
        Instant instant = null;
        if (dateParam != null && datehParam != null) {
            instant = LocalDateTime.parse(dateParam + " " + datehParam, Match.DATE_FORMAT).toInstant(ZoneOffset.UTC);
        }

        Long tournamentId = FormUtils.getRequiredLong(req.getParameter("tournamentId"));
        Long playerId1 = FormUtils.getRequiredLong(req.getParameter("player1"));
        Long playerId2 = FormUtils.getRequiredLong(req.getParameter("player2"));
        Integer score1 = FormUtils.getInt(req.getParameter("score1"));
        Integer score2 = FormUtils.getInt(req.getParameter("score2"));

        return new MatchUpdateForm(
            cityParam,
            stadiumParam,
            instant,
            tournamentId,
            playerId1,
            playerId2,
            score1,
            score2
        );
    }
}
