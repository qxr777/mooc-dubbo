package edu.whut.cs.jee.mooc.upms.repository;

import edu.whut.cs.jee.mooc.common.persistence.BaseRepository;
import edu.whut.cs.jee.mooc.upms.model.User;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface UserRepository extends BaseRepository<User, Long>, QueryByExampleExecutor<User> {
}
