package com.hillel.artemjev.notebook.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class Note {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime dateTime;
}
