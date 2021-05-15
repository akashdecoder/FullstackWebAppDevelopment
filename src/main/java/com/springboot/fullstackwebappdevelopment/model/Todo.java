package com.springboot.fullstackwebappdevelopment.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "todo_1")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column(nullable = false, length=30)
    private String description;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private boolean isDone;


    public Todo() {
    }

    public Todo(String username, String description, Date date, boolean isdone) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.username = username;
        this.isDone = isdone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
