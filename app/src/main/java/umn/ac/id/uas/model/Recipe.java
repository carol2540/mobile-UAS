package umn.ac.id.uas.model;

public class Recipe {
    private String  foodName, id, foodRecipe, foodImage;
    public Recipe(){}
    public Recipe(String foodName, String foodRecipe, String foodImage) {
        this.foodName = foodName;
        this.foodRecipe = foodRecipe;
        this.foodImage = foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoodRecipe() {
        return foodRecipe;
    }

    public void setFoodRecipe(String foodRecipe) {
        this.foodRecipe = foodRecipe;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }
}


