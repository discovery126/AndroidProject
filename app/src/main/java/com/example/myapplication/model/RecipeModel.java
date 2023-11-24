package com.example.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe_model")
public class RecipeModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int idCategory;
    @ColumnInfo(name = "title_recipe")
    String tittleRecipe;
    @ColumnInfo(name = "author_recipe")
    String authorRecipe;
    @ColumnInfo(name = "components_recipe")
    String componentsRecipe;
    int imageRecipe;
    int[] category;


    public RecipeModel(String tittleRecipe, String authorRecipe, String componentsRecipe, int[] category, int imageRecipe) {
        this.tittleRecipe = tittleRecipe;
        this.authorRecipe = authorRecipe;
        this.componentsRecipe = componentsRecipe;
        this.category = category;
        this.imageRecipe = imageRecipe;
    }

    public String getTittleRecipe() {
        return tittleRecipe;
    }

    public String getAuthorRecipe() {
        return authorRecipe;
    }

    public String getComponentsRecipe() {
        return componentsRecipe;
    }

    public int[] getCategory() {
        return category;
    }

    public int getImageRecipe() {
        return imageRecipe;
    }
}