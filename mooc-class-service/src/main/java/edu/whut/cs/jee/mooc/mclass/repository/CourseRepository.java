package edu.whut.cs.jee.mooc.mclass.repository;

import edu.whut.cs.jee.mooc.common.persistence.BaseRepository;
import edu.whut.cs.jee.mooc.mclass.model.Course;
import org.springframework.stereotype.Repository;

public interface CourseRepository extends BaseRepository<Course, Long> {
}
