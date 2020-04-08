package fr.polytech.rlcalm.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class HashMapDao<Id, T extends Identifiable<Id>> {
    public final HashMap<Id, T> table = new HashMap<>();
    public final Supplier<Id> idSupplier;

    protected HashMapDao(Supplier<Id> idSupplier) {
        this.idSupplier = idSupplier;
    }

    public Collection<T> getAll() {
        return table.values();
    }

    public Optional<T> findById(Id id) {
        return Optional.ofNullable(table.get(id));
    }

    public void save(T b) {
        if (b.getId() == null) {
            b.setId(idSupplier.get());
        }
        table.put(b.getId(), b);
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }

    public void remove(T toDelete) {
        table.remove(toDelete.getId());
    }
}
