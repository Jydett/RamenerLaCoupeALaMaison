package fr.polytech.rlcalm.beans;

import fr.polytech.rlcalm.dao.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Country implements Identifiable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String ISOCode;

    public String getIcon() {
        return "<a title='" + name + "'><i class='flag flag-" + ISOCode + "'></i></a>";
    }
}
