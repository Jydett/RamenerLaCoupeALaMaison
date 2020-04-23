package fr.polytech.rlcalm.form;

import fr.polytech.rlcalm.beans.Role;
import fr.polytech.rlcalm.exception.InvalidFormException;
import fr.polytech.rlcalm.utils.FormUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerUpdateForm {

    private Long id;

    private String name;

    private Integer mediaRating;

    private Long clubId;

    private Role role;

    public static PlayerUpdateForm fromRequest(HttpServletRequest req) {
        Long idParam = FormUtils.getLongForId(req.getParameter("id"), "Ce joueur n'est pas valide");
        String name = req.getParameter("name");
        if (FormUtils.isNullOrEmpty(name)) {
            throw new InvalidFormException("Il faut saisir le nom du joueur");
        }
        Integer mediaRating = FormUtils.getInt(req.getParameter("mediaRating"), "La note des médias n'est pas valide");
        Long clubId = FormUtils.getLong(req.getParameter("clubId"), "Ce club n'est pas valide");
        String roleParam = req.getParameter("role");
        Role role = null;
        if (roleParam != null) {
            try {
                role = Role.valueOf(roleParam);
            } catch (IllegalArgumentException e) {
                throw new InvalidFormException("Ce rôle n'existe pas");
            }
        }

        return new PlayerUpdateForm(
            idParam,
            name,
            mediaRating,
            clubId,
            role
        );
    }
}
