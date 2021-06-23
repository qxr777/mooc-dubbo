package edu.whut.cs.jee.mooc.mclass.service;

import edu.whut.cs.jee.mooc.mclass.dto.ChoiceDto;
import edu.whut.cs.jee.mooc.mclass.dto.FillDto;
import edu.whut.cs.jee.mooc.mclass.dto.JudgmentDto;
import edu.whut.cs.jee.mooc.mclass.dto.SubjectDto;

import java.util.List;

public interface SubjectService {

    ChoiceDto saveChoice(ChoiceDto choiceDto);

    JudgmentDto saveJudgment(JudgmentDto judgmentDto);

    FillDto saveFill(FillDto fillDto);

    /**
     * 获得随堂测试的习题
     * @param examinationId
     * @return
     */
    List<SubjectDto> getSubjectsOfExaminzation(Long examinationId);

    /**
     * 获得练习库中的习题
     * @param exerciseId
     * @return
     */
    List<SubjectDto> getSubjectsOfExercise(Long exerciseId);

    void removeSubject(Long subjectId);
}
