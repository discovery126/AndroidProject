package com.example.myapplication.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe")
public class RecipeModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipe_id")
    int idRecipe;
    @ColumnInfo(name = "title_recipe")
    String tittleRecipe;
    @ColumnInfo(name = "author_recipe")
    String authorRecipe;
    @ColumnInfo(name = "number_components_recipe")
    int numberComponentsRecipe;

    @ColumnInfo(name = "components_recipe")
    String componentsRecipe;

    @ColumnInfo(name = "steps_recipe")
    String descriptionRecipe;

    @ColumnInfo(name = "image_id")
    int imageRecipe;

    public RecipeModel(String tittleRecipe, String authorRecipe, int numberComponentsRecipe, String componentsRecipe, String descriptionRecipe, int imageRecipe) {
        this.tittleRecipe = tittleRecipe;
        this.authorRecipe = authorRecipe;
        this.numberComponentsRecipe = numberComponentsRecipe;
        this.componentsRecipe = componentsRecipe;
        this.descriptionRecipe = descriptionRecipe;
        this.imageRecipe = imageRecipe;
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public String getTittleRecipe() {
        return tittleRecipe;
    }

    public void setTittleRecipe(String tittleRecipe) {
        this.tittleRecipe = tittleRecipe;
    }

    public String getAuthorRecipe() {
        return authorRecipe;
    }

    public void setAuthorRecipe(String authorRecipe) {
        this.authorRecipe = authorRecipe;
    }

    public int getNumberComponentsRecipe() {
        return numberComponentsRecipe;
    }

    public void setNumberComponentsRecipe(int numberComponentsRecipe) {
        this.numberComponentsRecipe = numberComponentsRecipe;
    }

    public String getComponentsRecipe() {
        return componentsRecipe;
    }

    public void setComponentsRecipe(String componentsRecipe) {
        this.componentsRecipe = componentsRecipe;
    }

    public String getDescriptionRecipe() {
        return descriptionRecipe;
    }

    public void setDescriptionRecipe(String descriptionRecipe) {
        this.descriptionRecipe = descriptionRecipe;
    }

    public int getImageRecipe() {
        return imageRecipe;
    }

    public void setImageRecipe(int imageRecipe) {
        this.imageRecipe = imageRecipe;
    }
}