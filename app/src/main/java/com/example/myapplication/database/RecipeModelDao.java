package com.example.myapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface RecipeModelDao {
    @Insert
    public void addRecipe(RecipeModel recipeModel);
    @Update
    public void updateRecipe(RecipeModel recipeModel);
    @Delete
    public void deleteRecipe(RecipeModel recipeModel);
    @Query("SELECT * FROM recipe")
    public List<RecipeModel> getAllRecipe();
    @Query("SELECT count(*) FROM recipe")
    public int getCountRecipe();
    @Query("SELECT recipe_id FROM recipe where title_recipe=:titleRecipe")
    public int getRecipeId(String titleRecipe);

    @Query("SELECT * FROM recipe where recipe_id=:recipeId")
    public RecipeModel getRecipe(int recipeId);

}
