package edu.whut.cs.jee.mooc.mclass.service;

import edu.whut.cs.jee.mooc.mclass.dto.ExerciseDto;

import java.util.List;

public interface ExerciseService {

    /**
     * 创建练习
     * @param exerciseDto
     * @return
     */
    ExerciseDto saveExercise(ExerciseDto exerciseDto);

    List<ExerciseDto> getExercises(Long courseId);

    /**
     * 删除练习库中的练习
     * @param exerciseId
     * @return
     */
    void removeExcercise(Long exerciseId);
}
