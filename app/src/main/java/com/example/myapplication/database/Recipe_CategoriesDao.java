package com.example.myapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Recipe_CategoriesDao {
    @Insert
    public void addRecipeCategories(Recipe_Categories recipeCategories);

    @Update
    public void updateRecipeCategories(Recipe_Categories recipeCategories);

    @Delete
    public void deleteRecipeCategories(Recipe_Categories recipeCategories);
    @Query("SELECT r.* FROM recipe_categories rc JOIN recipe r ON rc.recipe_id=r.recipe_id where rc.category_id = :categoryId ")
    public List<RecipeModel>getRecipeByCategoryId(int categoryId);
}
