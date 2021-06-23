package edu.whut.cs.jee.mooc.mclass.service;


import edu.whut.cs.jee.mooc.common.exception.APIException;
import edu.whut.cs.jee.mooc.mclass.dto.AttendanceDto;
import edu.whut.cs.jee.mooc.mclass.dto.CheckInDto;

public interface CheckInService {

    /**
     * 添加、启动签到
     * @param checkInDto
     * @return
     */
    Long saveCheckIn(CheckInDto checkInDto);

    /**
     * 学生签到
     * @param attendanceDto
     * @return
     * @throws APIException
     */
    Long saveAttendance(AttendanceDto attendanceDto) throws APIException;

    /**
     * 关闭签到
     * @param checkInId
     * @return
     */
    void closeCheckIn(Long checkInId);

    /**
     * 获取签到详情
     * @param checkInId
     * @return
     */
    CheckInDto getCheckInDto(Long checkInId);

    void removeCheckIn(Long checkInId);
}
