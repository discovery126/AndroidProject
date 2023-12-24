package com.example.myapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.Optional;

@Dao
public interface FavouriteRecipeDao {
    @Insert
    void addFavouriteRecipe(FavouriteRecipe recipe);
    @Update
    void updateFavouriteRecipe(FavouriteRecipe recipe);

    @Query("DELETE FROM favourite_recipe where user_id_favourite = :idUser and recipe_id_favourite =:idRecipe")
    void deleteFavouriteRecipe(int idUser,int idRecipe);

    @Query("DELETE FROM favourite_recipe WHERE user_id_favourite = (SELECT user_id from users where users.user_id = :user_id) AND recipe_id_favourite = (SELECT recipe_id from recipe where recipe.title_recipe = :titleRecipe)")
    void deleteFavouriteRecipe(int user_id, String titleRecipe);

    @Query("SELECT * FROM favourite_recipe")
    List<FavouriteRecipe> getAllItems();
    @Query("SELECT recipe.* from users inner join favourite_recipe on user_id=user_id_favourite inner join recipe on recipe_id_favourite=recipe_id where users.user_id =:user_id")
    List<RecipeModel> getFavouriteRecipesUser(int user_id);
    @Query("SELECT recipe.* from recipe inner join favourite_recipe on recipe_id_favourite=recipe_id where favourite_recipe.user_id_favourite=:idUser and favourite_recipe.recipe_id_favourite=:idRecipe")
    Optional<RecipeModel> getFavouriteRecipesUser(int idUser, int idRecipe);
}
