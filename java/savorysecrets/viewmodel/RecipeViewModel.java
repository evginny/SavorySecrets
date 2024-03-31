package com.zybooks.savorysecrets.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.zybooks.savorysecrets.entity.Recipe;
import com.zybooks.savorysecrets.repository.RecipeRepository;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private final RecipeRepository recipeRepository;
    private LiveData<List<Recipe>> allRecipes;

    public RecipeViewModel(Application application) {
        super(application);
        recipeRepository = new RecipeRepository(application);
        allRecipes = recipeRepository.getAllRecipes();
    }

    public LiveData<List<Recipe>> getRecipesByName(String name) {
        // Implement the method to filter the recipes by name using Room 
        return recipeRepository.getRecipesByName(name);
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return allRecipes;
    }

    public LiveData<List<Recipe>> getRecipesByCategory(String category) {
        return recipeRepository.getRecipesByCategory(category);
    }

    public void insert(Recipe recipe) {
        recipeRepository.insert(recipe);
    }

    public void update(Recipe recipe) {
        recipeRepository.update(recipe);
    }

    public void delete(Recipe recipe) {
        recipeRepository.delete(recipe);
    }
    public LiveData<Recipe> getRecipe(int id) {
        return recipeRepository.getRecipe(id);
    }
}
