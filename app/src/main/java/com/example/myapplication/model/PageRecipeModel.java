package com.example.myapplication.model;

import android.widget.TextView;

public class PageRecipeModel {
    String tittlePageRecipe;
    String authorPageRecipe;
    String descriptionPageRecipe;
    int imagePageRecipe;

    public PageRecipeModel(String tittlePageRecipe, String authorPageRecipe, String descriptionPageRecipe, int imagePageRecipe) {
        this.tittlePageRecipe = tittlePageRecipe;
        this.authorPageRecipe = authorPageRecipe;
        this.descriptionPageRecipe = descriptionPageRecipe;
        this.imagePageRecipe = imagePageRecipe;
    }

    public void setTittlePageRecipe(String tittlePageRecipe) {
        this.tittlePageRecipe = tittlePageRecipe;
    }

    public void setAuthorPageRecipe(String authorPageRecipe) {
        this.authorPageRecipe = authorPageRecipe;
    }

    public void setDescriptionPageRecipe(String descriptionPageRecipe) {
        this.descriptionPageRecipe = descriptionPageRecipe;
    }

    public void setImagePageRecipe(int imagePageRecipe) {
        this.imagePageRecipe = imagePageRecipe;
    }

    public String getTittlePageRecipe() {
        return tittlePageRecipe;
    }

    public String getAuthorPageRecipe() {
        return authorPageRecipe;
    }

    public String getDescriptionPageRecipe() {
        return descriptionPageRecipe;
    }

    public int getImagePageRecipe() {
        return imagePageRecipe;
    }
}
