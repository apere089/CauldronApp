package fiu.team5cen4010.cauldron.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import fiu.team5cen4010.cauldron.Model.Recipe;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "cauldron_final.db";
    private static final int DB_VER = 1;

    public Database(Context context) {
        super(context, DB_NAME, null,DB_VER);
    }

    //Function to display all recipe information(whole table)
    public List<Recipe> getRecipe()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Attributes as they appear in the database
        String[] sqlSelect = {"recipeid","name","type","description","ingredients","instruction","imageid","nutritionimage","cookingtime","totalcost","numservings","numcalories"};
        String RECIPE_TABLE = "Recipe"; //Table name as it is in database

        qb.setTables(RECIPE_TABLE);
        Cursor cursor = qb.query(db,sqlSelect, null,null, null, null, "type, name");
        List<Recipe> result = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do{
                Recipe recipe = new Recipe();
                recipe.setRecipeid(cursor.getInt(cursor.getColumnIndex("recipeid")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                recipe.setType(cursor.getString(cursor.getColumnIndex("type")));
                recipe.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                recipe.setIngredients(cursor.getString(cursor.getColumnIndex("ingredients")));
                recipe.setInstruction(cursor.getString(cursor.getColumnIndex("instruction")));
                recipe.setImageid(cursor.getString(cursor.getColumnIndex("imageid")));
                recipe.setNutritionimage(cursor.getString(cursor.getColumnIndex("nutritionimage")));
                recipe.setCookingtime(cursor.getInt(cursor.getColumnIndex("cookingtime")));
                recipe.setTotalcost(cursor.getFloat(cursor.getColumnIndex("totalcost")));
                recipe.setNumservings(cursor.getInt(cursor.getColumnIndex("numservings")));
                recipe.setNumcalories(cursor.getInt(cursor.getColumnIndex("numcalories")));

                result.add(recipe);
            }while(cursor.moveToNext());
        }
        return result;
    }

    //Function to display all recipes from the "New" Category in HomeActivity (home screen)
    public List<Recipe> getRecipesNew()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Attributes as they appear in the database
        String[] sqlSelect = {"recipeid","name","type","description","ingredients","instruction","imageid","nutritionimage","cookingtime","totalcost","numservings","numcalories"};
        String RECIPE_TABLE = "Recipe"; //Table name as it is in database

        qb.setTables(RECIPE_TABLE);
        Cursor cursor = qb.query(db,sqlSelect, null,null, null, null, "recipeid DESC","5"); //limit of 5 recipes but we can do more if desired
        List<Recipe> result = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do{
                Recipe recipe = new Recipe();
                recipe.setRecipeid(cursor.getInt(cursor.getColumnIndex("recipeid")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                recipe.setType(cursor.getString(cursor.getColumnIndex("type")));
                recipe.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                recipe.setIngredients(cursor.getString(cursor.getColumnIndex("ingredients")));
                recipe.setInstruction(cursor.getString(cursor.getColumnIndex("instruction")));
                recipe.setImageid(cursor.getString(cursor.getColumnIndex("imageid")));
                recipe.setNutritionimage(cursor.getString(cursor.getColumnIndex("nutritionimage")));
                recipe.setCookingtime(cursor.getInt(cursor.getColumnIndex("cookingtime")));
                recipe.setTotalcost(cursor.getFloat(cursor.getColumnIndex("totalcost")));
                recipe.setNumservings(cursor.getInt(cursor.getColumnIndex("numservings")));
                recipe.setNumcalories(cursor.getInt(cursor.getColumnIndex("numcalories")));

                result.add(recipe);
            }while(cursor.moveToNext());
        }
        return result;
    }

    //Function to display all recipes from the "Popular" Category in HomeActivity (home screen)
    //Selecting 5 static. Updating this when we have a field in the database (for instance 'popular') to count for popular votes for each recipe.
    //To do this, we need to implement a writable database so that every time a user likes a recipe, the popular attribute for this recipe increases by one and only one
    //If we implement this, we would get rid of the selection: and only put in sortOrder: "popular DESC", limit "5"
    public List<Recipe> getRecipesPopular()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Attributes as they appear in the database
        String[] sqlSelect = {"recipeid","name","type","description","ingredients","instruction","imageid","nutritionimage","cookingtime","totalcost","numservings","numcalories"};
        String RECIPE_TABLE = "Recipe"; //Table name as it is in database

        qb.setTables(RECIPE_TABLE);
        Cursor cursor = qb.query(db,sqlSelect, "recipeid == 1 or recipeid == 3 or recipeid == 4 or recipeid == 5 or recipeid == 9 ",null, null, null, "name","5");
        List<Recipe> result = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do{
                Recipe recipe = new Recipe();
                recipe.setRecipeid(cursor.getInt(cursor.getColumnIndex("recipeid")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                recipe.setType(cursor.getString(cursor.getColumnIndex("type")));
                recipe.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                recipe.setIngredients(cursor.getString(cursor.getColumnIndex("ingredients")));
                recipe.setInstruction(cursor.getString(cursor.getColumnIndex("instruction")));
                recipe.setImageid(cursor.getString(cursor.getColumnIndex("imageid")));
                recipe.setNutritionimage(cursor.getString(cursor.getColumnIndex("nutritionimage")));
                recipe.setCookingtime(cursor.getInt(cursor.getColumnIndex("cookingtime")));
                recipe.setTotalcost(cursor.getFloat(cursor.getColumnIndex("totalcost")));
                recipe.setNumservings(cursor.getInt(cursor.getColumnIndex("numservings")));
                recipe.setNumcalories(cursor.getInt(cursor.getColumnIndex("numcalories")));

                result.add(recipe);
            }while(cursor.moveToNext());
        }
        return result;
    }

    //Function to display all recipe names
    public List<String> getRecipeName()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Attributes as they appear in the database
        String[] sqlSelect = {"name"};
        String RECIPE_TABLE = "Recipe"; //Table name as it is in database

        qb.setTables(RECIPE_TABLE);
        Cursor cursor = qb.query(db,sqlSelect, null,null, null, null, null);
        List<String> result = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do{
                result.add(cursor.getString(cursor.getColumnIndex("name")));
            }while(cursor.moveToNext());
        }
        return result;
    }

    //Function to query recipes by names
    public List<Recipe> getRecipeByName(String name)
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Attributes as they appear in the database
        String[] sqlSelect = {"recipeid","name","type","description","ingredients","instruction","imageid","nutritionimage","cookingtime","totalcost","numservings","numcalories"};
        String RECIPE_TABLE = "Recipe"; //Table name as it is in database

        qb.setTables(RECIPE_TABLE);
        //For exact name query do: Cursor cursor = qb.query(db,sqlSelect, "name = ?",new String[]{name}, null, null, null);
        //LIKE implementation is done below
        Cursor cursor = qb.query(db,sqlSelect, "name LIKE ?",new String[]{"%"+name+"%"}, null, null, "type, name");
        List<Recipe> result = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do{
                Recipe recipe = new Recipe();
                recipe.setRecipeid(cursor.getInt(cursor.getColumnIndex("recipeid")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                recipe.setType(cursor.getString(cursor.getColumnIndex("type")));
                recipe.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                recipe.setIngredients(cursor.getString(cursor.getColumnIndex("ingredients")));
                recipe.setInstruction(cursor.getString(cursor.getColumnIndex("instruction")));
                recipe.setImageid(cursor.getString(cursor.getColumnIndex("imageid")));
                recipe.setNutritionimage(cursor.getString(cursor.getColumnIndex("nutritionimage")));
                recipe.setCookingtime(cursor.getInt(cursor.getColumnIndex("cookingtime")));
                recipe.setTotalcost(cursor.getFloat(cursor.getColumnIndex("totalcost")));
                recipe.setNumservings(cursor.getInt(cursor.getColumnIndex("numservings")));
                recipe.setNumcalories(cursor.getInt(cursor.getColumnIndex("numcalories")));

                result.add(recipe);
            }while(cursor.moveToNext());
        }
        return result;
    }

    //Function to query recipes by names
    public List<Recipe> getSavedRecipes(ArrayList<String> favorites)
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] arrFavorites = new String[favorites.size()];

        // ArrayList to Array Conversion
        for (int i =0; i < favorites.size(); i++)
            arrFavorites[i] = favorites.get(i);

        String selection = "";
        for (int i = 0; i < arrFavorites.length; i++)
        {
            if(i != arrFavorites.length - 1)
            {
                selection = selection + "name = ? or ";
            }
            else{
                selection = selection + "name = ?";
            }

        }

        //Attributes as they appear in the database
        String[] sqlSelect = {"recipeid","name","type","description","ingredients","instruction","imageid","nutritionimage","cookingtime","totalcost","numservings","numcalories"};
        String RECIPE_TABLE = "Recipe"; //Table name as it is in database

        qb.setTables(RECIPE_TABLE);
        //For exact name query do: Cursor cursor = qb.query(db,sqlSelect, "name = ?",new String[]{name}, null, null, null);
        //LIKE implementation is done below
        Cursor cursor = qb.query(db,sqlSelect, selection,arrFavorites, null, null, "name");
        List<Recipe> result = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do{
                Recipe recipe = new Recipe();
                recipe.setRecipeid(cursor.getInt(cursor.getColumnIndex("recipeid")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                recipe.setType(cursor.getString(cursor.getColumnIndex("type")));
                recipe.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                recipe.setIngredients(cursor.getString(cursor.getColumnIndex("ingredients")));
                recipe.setInstruction(cursor.getString(cursor.getColumnIndex("instruction")));
                recipe.setImageid(cursor.getString(cursor.getColumnIndex("imageid")));
                recipe.setNutritionimage(cursor.getString(cursor.getColumnIndex("nutritionimage")));
                recipe.setCookingtime(cursor.getInt(cursor.getColumnIndex("cookingtime")));
                recipe.setTotalcost(cursor.getFloat(cursor.getColumnIndex("totalcost")));
                recipe.setNumservings(cursor.getInt(cursor.getColumnIndex("numservings")));
                recipe.setNumcalories(cursor.getInt(cursor.getColumnIndex("numcalories")));

                result.add(recipe);
            }while(cursor.moveToNext());
        }
        return result;
    }

    //Get recipes by cost (Search is done like this: totalcost <= cost)
    public List<Recipe> getRecipeByCost(String cost)
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Attributes as they appear in the database
        String[] sqlSelect = {"recipeid","name","type","description","ingredients","instruction","imageid","nutritionimage","cookingtime","totalcost","numservings","numcalories"};
        String RECIPE_TABLE = "Recipe"; //Table name as it is in database

        qb.setTables(RECIPE_TABLE);

        Cursor cursor = qb.query(db,sqlSelect, "totalcost <= ?",new String[]{cost}, null, null, "totalcost, type, name");
        List<Recipe> result = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do{
                Recipe recipe = new Recipe();
                recipe.setRecipeid(cursor.getInt(cursor.getColumnIndex("recipeid")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                recipe.setType(cursor.getString(cursor.getColumnIndex("type")));
                recipe.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                recipe.setIngredients(cursor.getString(cursor.getColumnIndex("ingredients")));
                recipe.setInstruction(cursor.getString(cursor.getColumnIndex("instruction")));
                recipe.setImageid(cursor.getString(cursor.getColumnIndex("imageid")));
                recipe.setNutritionimage(cursor.getString(cursor.getColumnIndex("nutritionimage")));
                recipe.setCookingtime(cursor.getInt(cursor.getColumnIndex("cookingtime")));
                recipe.setTotalcost(cursor.getFloat(cursor.getColumnIndex("totalcost")));
                recipe.setNumservings(cursor.getInt(cursor.getColumnIndex("numservings")));
                recipe.setNumcalories(cursor.getInt(cursor.getColumnIndex("numcalories")));

                result.add(recipe);
            }while(cursor.moveToNext());
        }
        return result;
    }

    //Function to query recipes by ingredients. (Inclusive: returns recipes with exactly those ingredients-> 1 AND 2 AND 3)
    public List<Recipe> getRecipeByIngredients_INCLUSIVE(String ingredient1, String ingredient2, String ingredient3)
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Attributes as they appear in the database
        String[] sqlSelect = {"Recipe.recipeid","Recipe.name","Recipe.type","Recipe.description","Recipe.ingredients","Recipe.instruction","Recipe.imageid","Recipe.nutritionimage","Recipe.cookingtime","Recipe.totalcost","Recipe.numservings","Recipe.numcalories"};
        String RECIPE_TABLE = "Recipe"; //Table name as it is in database
        String INGREDIENT_TABLE = "Ingredient"; //Table name as it is in database

        qb.setTables(RECIPE_TABLE
                + " INNER JOIN "
                + INGREDIENT_TABLE
                + " ON "
                + "Recipe.recipeid"
                + " = "
                + "Ingredient.recipeid");

        Cursor cursor = qb.query(db,sqlSelect, "ingredientname LIKE ? or ingredientname LIKE ? or ingredientname LIKE ?",new String[]{"%"+ingredient1+"%","%"+ingredient2+"%", "%"+ingredient3+"%"} , "Recipe.recipeid", "count(*) >= 3", "type, name",null);
        List<Recipe> result = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do{
                Recipe recipe = new Recipe();
                recipe.setRecipeid(cursor.getInt(cursor.getColumnIndex("recipeid")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                recipe.setType(cursor.getString(cursor.getColumnIndex("type")));
                recipe.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                recipe.setIngredients(cursor.getString(cursor.getColumnIndex("ingredients")));
                recipe.setInstruction(cursor.getString(cursor.getColumnIndex("instruction")));
                recipe.setImageid(cursor.getString(cursor.getColumnIndex("imageid")));
                recipe.setNutritionimage(cursor.getString(cursor.getColumnIndex("nutritionimage")));
                recipe.setCookingtime(cursor.getInt(cursor.getColumnIndex("cookingtime")));
                recipe.setTotalcost(cursor.getFloat(cursor.getColumnIndex("totalcost")));
                recipe.setNumservings(cursor.getInt(cursor.getColumnIndex("numservings")));
                recipe.setNumcalories(cursor.getInt(cursor.getColumnIndex("numcalories")));

                result.add(recipe);
            }while(cursor.moveToNext());
        }
        return result;
    }

    //Function to query recipes by ingredients. (Exclusive: returns recipes with at least one of the ingredients-> 1 OR 2 OR 3)
    public List<Recipe> getRecipeByIngredients_EXCLUSIVE(String ingredient1, String ingredient2, String ingredient3)
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Attributes as they appear in the database
        String[] sqlSelect = {"DISTINCT Recipe.recipeid","Recipe.name","Recipe.type","Recipe.description","Recipe.ingredients","Recipe.instruction","Recipe.imageid","Recipe.nutritionimage","Recipe.cookingtime","Recipe.totalcost","Recipe.numservings","Recipe.numcalories"};
        String RECIPE_TABLE = "Recipe"; //Table name as it is in database
        String INGREDIENT_TABLE = "Ingredient"; //Table name as it is in database

        qb.setTables(RECIPE_TABLE
                + " INNER JOIN "
                + INGREDIENT_TABLE
                + " ON "
                + "Recipe.recipeid"
                + " = "
                + "Ingredient.recipeid");

        Cursor cursor = qb.query(db,sqlSelect, "ingredientname LIKE ? or ingredientname LIKE ? or ingredientname LIKE ?",new String[]{"%"+ingredient1+"%","%"+ingredient2+"%", "%"+ingredient3+"%"} ,null ,null , "type, name",null);
        List<Recipe> result = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do{
                Recipe recipe = new Recipe();
                recipe.setRecipeid(cursor.getInt(cursor.getColumnIndex("recipeid")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                recipe.setType(cursor.getString(cursor.getColumnIndex("type")));
                recipe.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                recipe.setIngredients(cursor.getString(cursor.getColumnIndex("ingredients")));
                recipe.setInstruction(cursor.getString(cursor.getColumnIndex("instruction")));
                recipe.setImageid(cursor.getString(cursor.getColumnIndex("imageid")));
                recipe.setNutritionimage(cursor.getString(cursor.getColumnIndex("nutritionimage")));
                recipe.setCookingtime(cursor.getInt(cursor.getColumnIndex("cookingtime")));
                recipe.setTotalcost(cursor.getFloat(cursor.getColumnIndex("totalcost")));
                recipe.setNumservings(cursor.getInt(cursor.getColumnIndex("numservings")));
                recipe.setNumcalories(cursor.getInt(cursor.getColumnIndex("numcalories")));

                result.add(recipe);
            }while(cursor.moveToNext());
        }
        return result;
    }

    //Function to query recipes by single ingredient
    public List<Recipe> getRecipeBySingleIngredient(String ingredient)
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Attributes as they appear in the database
        String[] sqlSelect = {"DISTINCT Recipe.recipeid","Recipe.name","Recipe.type","Recipe.description","Recipe.ingredients","Recipe.instruction","Recipe.imageid","Recipe.nutritionimage","Recipe.cookingtime","Recipe.totalcost","Recipe.numservings","Recipe.numcalories"};
        String RECIPE_TABLE = "Recipe"; //Table name as it is in database
        String INGREDIENT_TABLE = "Ingredient"; //Table name as it is in database

        qb.setTables(RECIPE_TABLE
                + " INNER JOIN "
                + INGREDIENT_TABLE
                + " ON "
                + "Recipe.recipeid"
                + " = "
                + "Ingredient.recipeid");

        Cursor cursor = qb.query(db,sqlSelect, "ingredientname LIKE ?",new String[]{"%"+ingredient+"%"} ,null ,null , "type, name",null);
        List<Recipe> result = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do{
                Recipe recipe = new Recipe();
                recipe.setRecipeid(cursor.getInt(cursor.getColumnIndex("recipeid")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                recipe.setType(cursor.getString(cursor.getColumnIndex("type")));
                recipe.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                recipe.setIngredients(cursor.getString(cursor.getColumnIndex("ingredients")));
                recipe.setInstruction(cursor.getString(cursor.getColumnIndex("instruction")));
                recipe.setImageid(cursor.getString(cursor.getColumnIndex("imageid")));
                recipe.setNutritionimage(cursor.getString(cursor.getColumnIndex("nutritionimage")));
                recipe.setCookingtime(cursor.getInt(cursor.getColumnIndex("cookingtime")));
                recipe.setTotalcost(cursor.getFloat(cursor.getColumnIndex("totalcost")));
                recipe.setNumservings(cursor.getInt(cursor.getColumnIndex("numservings")));
                recipe.setNumcalories(cursor.getInt(cursor.getColumnIndex("numcalories")));

                result.add(recipe);
            }while(cursor.moveToNext());
        }
        return result;
    }

    //Get recipes by calories
    public List<Recipe> getRecipeByCalories(String calories)
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Attributes as they appear in the database
        String[] sqlSelect = {"recipeid","name","type","description","ingredients","instruction","imageid","nutritionimage","cookingtime","totalcost","numservings","numcalories"};
        String RECIPE_TABLE = "Recipe"; //Table name as it is in database

        qb.setTables(RECIPE_TABLE);

        Cursor cursor = qb.query(db,sqlSelect, "numcalories <= ?",new String[]{calories} ,null ,null , "numcalories, type, name",null);
        List<Recipe> result = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do{
                Recipe recipe = new Recipe();
                recipe.setRecipeid(cursor.getInt(cursor.getColumnIndex("recipeid")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                recipe.setType(cursor.getString(cursor.getColumnIndex("type")));
                recipe.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                recipe.setIngredients(cursor.getString(cursor.getColumnIndex("ingredients")));
                recipe.setInstruction(cursor.getString(cursor.getColumnIndex("instruction")));
                recipe.setImageid(cursor.getString(cursor.getColumnIndex("imageid")));
                recipe.setNutritionimage(cursor.getString(cursor.getColumnIndex("nutritionimage")));
                recipe.setCookingtime(cursor.getInt(cursor.getColumnIndex("cookingtime")));
                recipe.setTotalcost(cursor.getFloat(cursor.getColumnIndex("totalcost")));
                recipe.setNumservings(cursor.getInt(cursor.getColumnIndex("numservings")));
                recipe.setNumcalories(cursor.getInt(cursor.getColumnIndex("numcalories")));

                result.add(recipe);
            }while(cursor.moveToNext());
        }
        return result;
    }

    //Get recipes by max fat, max protein and max carbs
    public List<Recipe> getRecipeByNutrition(String fat, String protein, String carbs)
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Attributes as they appear in the database
        String[] sqlSelect = {"Recipe.recipeid","Recipe.name","Recipe.type","Recipe.description","Recipe.ingredients","Recipe.instruction","Recipe.imageid","Recipe.nutritionimage","Recipe.cookingtime","Recipe.totalcost","Recipe.numservings","Recipe.numcalories"};
        String RECIPE_TABLE = "Recipe"; //Table name as it is in database
        String NUTRITION_TABLE = "Nutrition"; //Table name as it is in database

        qb.setTables(RECIPE_TABLE
                + " INNER JOIN "
                + NUTRITION_TABLE
                + " ON "
                + "Recipe.recipeid"
                + " = "
                + "Nutrition.recipeid");

        Cursor cursor = qb.query(db,sqlSelect, "totalfat <= ? and protein <= ? and carbohydrates <= ?",new String[]{fat,protein,carbs} ,null ,null , "type, name",null);
        List<Recipe> result = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do{
                Recipe recipe = new Recipe();
                recipe.setRecipeid(cursor.getInt(cursor.getColumnIndex("recipeid")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                recipe.setType(cursor.getString(cursor.getColumnIndex("type")));
                recipe.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                recipe.setIngredients(cursor.getString(cursor.getColumnIndex("ingredients")));
                recipe.setInstruction(cursor.getString(cursor.getColumnIndex("instruction")));
                recipe.setImageid(cursor.getString(cursor.getColumnIndex("imageid")));
                recipe.setNutritionimage(cursor.getString(cursor.getColumnIndex("nutritionimage")));
                recipe.setCookingtime(cursor.getInt(cursor.getColumnIndex("cookingtime")));
                recipe.setTotalcost(cursor.getFloat(cursor.getColumnIndex("totalcost")));
                recipe.setNumservings(cursor.getInt(cursor.getColumnIndex("numservings")));
                recipe.setNumcalories(cursor.getInt(cursor.getColumnIndex("numcalories")));

                result.add(recipe);
            }while(cursor.moveToNext());
        }
        return result;
    }


}