package ru.alex.bookstore.database.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import ru.alex.bookstore.listener.AuditListener;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditListener.class)
public abstract class AuditableEntity {
    private Instant createdAt;
    private Instant  updatedAt;
}


