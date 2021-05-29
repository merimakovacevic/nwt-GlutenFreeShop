package com.example.systemevents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "system_events")
public class SystemEvent {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Enum.LogType logType;

    @NotNull
    private String serviceName;

    @NotNull
    private Integer userId;

    @NotNull
    private Enum.Action action;

    @NotNull
    private String requestBody;

    @NotNull
    private String time;
}
