package umn.ac.id.uas;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mRecipe;
    private String mAuthorId;
    private String mAuthorName;
    private String id;

    public Upload() {

    }

    public Upload(String author, String imageUrl, String recipe, String name, String authorName) {
        if(name.trim().equals("")){
            name = "No Name";
        }

        mAuthorId = author;
        mName = name;
        mImageUrl = imageUrl;
        mRecipe = recipe;
        mAuthorName = authorName;
    }

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getAuthorId() { return mAuthorId; }

    public void setAuthorId(String author) { mAuthorId = author; }

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

    public String getAuthorName() { return mAuthorName; }

    public void setAuthorName(String authorName) { mAuthorName = authorName; }

}
