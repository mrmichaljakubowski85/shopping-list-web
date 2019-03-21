package com.tomtre.shoppinglist.web.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseIdEntity {

    public static final String SEQUENCE_GENERATOR_NAME = "id_gen";
    public static final String ID_COLUMN_NAME = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENERATOR_NAME)
    @Column(name = ID_COLUMN_NAME, updatable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseIdEntity{" +
                "id=" + id +
                '}';
    }
}
