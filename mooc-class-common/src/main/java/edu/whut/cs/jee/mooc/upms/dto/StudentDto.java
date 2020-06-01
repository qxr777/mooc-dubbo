package edu.whut.cs.jee.mooc.upms.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class StudentDto extends UserDto{

    @NotNull(message = "学号不允许为空")
    @Length(max = 20, min = 6, message = "学号长度必须是6-20个字符")
    private String studentNo;

}
