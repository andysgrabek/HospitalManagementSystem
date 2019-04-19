package work.in.progress.hospitalmanagement.service;

/**
 * Central service marker interface. Captures the domain type to manage
 * as well as the domain type's id type.
 *
 * @param <T>  the domain type the service manages
 * @param <ID> the type of the id of the entity the service manages
 */
@org.springframework.stereotype.Service
interface Service<T, ID> {
    Iterable<T> findAll();

    T save(T entity);

    void delete(T entity);
}
