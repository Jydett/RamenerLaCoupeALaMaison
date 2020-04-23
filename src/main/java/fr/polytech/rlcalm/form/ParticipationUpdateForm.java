package fr.polytech.rlcalm.form;

import fr.polytech.rlcalm.utils.FormUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ParticipationUpdateForm {
    private Map<Long, Integer> scoreByPlayers;

    public static ParticipationUpdateForm formRequest(HttpServletRequest req) {
        Map<Long, Integer> scoreByPlayers = new HashMap<>();
        for (Map.Entry<String, String[]> entry : req.getParameterMap().entrySet()) {
            String key = entry.getKey();
            if (key.startsWith("par")) {
                key = key.substring(3);
                Long playerId = FormUtils.getRequiredLong(key, "One of the players id is incorrect !");
                String[] scores = entry.getValue();
                Integer score = FormUtils.getRequiredInt(scores[0], "One of the score is not an integer !");
                scoreByPlayers.put(playerId, score);
            }
        }
        return new ParticipationUpdateForm(scoreByPlayers);
    }
}
