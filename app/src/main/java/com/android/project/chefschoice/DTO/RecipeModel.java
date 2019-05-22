package com.android.project.chefschoice.DTO;

import java.io.Serializable;

public class RecipeModel implements Serializable {
    private String recipeTitle;
    private String recipeUrl;

    public RecipeModel(String recipeTitle, String recipeUrl) {
        this.recipeTitle = recipeTitle;
        this.recipeUrl = recipeUrl;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public String getRecipeUrl() {
        return recipeUrl;
    }

    public void setRecipeUrl(String recipeUrl) {
        this.recipeUrl = recipeUrl;
    }

    @Override
    public String toString() {
        return "RecipeModel{" +
                "recipeTitle='" + recipeTitle + '\'' +
                ", recipeUrl='" + recipeUrl + '\'' +
                '}';
    }
}
