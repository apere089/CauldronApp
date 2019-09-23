package fiu.team5cen4010.cauldron;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import fiu.team5cen4010.cauldron.Adapter.SearchAdapter;
import fiu.team5cen4010.cauldron.AdvancedSearch.AdvancedSearch;
import fiu.team5cen4010.cauldron.Database.Database;
import fiu.team5cen4010.cauldron.HomeScreen.HomeActivity;
import fiu.team5cen4010.cauldron.Model.Recipe;
import fiu.team5cen4010.cauldron.Profile.ProfileActivity;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter adapter;

    MaterialSearchBar materialSearchBar;
    List<String> suggestList = new ArrayList<>();

    Database database;

    int searchBy;
    String ingredient1="", ingredient2="", ingredient3="";

    String calories = "0";

    String fat="0",protein="0",carbs="0";

    String cost="0.0";

    String recipename="";

    ArrayList<String> favorites;

    private Intent homeScreen, searchScreen, profileScreen;

    public static UserDatabase users;
    public static int userIndex;

    public static boolean active = false;

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeScreen =  new Intent(this, HomeActivity.class);

        searchScreen = new Intent(this, AdvancedSearch.class);

        profileScreen = new Intent(this, ProfileActivity.class);


        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.navigation_mainactivity);
        bottomNav.setSelectedItemId(R.id.search);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if(getIntent().hasExtra("userdatabase")){
            users = (UserDatabase) getIntent().getSerializableExtra("userdatabase");
            userIndex = (Integer) getIntent().getIntExtra("userindex", 0);
        }

        homeScreen.putExtra("userdatabase",users);
        homeScreen.putExtra("userindex",userIndex);
        searchScreen.putExtra("userdatabase",users);
        searchScreen.putExtra("userindex",userIndex);
        profileScreen.putExtra("userdatabase",users);
        profileScreen.putExtra("userindex",userIndex);

        if(getIntent().hasExtra("searchby"))
        {
            searchBy = (Integer) getIntent().getIntExtra("searchby", 0);

            //MessageBox(String.valueOf(searchBy));
            if(searchBy == 0) //By Ingredients
            {
                ingredient1 = (String) getIntent().getStringExtra("ingredient1");
                ingredient2 = (String) getIntent().getStringExtra("ingredient2");
                ingredient3 = (String) getIntent().getStringExtra("ingredient3");

                if (ingredient1.isEmpty()) ingredient1 = "123456";
                if (ingredient2.isEmpty()) ingredient2 = "123456";
                if (ingredient3.isEmpty()) ingredient3 = "123456";
            }
            else if(searchBy == 1) //By Calories
            {
                calories = (String) getIntent().getStringExtra("maxcalories");
            }
            else if(searchBy == 2) //By Nutrition
            {
                fat = (String) getIntent().getStringExtra("fat");
                protein = (String) getIntent().getStringExtra("protein");
                carbs = (String) getIntent().getStringExtra("carbs");

                if (fat.isEmpty()) fat = "123456";
                if (protein.isEmpty()) protein = "123456";
                if (carbs.isEmpty()) carbs = "123456";
            }
            else if(searchBy == 3) //By Cost
            {
                cost = (String) getIntent().getStringExtra("maxcost");
            }
            else if(searchBy == 4) //By name (from search bar - AdvancedSearch and HomeActivity)
            {
                recipename = (String)getIntent().getStringExtra("recipename");
            }
            else if(searchBy == 5) //By name (from 'Saved Recipes' button from ProfileActivity (Favorites Feature)
            {
                 favorites = users.getUserList().get(userIndex).getFavorites();
            }
            else //default All Recipes
            {
                searchBy = 10;
            }
        }
        else // default All Recipes - if not coming from AdvancedSearch (Its not the case if app transition(users object sync) remains unchanged)
        {
            searchBy = 10;
        }

        //Init View
        recyclerView = (RecyclerView)findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        materialSearchBar = (MaterialSearchBar)findViewById(R.id.search_bar);

        //Init Database
        database = new Database(this);

        //Setup search bar
        materialSearchBar.setHint("Search recipes by name");
        materialSearchBar.setCardViewElevation(10);
        loadSuggestList();
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<String> suggest = new ArrayList<>();
                for(String search:suggestList)
                {
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

                if(!enabled)
                {
                    //If close Search, just do nothing
                    //adapter = new SearchAdapter(getBaseContext(), database.getRecipe());
                    //recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                startSearch(text.toString());
            }

            

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        //Get a list of recipes from search by name (search bar from AdvancedSearch)
        List<Recipe> checkResult_byName = database.getRecipeByName(recipename);

        //Get list of recipes for a single ingredient
        List<Recipe> check_ingredient1 = database.getRecipeBySingleIngredient(ingredient1);
        List<Recipe> check_ingredient2 = database.getRecipeBySingleIngredient(ingredient2);
        List<Recipe> check_ingredient3 = database.getRecipeBySingleIngredient(ingredient3);

        //Get a list of recipes from an inclusive search
        List<Recipe> checkResult_inclusive = database.getRecipeByIngredients_INCLUSIVE(ingredient1,ingredient2,ingredient3);

        //Get a list of recipes from an exclusive search
        List<Recipe> checkResult_exclusive = database.getRecipeByIngredients_EXCLUSIVE(ingredient1,ingredient2,ingredient3);

        //Get a list of recipes from search by calories
        List<Recipe> checkResult_byCalories = database.getRecipeByCalories(calories);

        //Get a list of recipes from search by nutrition
        List<Recipe> checkResult_byNutrition = database.getRecipeByNutrition(fat,protein,carbs);

        //Get a list of recipes from search by cost
        List<Recipe> checkResult_byCost = database.getRecipeByCost(cost);

        switch(searchBy)
        {
            //Case 0 means search by Ingredients
            case 0:
                    if(checkResult_exclusive.isEmpty())
                    {
                        adapter = new SearchAdapter(this, database.getRecipeByIngredients_EXCLUSIVE(ingredient1,ingredient2,ingredient3));
                        recyclerView.setAdapter(adapter);
                        //input is any and any is not found in db
                        MessageBox("Sorry, we got no recipes!");
                        break;
                    }
                    else if(!checkResult_inclusive.isEmpty() && !ingredient1.equals("123456") && !ingredient2.equals("123456") && !ingredient3.equals("123456")) //If list not empty means successful inclusive
                    {
                        adapter = new SearchAdapter(this, database.getRecipeByIngredients_INCLUSIVE(ingredient1,ingredient2,ingredient3));
                        recyclerView.setAdapter(adapter);
                        //input is 3 ingredients and all of them are found in db
                        MessageBox("Yayyy! We've got recipes with "+ingredient1+", "+ingredient2+" and " +ingredient3+"!");
                        break;
                    }
                    else if(checkResult_inclusive.isEmpty() && !ingredient1.equals("123456") && !ingredient2.equals("123456") && !ingredient3.equals("123456")) //If list is empty then we run exclusive search
                    {
                        adapter = new SearchAdapter(this, database.getRecipeByIngredients_EXCLUSIVE(ingredient1,ingredient2,ingredient3));
                        recyclerView.setAdapter(adapter);

                        //input is 3 ingredients, inclusive fails but the 3 are found exclusively
                        if(!check_ingredient1.isEmpty() && !check_ingredient2.isEmpty() && !check_ingredient3.isEmpty()) MessageBox("We got no recipes with all three but here are some with "+ingredient1+" and/or "+ingredient2+" and/or "+ingredient3+"!");

                        //input is 3 ingredients but 1 of those 3 is not found in db
                        if(check_ingredient1.isEmpty() && !check_ingredient2.isEmpty() && !check_ingredient3.isEmpty()) MessageBox("We got no recipes with "+ingredient1+" but here are some with "+ingredient2+" and/or "+ingredient3+"!");
                        if(!check_ingredient1.isEmpty() && check_ingredient2.isEmpty() && !check_ingredient3.isEmpty()) MessageBox("We got no recipes with "+ingredient2+" but here are some with "+ingredient1+" and/or "+ingredient3+"!");
                        if(!check_ingredient1.isEmpty() && !check_ingredient2.isEmpty() && check_ingredient3.isEmpty()) MessageBox("We got no recipes with "+ingredient3+" but here are some with "+ingredient1+" and/or "+ingredient2+"!");

                        //input is 3 ingredients but 2 of those 3 are not found in db
                        if(check_ingredient1.isEmpty() && check_ingredient2.isEmpty() && !check_ingredient3.isEmpty()) MessageBox("We got no recipes with "+ingredient1+" or "+ingredient2+" but enjoy these with "+ingredient3+"!");
                        if(check_ingredient1.isEmpty() && !check_ingredient2.isEmpty() && check_ingredient3.isEmpty()) MessageBox("We got no recipes with "+ingredient1+" or "+ingredient3+" but enjoy these with "+ingredient2+"!");
                        if(!check_ingredient1.isEmpty() && check_ingredient2.isEmpty() && check_ingredient3.isEmpty()) MessageBox("We got no recipes with "+ingredient2+" or "+ingredient3+" but enjoy these with "+ingredient1+"!");

                        break;
                    }
                    else
                    {
                        adapter = new SearchAdapter(this, database.getRecipeByIngredients_EXCLUSIVE(ingredient1,ingredient2,ingredient3));
                        recyclerView.setAdapter(adapter);

                        //input is a single 1 ingredient which is found in database
                        if(!check_ingredient1.isEmpty() && ingredient2 == "123456" && ingredient3 == "123456") MessageBox("We got some tasty recipes with "+ingredient1+" for you!");
                        if(ingredient1 == "123456" && !check_ingredient2.isEmpty() && ingredient3 == "123456") MessageBox("We got some tasty recipes with "+ingredient2+" for you!");
                        if(ingredient1 == "123456" && ingredient2 == "123456" && !check_ingredient3.isEmpty()) MessageBox("We got some tasty recipes with "+ingredient3+" for you!");

                        //input is 2 ingredients and both are found in database
                        if(!check_ingredient1.isEmpty() && !check_ingredient2.isEmpty() && check_ingredient3.isEmpty()) MessageBox("We got some tasty recipes with "+ingredient1+" and/or "+ingredient2+" for you!");
                        if(!check_ingredient1.isEmpty() && check_ingredient2.isEmpty() && !check_ingredient3.isEmpty()) MessageBox("We got some tasty recipes with "+ingredient1+" and/or "+ingredient3+" for you!");
                        if(check_ingredient1.isEmpty() && !check_ingredient2.isEmpty() && !check_ingredient3.isEmpty()) MessageBox("We got some tasty recipes with "+ingredient2+" and/or "+ingredient3+" for you!");

                        //input is 2 ingredients and one is found while the other is not found
                        if(check_ingredient1.isEmpty()&& ingredient1 != "123456" && ingredient2 == "123456" && !check_ingredient3.isEmpty()) MessageBox("We could not find recipes with "+ingredient1+" but take these with "+ingredient3+"!");
                        if(check_ingredient1.isEmpty()&& ingredient1 != "123456" && ingredient3 == "123456" && !check_ingredient2.isEmpty()) MessageBox("We could not find recipes with "+ingredient1+" but take these with "+ingredient2+"!");
                        if(check_ingredient2.isEmpty()&& ingredient2 != "123456" && ingredient1 == "123456" && !check_ingredient3.isEmpty()) MessageBox("We could not find recipes with "+ingredient2+" but take these with "+ingredient3+"!");
                        if(check_ingredient2.isEmpty()&& ingredient2 != "123456" && ingredient3 == "123456" && !check_ingredient1.isEmpty()) MessageBox("We could not find recipes with "+ingredient2+" but take these with "+ingredient1+"!");
                        if(check_ingredient3.isEmpty()&& ingredient3 != "123456" && ingredient1 == "123456" && !check_ingredient2.isEmpty()) MessageBox("We could not find recipes with "+ingredient3+" but take these with "+ingredient2+"!");
                        if(check_ingredient3.isEmpty()&& ingredient3 != "123456" && ingredient2 == "123456" && !check_ingredient1.isEmpty()) MessageBox("We could not find recipes with "+ingredient3+" but take these with "+ingredient1+"!");

                        break;
                    }

            //Case 1 means search by Calories
            case 1:
                    if(checkResult_byCalories.isEmpty())
                    {
                        adapter = new SearchAdapter(this, database.getRecipeByCalories(calories));
                        recyclerView.setAdapter(adapter);
                        MessageBox("Sorry, we got no recipes!");
                        break;
                    }
                    else
                    {
                        adapter = new SearchAdapter(this, database.getRecipeByCalories(calories));
                        recyclerView.setAdapter(adapter);
                        MessageBox("We got some tasty recipes for you!");
                        break;
                    }


            //Case 2 means search by Nutrition
            case 2:
                if(checkResult_byNutrition.isEmpty())
                {
                    adapter = new SearchAdapter(this, database.getRecipeByNutrition(fat,protein,carbs));
                    recyclerView.setAdapter(adapter);
                    MessageBox("Sorry, we got no recipes!");
                    break;
                }
                else
                {
                    adapter = new SearchAdapter(this, database.getRecipeByNutrition(fat,protein,carbs));
                    recyclerView.setAdapter(adapter);
                    MessageBox("We got some tasty recipes for you!");
                    break;
                }

            //Case 3 means search by Cost
            case 3:
                if(checkResult_byCost.isEmpty())
                {
                    adapter = new SearchAdapter(this, database.getRecipeByCost(cost));
                    recyclerView.setAdapter(adapter);
                    MessageBox("Sorry, we got no recipes!");
                    break;
                }
                else
                {
                    adapter = new SearchAdapter(this, database.getRecipeByCost(cost));
                    recyclerView.setAdapter(adapter);
                    MessageBox("We got some tasty recipes for you!");
                    break;
                }

            //Case 4 means search by name from Advanced Search search bar
            case 4: if(checkResult_byName.isEmpty())
                    {
                        startSearch(recipename);
                        MessageBox("Sorry, we got no recipes!");
                        break;
                    }
                    else
                    {
                        startSearch(recipename);
                        MessageBox("We got some tasty recipes for you!");
                        break;
                    }

                //Case 5 means search by name from 'Favorites' button (From ProfileActivity)
            case 5:
                adapter = new SearchAdapter(this, database.getSavedRecipes(favorites));
                recyclerView.setAdapter(adapter);
                MessageBox("Enjoy your favorites!");
                break;

            //default here searchby was set to 10
            default:  //Init Adapter default set all recipes
                adapter = new SearchAdapter(this, database.getRecipe());
                recyclerView.setAdapter(adapter);
                MessageBox("Enjoy all our recipes!");
                break;
        }

    }

    private void startSearch(String text) {

        adapter = new SearchAdapter(this,database.getRecipeByName(text));
        recyclerView.setAdapter(adapter);
    }

    private void loadSuggestList() {
        suggestList = database.getRecipeName();
        materialSearchBar.setLastSuggestions(suggestList);
    }

    public void MessageBox(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    //Setting up onClick for the menu items in bottom nav bar
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){

                        case R.id.home:
                            startActivity(homeScreen);
                            break;
                        case R.id.search:
                            startActivity(searchScreen);
                            break;
                        case R.id.profile:
                            finish(); //here to fix the problem when editing info in profile so that we cant go back from profile to main
                            startActivity(profileScreen);
                            break;

                    }


                    return false;
                }
            };
}
