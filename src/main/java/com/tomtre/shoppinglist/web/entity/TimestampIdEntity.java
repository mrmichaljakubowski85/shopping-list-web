package com.tomtre.shoppinglist.web.entity;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class TimestampIdEntity extends BaseIdEntity {

    @Column(name = "create_datetime")
    @CreationTimestamp
    private LocalDateTime createDateTime;

    @Column(name = "update_datetime")
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

}
