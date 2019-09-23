package fiu.team5cen4010.cauldron.Model;

public class Recipe {
    public int recipeid, cookingtime, numservings, numcalories;
    public float totalcost;
    public String name, type, description, ingredients, instruction, imageid, nutritionimage;

    public Recipe(int recipeid, String name, String type, String description,String ingredients, String instruction, String imageid,String nutritionimage ,int cookingtime, float totalcost, int numservings, int numcalories) {
        this.recipeid = recipeid;
        this.name = name;
        this.type = type;
        this.description = description;
        this.ingredients = ingredients;
        this.instruction = instruction;
        this.imageid = imageid;
        this.nutritionimage = nutritionimage;
        this.cookingtime = cookingtime;
        this.totalcost = totalcost;
        this.numservings = numservings;
        this.numcalories = numcalories;
    }

    public int getNumservings() {
        return numservings;
    }

    public void setNumservings(int numservings) {
        this.numservings = numservings;
    }

    public int getNumcalories() {
        return numcalories;
    }

    public void setNumcalories(int numcalories) {
        this.numcalories = numcalories;
    }

    public Recipe(){}

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getNutritionimage() {
        return nutritionimage;
    }

    public void setNutritionimage(String nutritionimage) {
        this.nutritionimage = nutritionimage;
    }

    public int getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(int recipeid) {
        this.recipeid = recipeid;
    }

    public int getCookingtime() {
        return cookingtime;
    }

    public String getCookingtimeToString() {
        String intAsString = Integer.toString(cookingtime);
        return "Ready in: "+ intAsString+ " minutes";
    }


    public void setCookingtime(int cookingtime) {
        this.cookingtime = cookingtime;
    }

    public float getTotalcost() {
        return totalcost;
    }

    public String getTotalcostToString() {
        String floatAsString = Float.toString(totalcost);
        return "Cost: $"+ floatAsString;
    }

    public void setTotalcost(float totalcost) {
        this.totalcost = totalcost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

  
}
