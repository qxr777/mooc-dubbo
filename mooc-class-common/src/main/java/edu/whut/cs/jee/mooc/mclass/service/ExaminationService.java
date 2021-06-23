package edu.whut.cs.jee.mooc.mclass.service;

import edu.whut.cs.jee.mooc.mclass.dto.ExaminationDto;
import edu.whut.cs.jee.mooc.mclass.dto.ExaminationRecordDto;

import java.util.List;

public interface ExaminationService {

    /**
     * 备课 - 添加活动 - 添加练习 - 从练习库导入
     * @param lessonId
     * @param exerciseId
     * @return
     */
    Long importFromExercise(Long lessonId, Long exerciseId);

    ExaminationDto getExamination(Long examinationId);

    /**
     * 批改、保存随堂测试答题卡
     * @param examinationRecordDto
     * @return
     */
    ExaminationRecordDto saveExaminationRecord(ExaminationRecordDto examinationRecordDto);

    /**
     * 删除随堂测试
     * @param examinationId
     * @return
     */
    void removeExamination(Long examinationId);

    void removeExaminationRecord(Long examinationRecordId);

    /**
     * 向学生发布随堂练习
     * @param examinationId
     */
    void publishExamination(Long examinationId, Long lessonId);

    /**
     * 获取随堂测试答题卡
     * @param examinationId
     * @return
     */
    List<ExaminationRecordDto> getExaminationRecords(Long examinationId);

    /**
     * 获取此慕课堂未发布的随堂测试
     * @param moocClassId
     * @return
     */
    List<ExaminationDto> getPrivateExaminations(Long moocClassId);

}
