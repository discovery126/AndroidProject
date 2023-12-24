package com.example.myapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, RecipeModel.class, CategoryModel.class, Recipe_Categories.class,FavouriteRecipe.class},version = 1)
public abstract class MainDb extends RoomDatabase {

    public abstract UserDao getUserDao();
    public abstract RecipeModelDao getRecipeDao();
    public abstract CategoryModelDao getCategoryDao();
    public abstract Recipe_CategoriesDao getRecipCategDao();
    public abstract FavouriteRecipeDao getFavouriteRecipeDao();
}
