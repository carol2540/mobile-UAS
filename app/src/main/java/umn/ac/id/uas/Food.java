package umn.ac.id.uas;

public class Food {
    String heading;
    String foodImage;

    public Food(){}

    public Food(String heading, String foodImage) {
        this.heading = heading;
        this.foodImage = foodImage;
    }

    public String getFoodImage() {
        return this.foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public String getHeading() {
        return this.heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

}
