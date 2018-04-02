package com.noteCoin.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="id_generator")
    @SequenceGenerator(name="id_generator", sequenceName = "user_id", allocationSize = 50)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "pass")
    private Integer pass;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_enter")
    private Date lastEnter;

    public User() { }

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass.hashCode();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPass() {
        return pass;
    }

    public Date getLastEnter() {

        return lastEnter;
    }

    public void setLastEnter(Date lastEnter) {
        this.lastEnter = lastEnter;
    }

    @Override
    public String toString() {
        return "id:" + id + "name:" + name + "pass:" + pass;
    }
}
