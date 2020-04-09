package fr.polytech.rlcalm.form;

import fr.polytech.rlcalm.exception.InvalidFormException;
import fr.polytech.rlcalm.utils.FormUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchUpdateForm {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private Long id;

    private String city;

    private String stadium;

    private Instant instant;

    private Long tournamentId;

    private Long playerId1;

    private Long playerId2;

    //Match result
    private Integer score1;

    private Integer score2;

    public static MatchUpdateForm fromRequest(HttpServletRequest req) {
        Long idParam = FormUtils.getLong(req.getParameter("id"), "Ce match n'est pas valide");
        String cityParam = FormUtils.notNull(req.getParameter("city"), "Il faut saisir la ville où se déroulera le match");
        String stadiumParam = FormUtils.notNull(req.getParameter("stadium"), "Il faut saisir le stade où se déroulera le match");

        String dateParam = req.getParameter("date");
        String datehParam = req.getParameter("date-h");
        Instant instant = null;
        if (FormUtils.isNullOrEmpty(dateParam) ^ FormUtils.isNullOrEmpty(datehParam)) {
            throw new InvalidFormException("La date du match n'est pas complète !");
        }
        if (! FormUtils.isNullOrEmpty(datehParam)) {
            try {
                instant = LocalDateTime.parse(dateParam + " " + datehParam, DATE_FORMAT).toInstant(ZoneOffset.UTC);
            } catch (Exception ignored) {
                throw new InvalidFormException("Date invalide !");
            }
        }

        Long tournamentId = FormUtils.getLong(req.getParameter("tournamentId"), "Le tournois séléctionné n'est pas valide");
        Long playerId1 = FormUtils.getRequiredLong(req.getParameter("player1"), "Il faut séléctionner une première équipe");
        Long playerId2 = FormUtils.getRequiredLong(req.getParameter("player2"), "Il faut séléctionner une seconde équipe");
        Integer score1 = FormUtils.getInt(req.getParameter("score1"), "Le score de la première équipe n'est pas valide");
        Integer score2 = FormUtils.getInt(req.getParameter("score2"), "Le score de la première équipe n'est pas valide");

        return new MatchUpdateForm(
            idParam,
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
