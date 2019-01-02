package AplicatieTeme.Repository;

import AplicatieTeme.Domain.HasID;
import AplicatieTeme.Domain.Validators.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<ID, E extends HasID<ID>> implements CrudRepository<ID, E> {

    private final Validator<E> validator;
    private final Map<ID, E> entities;

    InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities = new HashMap<>();
    }

    @Override
    public E findOne(ID id) {
        Optional.ofNullable(id).orElseThrow(IllegalArgumentException::new);
        return entities.get(id);
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public E save(E entity) {
        Optional.ofNullable(entity).orElseThrow(IllegalArgumentException::new);
        validator.validate(entity);
        return entities.putIfAbsent(entity.getID(), entity);
    }

    @Override
    public E delete(ID id) {
        Optional.ofNullable(id).orElseThrow(IllegalArgumentException::new);
        return entities.remove(id);
    }

    @Override
    public E update(E entity) {
        Optional.ofNullable(entity).orElseThrow(IllegalArgumentException::new);
        validator.validate(entity);
        if (entities.get(entity.getID()) == null)
            return entity;
        entities.put(entity.getID(), entity);
        return null;
    }

}
