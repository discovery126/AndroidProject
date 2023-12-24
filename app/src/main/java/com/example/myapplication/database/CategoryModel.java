package com.example.myapplication.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class CategoryModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    int idCategory;
    @ColumnInfo(name = "title")
    String title;

    public CategoryModel(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.idCategory = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return idCategory;
    }

    public String getTitle() {
        return title;
    }
}
