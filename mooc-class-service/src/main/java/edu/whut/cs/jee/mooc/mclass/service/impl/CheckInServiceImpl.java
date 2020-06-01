package edu.whut.cs.jee.mooc.mclass.service.impl;

import edu.whut.cs.jee.mooc.common.constant.AppConstants;
import edu.whut.cs.jee.mooc.common.constant.CheckInConstants;
import edu.whut.cs.jee.mooc.common.constant.MoocClassConstatnts;
import edu.whut.cs.jee.mooc.common.exception.APIException;
import edu.whut.cs.jee.mooc.common.exception.AppCode;
import edu.whut.cs.jee.mooc.common.util.BeanConvertUtils;
import edu.whut.cs.jee.mooc.common.util.LocationUtils;
import edu.whut.cs.jee.mooc.mclass.dto.AttendanceDto;
import edu.whut.cs.jee.mooc.mclass.dto.CheckInDto;
import edu.whut.cs.jee.mooc.mclass.model.Attendance;
import edu.whut.cs.jee.mooc.mclass.model.CheckIn;
import edu.whut.cs.jee.mooc.mclass.model.Lesson;
import edu.whut.cs.jee.mooc.mclass.model.MoocClass;
import edu.whut.cs.jee.mooc.mclass.repository.AttendanceRepository;
import edu.whut.cs.jee.mooc.mclass.repository.CheckInRepository;
import edu.whut.cs.jee.mooc.mclass.repository.LessonRepository;
import edu.whut.cs.jee.mooc.mclass.repository.MoocClassRepository;
import edu.whut.cs.jee.mooc.mclass.service.CheckInService;
import edu.whut.cs.jee.mooc.mclass.service.MoocClassService;
import edu.whut.cs.jee.mooc.upms.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@org.springframework.stereotype.Service
@com.alibaba.dubbo.config.annotation.Service(timeout = 10000,interfaceClass = CheckInService.class)
@Transactional
public class CheckInServiceImpl implements CheckInService {

    @Autowired
    private MoocClassRepository moocClassRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    /**
     * 添加、启动签到
     * @param checkInDto
     * @return
     */
    public Long saveCheckIn(CheckInDto checkInDto) {
        Lesson lesson = lessonRepository.findById(checkInDto.getLessonId()).get();
        if (lesson.getCheckIn() != null && lesson.getCheckIn().getStatus() == CheckInConstants.CHECK_IN_STATUS_OPEN) {
            throw new APIException(AppCode.HAS_OPENING_CHECKIN, AppCode.HAS_OPENING_CHECKIN.getMsg() + lesson.getCheckIn().getId());
        }

//        CheckIn checkIn = checkInDto.convertTo();
        CheckIn checkIn = BeanConvertUtils.convertTo(checkInDto, CheckIn::new);
        checkIn.setStatus(CheckInConstants.CHECK_IN_STATUS_OPEN);
        checkIn = checkInRepository.save(checkIn);

        // 将所有学生标记未签到
        if (lesson.getCheckIn() != null && lesson.getCheckIn().getStatus() == CheckInConstants.CHECK_IN_STATUS_CLOSED) {
            List<Attendance> attendances = attendanceRepository.findByCheckInId(lesson.getCheckIn().getId());
            attendanceRepository.deleteAll(attendances);
            checkInRepository.delete(lesson.getCheckIn());
        }
        lesson.setCheckIn(checkIn);
        MoocClass moocClass = moocClassRepository.findById(lesson.getMoocClassId()).get();
        List<User> users = new ArrayList<User>(moocClass.getUsers());
        for(User user : users) {
            Attendance attendance = Attendance.builder()
                    .checkInId(checkIn.getId())
                    .status(CheckInConstants.ATTENDANCE_STATUS_ABSENCE)
                    .user(user)
                    .build();
            attendanceRepository.save(attendance);
        }
        return checkIn.getId();
    }

    /**
     * 学生签到
     * @param attendanceDto
     * @return
     * @throws APIException
     */
    public Long saveAttendance(AttendanceDto attendanceDto) throws APIException {
        Attendance attendance = attendanceRepository.findByCheckInIdAndUserId(attendanceDto.getCheckInId(), attendanceDto.getUserId()).get(0);
        CheckIn checkIn = checkInRepository.findById(attendance.getCheckInId()).get();
        //签到地点判断
        Double latitude = attendanceDto.getLatitude();
        Double longitude = attendanceDto.getLongitude();
        Double centerLatitude = checkIn.getLatitude();
        Double centerLongitude = checkIn.getLongitude();
        if (checkIn.isGps() && latitude != null && longitude != null) {
            double distance = LocationUtils.getDistance(latitude, longitude, centerLatitude, centerLongitude);
            if (distance > AppConstants.MAX_DISTANCE_RANGE) {
                throw new APIException(AppCode.OVER_RANGE_ERROR, AppCode.OVER_RANGE_ERROR.getMsg() + AppConstants.MAX_DISTANCE_RANGE + AppConstants.DISTANCE_UNIT);
            }
        }
        // 签到时限判断
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.ofInstant(checkIn.getDeadline().toInstant(), ZoneId.systemDefault());
        if(nowTime.isAfter(deadline)) {
            attendance.setStatus(CheckInConstants.ATTENDANCE_STATUS_LATE);
        } else {
            attendance.setStatus(CheckInConstants.ATTENDANCE_STATUS_CHECKED);
        }
        Lesson lesson = lessonRepository.findById(checkIn.getLessonId()).get();
        if(lesson.getStatus() == MoocClassConstatnts.LESSON_STATUS_END || checkIn.getStatus() == CheckInConstants.CHECK_IN_STATUS_CLOSED) {
            throw new APIException(AppCode.OVER_DUE_ERROR, AppCode.OVER_DUE_ERROR.getMsg());
        }

        attendance = attendanceRepository.save(attendance);
        return attendance.getId();
    }

    /**
     * 关闭签到
     * @param checkInId
     * @return
     */
    public void closeCheckIn(Long checkInId) {
        CheckIn checkIn = checkInRepository.findById(checkInId).get();
        checkIn.setStatus(CheckInConstants.CHECK_IN_STATUS_CLOSED);
        checkIn = this.updateCount(checkIn);
        checkInRepository.save(checkIn);
    }

    /**
     * 更新签到活动统计数据
     * @param checkIn
     * @return
     */
    private CheckIn updateCount(CheckIn checkIn) {
        // 正常签到
        List<Attendance> checkedAttendances = attendanceRepository.findByCheckInIdAndStatus(checkIn.getId(), CheckInConstants.ATTENDANCE_STATUS_CHECKED);
        int checkedCount = checkedAttendances.size();
        checkIn.setCheckedCount(checkedCount);

        // 迟到
        List<Attendance> lateAttendances = attendanceRepository.findByCheckInIdAndStatus(checkIn.getId(), CheckInConstants.ATTENDANCE_STATUS_LATE);
        int lateCount = lateAttendances.size();
        checkIn.setLateCount(lateCount);

        // 缺课记录
        List<Attendance> absenceAttendances = attendanceRepository.findByCheckInIdAndStatus(checkIn.getId(), CheckInConstants.ATTENDANCE_STATUS_ABSENCE);
        int absenceCount = absenceAttendances.size();
        checkIn.setAbsenceCount(absenceCount);

        return checkIn;
    }

    /**
     * 获取签到详情
     * @param checkInId
     * @return
     */
    @Transactional(readOnly = true)
    public CheckInDto getCheckInDto(Long checkInId) {
        CheckIn checkIn = checkInRepository.findById(checkInId).get();
        checkIn = this.updateCount(checkIn);
        CheckInDto checkInDto = BeanConvertUtils.convertTo(checkIn, CheckInDto::new,
                (s, t) ->
                {
                    t.setStatusCh(CheckInConstants.CHECK_IN_STATUS_STRING_CH[s.getStatus()]);
                });
        checkInDto.setAttendanceDtos(this.getAttendances(checkInId));
        return checkInDto;
    }

    private List<AttendanceDto> getAttendances(Long checkId) {
        List<Attendance> attendances = attendanceRepository.findByCheckInId(checkId);
        return BeanConvertUtils.convertListTo(attendances, AttendanceDto::new,
                (s, t) ->
                {
                    t.setUserId(s.getUser().getId());
                    t.setUserName(s.getUser().getName());
                    t.setStatusCh(CheckInConstants.ATTENDANCE_STATUS_STRING_CH[s.getStatus()]);
                });
    }

    public void removeCheckIn(Long checkInId) {
        checkInRepository.deleteById(checkInId);
    }
}
