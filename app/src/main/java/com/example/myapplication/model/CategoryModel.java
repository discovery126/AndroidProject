package com.example.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class CategoryModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int idCategory;
    @ColumnInfo(name = "title")
    String title;

    public CategoryModel(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
