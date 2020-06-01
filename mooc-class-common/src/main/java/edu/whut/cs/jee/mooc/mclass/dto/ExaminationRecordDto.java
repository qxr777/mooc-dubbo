package edu.whut.cs.jee.mooc.mclass.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ExaminationRecordDto implements Serializable {

    private Long id;

    private Long examinationId;

    private Long userId;

    private String userName;

    private String userNickname;

    private Double score = 0.0;

    private Integer correctCount = 0;

    private Date submitTime;

    private List<AnswerDto> answerDtos = new ArrayList<>();

    public void addAnswer(AnswerDto answerDto) {
        answerDtos.add(answerDto);
    }

}
