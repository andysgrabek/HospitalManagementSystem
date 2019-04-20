package work.in.progress.hospitalmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Objects;

abstract class AbstractService<T, ID> implements Service<T, ID> {

    private final JpaRepository<T, ID> repository;

    @Autowired(required = false)
    AbstractService(JpaRepository<T, ID> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    /**
     * Retrieves all instances of the managed type.
     *
     * @return all managed entries
     */
    @Override
    public List<T> findAll() {
        return repository.findAll(defaultSort());
    }

    /**
     * Saves a given entity. Use the returned entity for further operations as the
     * save operation might have changed the entity instance.
     *
     * @param entity the entity to save, must not be {@code null}
     * @return the saved entity, will never be {@code null}
     * @throws IllegalArgumentException if the specified entity is {@code null}
     */
    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    /**
     * Deletes a given entity.
     *
     * @param entity the entity to delete, must not be {@code null}
     * @throws IllegalArgumentException if the specified entity is {@code null}
     */
    @Override
    public void delete(T entity) {
        repository.delete(entity);
    }

    protected abstract Sort defaultSort();

}
