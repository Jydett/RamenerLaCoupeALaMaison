package fr.polytech.rlcalm.dao;

public interface Identifiable<Id> {
    Id getId();
    void setId(Id id);
}
