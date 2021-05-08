package com.example.systemevents.repository;

import com.example.systemevents.model.SystemEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemEventRepository extends JpaRepository<SystemEvent, Long> {
}
