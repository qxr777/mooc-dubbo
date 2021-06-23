package edu.whut.cs.jee.mooc.mclass.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionDto implements Serializable {
    private Long id;
    private String name;
    private String content;
    private Integer count = 0;
    private boolean correct;
}
