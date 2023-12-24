package com.example.myapplication.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "recipe_categories",
        primaryKeys = { "category_id", "recipe_id"},
        foreignKeys = {
                @ForeignKey(entity = CategoryModel.class,
                        parentColumns = "category_id",
                        childColumns = "category_id"),
                @ForeignKey(entity = RecipeModel.class,
                        parentColumns = "recipe_id",
                        childColumns = "recipe_id")
        })
public class Recipe_Categories {
        int category_id;
        int recipe_id;

        public Recipe_Categories(int category_id, int recipe_id) {
                this.category_id = category_id;
                this.recipe_id = recipe_id;
        }

        public int getCategory_id() {
                return category_id;
        }

        public void setCategory_id(int category_id) {
                this.category_id = category_id;
        }

        public int getRecipe_id() {
                return recipe_id;
        }

        public void setRecipe_id(int recipe_id) {
                this.recipe_id = recipe_id;
        }
}
