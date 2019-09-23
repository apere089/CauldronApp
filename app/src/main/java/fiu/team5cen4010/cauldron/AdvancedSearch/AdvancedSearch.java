package fiu.team5cen4010.cauldron.AdvancedSearch;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import fiu.team5cen4010.cauldron.Adapter.SearchAdapter;
import fiu.team5cen4010.cauldron.Database.Database;
import fiu.team5cen4010.cauldron.HomeScreen.HomeActivity;
import fiu.team5cen4010.cauldron.MainActivity;
import fiu.team5cen4010.cauldron.Profile.ProfileActivity;
import fiu.team5cen4010.cauldron.R;
import fiu.team5cen4010.cauldron.UserDatabase;

public class AdvancedSearch extends AppCompatActivity {

    MaterialSearchBar materialSearchBar;
    List<String> suggestList = new ArrayList<>();
    Database database;

    int searchby;
    Animation animation;
    EditText ingredient1, ingredient2, ingredient3;
    ImageButton ingredients_searchbutton;

    EditText maxcalories;
    ImageButton calories_searchbutton;

    EditText fat, protein, carbs;
    ImageButton nutrition_searchbutton;

    EditText cost;
    ImageButton cost_searchbutton;

    ImageButton ingredientsDropDownUp, caloriesDropDownUp, costDropDownUp, nutritionDropDownUp;

    private Intent homeScreen, searchScreen, profileScreen;
    UserDatabase users;
    int userIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);

        if(getIntent().hasExtra("userdatabase")){
            users = (UserDatabase) getIntent().getSerializableExtra("userdatabase");
            userIndex = (Integer) getIntent().getIntExtra("userindex", 0);
        }

        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.navigation_advancedSearch);
        bottomNav.setSelectedItemId(R.id.search);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        homeScreen = new Intent(this, HomeActivity.class);

        searchScreen = getIntent();

        profileScreen = new Intent(this, ProfileActivity.class);
        profileScreen.putExtra("userdatabase",users);
        profileScreen.putExtra("userindex",userIndex);
        homeScreen.putExtra("userdatabase",users);
        homeScreen.putExtra("userindex",userIndex);

        //Init Search Bar
        materialSearchBar = (MaterialSearchBar)findViewById(R.id.search_bar_advancedSearch);

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
                    //If close Search, just restore default (do nothing)
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

        //Search by Ingredients
        ingredient1 = (EditText) findViewById(R.id.ingredient1);
        ingredient2 = (EditText) findViewById(R.id.ingredient2);
        ingredient3 = (EditText) findViewById(R.id.ingredient3);
        ingredients_searchbutton = (ImageButton) findViewById(R.id.buttonSearchIngredients);

        //Search by Calories
        maxcalories = (EditText) findViewById(R.id.calories_ET);
        calories_searchbutton = (ImageButton) findViewById(R.id.buttonSearchCalories);

        //Search by Nutrition
        fat = (EditText) findViewById(R.id.fat);
        protein = (EditText) findViewById(R.id.protein);
        carbs = (EditText) findViewById(R.id.carbs);
        nutrition_searchbutton = (ImageButton) findViewById(R.id.buttonSearchNutrition);

        //Search by Cost
        cost = (EditText)findViewById(R.id.cost_ET);
        cost_searchbutton = (ImageButton) findViewById(R.id.buttonSearchCost);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_right);

        ingredientsDropDownUp = (ImageButton)findViewById(R.id.buttonIngredients);
        caloriesDropDownUp = (ImageButton)findViewById(R.id.buttonCalories);
        costDropDownUp = (ImageButton)findViewById(R.id.buttonCost);
        nutritionDropDownUp = (ImageButton)findViewById(R.id.buttonNutrition);
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
                            finish();
                            startActivity(searchScreen);
                            break;
                        case R.id.profile:
                            finish(); //here to fix the problem when editing info in profile so that we cant go back from profile to search
                            startActivity(profileScreen);
                            break;

                    }


                    return false;
                }
            };


    public void MessageBox(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private void startSearch(String text) {

        Intent NameSearch = new Intent(this,MainActivity.class);

        //searchby is 4 for search bar (search recipe by name)
        searchby = 4;
        NameSearch.putExtra("searchby",searchby);
        NameSearch.putExtra("recipename",text);
        NameSearch.putExtra("userdatabase",users);
        NameSearch.putExtra("userindex",userIndex);
        if(!text.trim().equals(""))
        {
            startActivity(NameSearch);
        }
    }

    private void loadSuggestList() {

        suggestList = database.getRecipeName();
        materialSearchBar.setLastSuggestions(suggestList);
    }

    public void onClickGetAllRecipes(View view) {
        Intent AllRecipesSearch = new Intent(this, MainActivity.class);

        //searchby is 10 or anything (different than from 0 to 4) for getting all recipes we have in database
        searchby = 10;

        AllRecipesSearch.putExtra("searchby", searchby);
        AllRecipesSearch.putExtra("userdatabase",users);
        AllRecipesSearch.putExtra("userindex",userIndex);
        startActivity(AllRecipesSearch);
    }

    //Just display the edit texts and the button to search for the "By Ingredients"
    public void onClickByIngredients(View view) {
        if (ingredient1.isShown()) {
            ingredient1.setVisibility(View.GONE);
            ingredient2.setVisibility(View.GONE);
            ingredient3.setVisibility(View.GONE);
            ingredients_searchbutton.setVisibility(View.GONE);
            ingredientsDropDownUp.setImageResource(R.mipmap.dropdown);
        } else {
            ingredient1.setVisibility(View.VISIBLE);
            ingredient2.setVisibility(View.VISIBLE);
            ingredient3.setVisibility(View.VISIBLE);
            ingredients_searchbutton.setVisibility(View.VISIBLE);
            ingredientsDropDownUp.setImageResource(R.mipmap.dropup);

            ingredient1.startAnimation(animation);
            ingredient2.startAnimation(animation);
            ingredient3.startAnimation(animation);
            ingredients_searchbutton.startAnimation(animation);
        }
    }

    //Check if a string is numeric
    public static boolean isNumeric(String strNum) {
        return strNum.matches("\\d+(\\.\\d+)?");
    }

    //Pass all search by ingredients data for query to MainActivity to display results
    public void onClickSearchByIngredients(View view) {
        Intent IngredientSearch = new Intent(this, MainActivity.class);

        //searchby is 0 for ingredient search
        searchby = 0;

        String ig1 = ingredient1.getText().toString();
        String ig2 = ingredient2.getText().toString();
        String ig3 = ingredient3.getText().toString();

        if (ig1.trim().equals("") && ig2.trim().equals("") && ig3.trim().equals("")) {
            MessageBox("Enter at least one ingredient!");
            ingredient1.setError("Enter an ingredient");
            ingredient2.setError("Enter an ingredient");
            ingredient3.setError("Enter an ingredient");
        }else if(!ig1.trim().equals("") && isNumeric(ig1)){
            ingredient1.setError("Enter an ingredient");
            MessageBox("Input must be text!");
        }else if(!ig2.trim().equals("") && isNumeric(ig2)){
            ingredient2.setError("Enter an ingredient");
            MessageBox("Input must be text!");
        }else if(!ig3.trim().equals("") && isNumeric(ig3)){
            ingredient3.setError("Enter an ingredient");
            MessageBox("Input must be text!");
        } else {
            IngredientSearch.putExtra("ingredient1", ig1);
            IngredientSearch.putExtra("ingredient2", ig2);
            IngredientSearch.putExtra("ingredient3", ig3);
            IngredientSearch.putExtra("searchby", searchby);
            IngredientSearch.putExtra("userdatabase",users);
            IngredientSearch.putExtra("userindex",userIndex);

            startActivity(IngredientSearch);
        }


    }

    //Just display the edit texts and the button to search for the "By Ingredients"
    public void onClickByCalories(View view) {
        if (maxcalories.isShown()) {
            maxcalories.setVisibility(View.GONE);
            calories_searchbutton.setVisibility(View.GONE);
            caloriesDropDownUp.setImageResource(R.mipmap.dropdown);
        } else {
            maxcalories.setVisibility(View.VISIBLE);
            calories_searchbutton.setVisibility(View.VISIBLE);
            caloriesDropDownUp.setImageResource(R.mipmap.dropup);

            maxcalories.startAnimation(animation);
            calories_searchbutton.startAnimation(animation);
        }
    }

    //Pass all search by calories data for query to MainActivity to display results
    public void onClickSearchByCalories (View view){
        Intent CaloriesSearch = new Intent(this, MainActivity.class);

        //searchby is 1 for calories search
        searchby = 1;

        String maxcal = maxcalories.getText().toString();

        if (maxcal.trim().equals("")) {
            maxcalories.setError("Enter max calories");
            MessageBox("Enter a max number of calories!");
        }else if(!maxcal.trim().equals("") && !isNumeric(maxcal)){
            maxcalories.setError("Enter max calories");
            MessageBox("Input must be numeric!");
        } else {
            CaloriesSearch.putExtra("maxcalories", maxcal);
            CaloriesSearch.putExtra("searchby", searchby);
            CaloriesSearch.putExtra("userdatabase",users);
            CaloriesSearch.putExtra("userindex",userIndex);

            startActivity(CaloriesSearch);
        }
    }



    public void onClickByNutrition(View view) {
        if (fat.isShown()) {
            fat.setVisibility(View.GONE);
            protein.setVisibility(View.GONE);
            carbs.setVisibility(View.GONE);
            nutrition_searchbutton.setVisibility(View.GONE);
            nutritionDropDownUp.setImageResource(R.mipmap.dropdown);
        } else {
            fat.setVisibility(View.VISIBLE);
            protein.setVisibility(View.VISIBLE);
            carbs.setVisibility(View.VISIBLE);
            nutrition_searchbutton.setVisibility(View.VISIBLE);
            nutritionDropDownUp.setImageResource(R.mipmap.dropup);

            fat.startAnimation(animation);
            protein.startAnimation(animation);
            carbs.startAnimation(animation);
            nutrition_searchbutton.startAnimation(animation);
        }
    }

    public void onClickSearchByNutrition(View view) {
        Intent NutritionSearch = new Intent(this, MainActivity.class);

        //searchby is 2 for nutrition search
        searchby = 2;

        String getfat = fat.getText().toString();
        String getprotein = protein.getText().toString();
        String getcarbs = carbs.getText().toString();

        if (getfat.trim().equals("") && getprotein.trim().equals("") && getcarbs.trim().equals("")) {
            MessageBox("Enter at least one nutritional value!");
            fat.setError("Enter max fat");
            protein.setError("Enter max protein");
            carbs.setError("Enter max carbs");
        }else if(!getfat.trim().equals("") && !isNumeric(getfat)){
            fat.setError("Enter max fat");
            MessageBox("Input must be numeric!");
        }else if(!getprotein.trim().equals("") && !isNumeric(getprotein)){
            protein.setError("Enter max protein");
            MessageBox("Input must be numeric!");
        }else if(!getcarbs.trim().equals("") && !isNumeric(getcarbs)){
            carbs.setError("Enter max carbs");
            MessageBox("Input must be numeric!");
        }
        else {
            NutritionSearch.putExtra("fat", getfat);
            NutritionSearch.putExtra("protein", getprotein);
            NutritionSearch.putExtra("carbs", getcarbs);
            NutritionSearch.putExtra("searchby", searchby);
            NutritionSearch.putExtra("userdatabase",users);
            NutritionSearch.putExtra("userindex",userIndex);

            startActivity(NutritionSearch);
        }
    }

    public void onClickByCost(View view) {
        if (cost.isShown()) {
            cost.setVisibility(View.GONE);
            cost_searchbutton.setVisibility(View.GONE);
            costDropDownUp.setImageResource(R.mipmap.dropdown);
        } else {
            cost.setVisibility(View.VISIBLE);
            cost_searchbutton.setVisibility(View.VISIBLE);
            costDropDownUp.setImageResource(R.mipmap.dropup);

            cost.startAnimation(animation);
            cost_searchbutton.startAnimation(animation);
        }
    }

    public void onClickSearchByCost(View view) {
        Intent CostSearch = new Intent(this, MainActivity.class);

        //searchby is 3 for cost search
        searchby = 3;

        String maxcost = cost.getText().toString();

        if (maxcost.trim().equals("")) {
            cost.setError("Enter max cost");
            MessageBox("Enter a number for the cost!");
        }else if(!maxcost.trim().equals("") && !isNumeric(maxcost)){
            cost.setError("Enter max cost");
            MessageBox("Input must be numeric!");
        } else {
            CostSearch.putExtra("maxcost", maxcost);
            CostSearch.putExtra("searchby", searchby);
            CostSearch.putExtra("userdatabase",users);
            CostSearch.putExtra("userindex",userIndex);

            startActivity(CostSearch);
        }
    }
}
