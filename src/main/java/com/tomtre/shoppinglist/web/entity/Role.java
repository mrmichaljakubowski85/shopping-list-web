package com.tomtre.shoppinglist.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@SequenceGenerator(name = BaseIdEntity.SEQUENCE_GENERATOR_NAME, sequenceName = "role_seq")
@Table(name = "roles")
public class Role extends BaseIdEntity {

    @Column(nullable = false, unique = true)
    private String name;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                '}';
    }
}
