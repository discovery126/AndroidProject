package com.example.myapplication.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite_recipe", foreignKeys = {@ForeignKey(entity = User.class,
        parentColumns = "user_id",
        childColumns = "user_id_favourite",
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = RecipeModel.class,
        parentColumns = "recipe_id",
        childColumns = "recipe_id_favourite",
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE)
})
public class FavouriteRecipe {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "favourite_recipe_id")
    int favourite_recipe_id;
    @ColumnInfo(name = "user_id_favourite")
    int userIdFavourite;
    @ColumnInfo(name = "recipe_id_favourite")
    int recipeIdFavourite;


    public FavouriteRecipe(int userIdFavourite, int recipeIdFavourite) {
        this.userIdFavourite = userIdFavourite;
        this.recipeIdFavourite = recipeIdFavourite;
    }

    public int getFavourite_recipe_id() {
        return favourite_recipe_id;
    }

    public void setFavourite_recipe_id(int favourite_recipe_id) {
        this.favourite_recipe_id = favourite_recipe_id;
    }

    public int getUserIdFavourite() {
        return userIdFavourite;
    }

    public void setUserIdFavourite(int userIdFavourite) {
        this.userIdFavourite = userIdFavourite;
    }

    public int getRecipeIdFavourite() {
        return recipeIdFavourite;
    }

    public void setRecipeIdFavourite(int recipeIdFavourite) {
        this.recipeIdFavourite = recipeIdFavourite;
    }
}
