package com.example.systemevents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private LogType logType;

    @NotNull
    private String serviceName;

    @NotNull
    private String userEmail;

    @NotNull
    private Action action;

    @NotNull
    private String time;


    public static enum LogType {
        INFO,
        ERROR
    }

    public static enum Action {
        GET,
        POST,
        PUT,
        DELETE
    }
}
