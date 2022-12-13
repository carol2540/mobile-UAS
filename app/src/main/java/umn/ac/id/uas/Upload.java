package umn.ac.id.uas;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mRecipe;

    public Upload() {

    }

    public Upload(String name, String imageUrl, String recipe) {
        if(name.trim().equals("")){
            name = "No Name";
        }

        mName = name;
        mImageUrl = imageUrl;
        mRecipe = recipe;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getRecipe() { return mRecipe; }

    public void setRecipe(String recipe) { mRecipe = recipe; }

}
