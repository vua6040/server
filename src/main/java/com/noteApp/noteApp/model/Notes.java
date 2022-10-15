package com.noteApp.noteApp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notes")
public class Notes implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3186281461711068177L;
    private Integer Id;
    private String title, notes, img, timeCreate, userId;
    private Boolean pinned;

    public Notes() {

    }

    public Notes(Integer Id, String title, String notes, String img, String timeCreate, Boolean pinned, String userId) {
        this.Id = Id;
        this.title = title;
        this.img = img;
        this.notes = notes;
        this.timeCreate = timeCreate;
        this.pinned = pinned;
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "notes", nullable = false)
    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(name = "img", nullable = true)
    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Column(name = "time_create", nullable = false)
    public String getTimeCreate() {
        return this.timeCreate;
    }

    public void setTimeCreate(String timeCreate) {
        this.timeCreate = timeCreate;
    }

    @Column(name = "pinned", nullable = false)
    public Boolean getPinned() {
        return this.pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    @Column(name = "userId", nullable = true)
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}