package com.zybooks.savorysecrets.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "recipes")
public class Recipe implements Serializable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String name;
    @NonNull
    private String ingredients;
    @NonNull
    private String instructions;
    @NonNull
    private String category; // breakfast, lunch, dinner, beverages, appetizers

    public Recipe(){}

    public Recipe(String name, String ingredients, String instructions, String category){
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.category = category;
    }

    // getter
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getIngredients(){
        return ingredients;
    }

    public String getInstructions(){
        return instructions;
    }

    public String getCategory(){
        return category;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(@NonNull String name){
        this.name = name;
    }

    public void setIngredients(@NonNull String ingredients){
        this.ingredients = ingredients;
    }

    public void setInstructions(@NonNull String instructions){
        this.instructions = instructions;
    }

    public void setCategory(@NonNull String category){
        this.category = category;
    }

}
