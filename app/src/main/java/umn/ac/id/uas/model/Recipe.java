package umn.ac.id.uas.model;

public class Recipe {
    private String foodName, id;
    public Recipe(){}
    public Recipe(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}


