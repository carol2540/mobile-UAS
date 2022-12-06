package umn.ac.id.uas;

public class Food {
    String heading;
    int foodImage;

    public Food(){}

    public Food(String heading, int foodImage) {
        this.heading = heading;
        this.foodImage = foodImage;
    }

    public int getFoodImage() {
        return this.foodImage;
    }

    public void setFoodImage(int foodImage) {
        this.foodImage = foodImage;
    }

    public String getHeading() {
        return this.heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

}
