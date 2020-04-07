package fr.polytech.rlcalm.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    private Long id;

    private String name;

    private Integer averageScore;

    @ManyToOne
    private Club club;

    @Enumerated(EnumType.STRING)
    private Role role;
}
