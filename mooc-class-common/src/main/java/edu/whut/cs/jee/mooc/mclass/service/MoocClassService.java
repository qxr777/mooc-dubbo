package edu.whut.cs.jee.mooc.mclass.service;


import edu.whut.cs.jee.mooc.mclass.dto.JoinDto;
import edu.whut.cs.jee.mooc.mclass.dto.LessonDto;
import edu.whut.cs.jee.mooc.mclass.dto.MoocClassDto;
import edu.whut.cs.jee.mooc.upms.dto.UserDto;

import java.util.List;

public interface MoocClassService {

    /**
     * 【参考】各层命名规约: 插入的方法用 save/insert 做前缀。
     * @param moocClassDto
     * @return
     */
    Long saveMoocClass(MoocClassDto moocClassDto);

    Long addMoocClass(MoocClassDto moocClassDto);

    Long editMoocClass(MoocClassDto moocClassDto);

    List<MoocClassDto> getOwnMoocClasses(Long teacherId);

    List<MoocClassDto> getJoinMoocClasses(Long userId);

    MoocClassDto getMoocClass(Long moocClassId);

    boolean isServing(Long moocClassId);

    List<UserDto> getUserDtos(Long moocClassId);

    /**
     * 【参考】各层命名规约: 插入的方法用 save/insert 做前缀。
     * @param lessonDto
     * @return
     */
    Long saveLesson(LessonDto lessonDto);

    /**
     * 【参考】各层命名规约: 删除的方法用 remove/delete 做前缀。
     * @param lessonId
     * @return
     */
    void removeLesson(Long lessonId);

    void removeMoocClass(Long moocClassId);

     void removeCourse(Long courseId);

    /**
     * 开始上课
     * @param moocClassId
     */
    LessonDto startLesson(Long moocClassId);

    LessonDto getLesson(Long lessonId);

    /**
     * 点击下课
     * @param lessonId
     */
    void endLesson(Long lessonId);

    /**
     * 学生加入慕课堂
     * @param joinDto
     * @return
     */
    void join(JoinDto joinDto);

    /**
     * 返回慕课堂的上课记录
     * @param moocClassId
     * @return
     */
    List<LessonDto> getLessons(Long moocClassId);

}
