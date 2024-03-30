package com.zybooks.savorysecrets.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.zybooks.savorysecrets.entity.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("Select * FROM recipes")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM recipes WHERE category = :category")
    LiveData<List<Recipe>> getRecipesByCategory(String category);

    @Query("SELECT * FROM recipes WHERE id = :id")
    LiveData<Recipe> getRecipe(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(Recipe recipe);

    @Update
    void updateRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);

    @Query("SELECT * FROM recipes WHERE name LIKE :name")
    LiveData<List<Recipe>> getRecipesByName(String name);
}
