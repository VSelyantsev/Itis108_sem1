package ru.kpfu.itis.selyantsev.repository;
public interface CrudRepository<T, R> {

    void save(T entity);

    T findByName(R name);

}
