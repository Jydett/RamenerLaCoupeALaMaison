package fr.polytech.rlcalm.beans;

public enum Role {
    Goalkeeper("Gardien"),
    RightFullback("Arriere droit"),
    LeftFullback("Arriere gauche"),
    CenterBack("Défenseur central"),
    Sweeper("Stoppeur"),
    Defending("Milieu defensif droit"),
    RightMidfielder("Milleu defensif gauche"),
    Central("Milieu offensif droit"),
    Striker("Milieu offensif gauche"),
    AttackingMidfielder("Attaquant soutien"),
    LeftMidfielder("Attaquant pointe");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}