package fr.polytech.rlcalm.beans;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    private String name;
    private String ISOCode;

    public String getIcon() {
        return "<i class='flag flag-" + ISOCode + "'></i>";
    }
}
