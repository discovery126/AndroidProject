package com.example.myapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CategoryModelDao {
    @Insert
    public void addCategory(CategoryModel categoryModel);
    @Update
    public void updateCategory(CategoryModel categoryModel);
    @Delete
    public void deleteCategory(CategoryModel categoryModel);
    @Query("select * from category")
    public List<CategoryModel> getAllCategories();
    @Query("select * from category where title == :title")
    public CategoryModel getCategory(String title);
    @Query("SELECT count(*) FROM category")
    public int getCountCategory();
    @Query("select category_id from category where title == :title")
    public int getCategoryId(String title);

}
