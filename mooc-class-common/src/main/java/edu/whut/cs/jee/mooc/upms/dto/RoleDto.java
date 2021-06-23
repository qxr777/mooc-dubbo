package edu.whut.cs.jee.mooc.upms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto implements Serializable {

    private Long id;

    private String name;

    public RoleDto(Long id) {
        this.id = id;
    }

}
