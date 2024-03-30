package com.zybooks.savorysecrets.repository;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zybooks.savorysecrets.R;
import com.zybooks.savorysecrets.dao.RecipeDao;
import com.zybooks.savorysecrets.database.RecipeDatabase;
import com.zybooks.savorysecrets.entity.Recipe;

public class RecipeRepository {
    private RecipeDao recipeDao;
    private ExecutorService executorService;

    public RecipeRepository(Application application) {
        RecipeDatabase db = RecipeDatabase.getDatabase(application);
        recipeDao = db.recipeDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<Recipe> getRecipe(int id) {
        return recipeDao.getRecipe(id);
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return recipeDao.getAllRecipes();
    }

    public LiveData<List<Recipe>> getRecipesByCategory(String category) {
        return recipeDao.getRecipesByCategory(category);
    }

    public void insert(final Recipe recipe) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                recipeDao.insertRecipe(recipe);
            }
        });
    }

    public void update(final Recipe recipe) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                recipeDao.updateRecipe(recipe);
            }
        });
    }

    public void delete(final Recipe recipe) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                recipeDao.deleteRecipe(recipe);
            }
        });
    }
    public LiveData<List<Recipe>> getRecipesByName(String name) {
        return recipeDao.getRecipesByName('%' + name + '%');
    }

}