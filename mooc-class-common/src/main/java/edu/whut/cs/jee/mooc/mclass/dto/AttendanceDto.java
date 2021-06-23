package edu.whut.cs.jee.mooc.mclass.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDto implements Serializable {

    private Long checkInId;

    private Long userId;

    /**
     * 签到处 经度
     */
    private Double longitude = 0.0;

    /**
     * 签到处 纬度
     */
    private Double latitude = 0.0;

    private String userName;

    private String statusCh;

    private Date createTime;

}
