package ru.kpfu.itis.selyantsev.dao;

import ru.kpfu.itis.selyantsev.models.User;
import ru.kpfu.itis.selyantsev.repository.CrudRepository;

public interface DAO extends CrudRepository<User, String> {
}
