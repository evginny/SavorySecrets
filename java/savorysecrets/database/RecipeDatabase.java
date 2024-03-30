package com.zybooks.savorysecrets.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.zybooks.savorysecrets.dao.RecipeDao;
import com.zybooks.savorysecrets.entity.Recipe;

@Database(entities = {Recipe.class}, version = 1)
public abstract class RecipeDatabase extends RoomDatabase {
    private static RecipeDatabase instance;
    public abstract RecipeDao recipeDao();
    public static synchronized RecipeDatabase getDatabase(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            RecipeDatabase.class, "recipe_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
