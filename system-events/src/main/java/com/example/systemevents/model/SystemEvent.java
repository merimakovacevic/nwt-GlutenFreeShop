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
    private String time;

    @NotNull
    private String serviceName;

    private String user;

    @NotNull
    private String eventName;

    @NotNull
    private String eventType;
}
