package fr.polytech.rlcalm.form;

import fr.polytech.rlcalm.exception.InvalidFormException;
import fr.polytech.rlcalm.utils.FormUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClubUpdateForm {

    private Long id;

    private String name;

    private Integer countryId;

    public static ClubUpdateForm fromRequest(HttpServletRequest req) {
        Long idParam = FormUtils.getLongForId(req.getParameter("id"), "Ce club n'est pas valide");
        String name = req.getParameter("name");
        if (FormUtils.isNullOrEmpty(name)) {
            throw new InvalidFormException("Il faut saisir le nom du club");
        }
        Integer countryId = FormUtils.getInt(req.getParameter("countryId"), "Ce pays n'est pas valide");

        return new ClubUpdateForm(idParam, name, countryId);
    }
}
