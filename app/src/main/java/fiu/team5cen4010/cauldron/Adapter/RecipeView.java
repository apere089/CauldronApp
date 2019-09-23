package fiu.team5cen4010.cauldron.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import fiu.team5cen4010.cauldron.AdvancedSearch.AdvancedSearch;
import fiu.team5cen4010.cauldron.HomeScreen.HomeActivity;
import fiu.team5cen4010.cauldron.Profile.ProfileActivity;
import fiu.team5cen4010.cauldron.R;
import fiu.team5cen4010.cauldron.UserDatabase;

public class RecipeView extends AppCompatActivity  {

    ImageView imageView, nutritionImage;
    TextView text_name, text_description, text_ingredients, text_instructions;
    TextView text_calories, text_cookingtime, text_servings;

    UserDatabase users;
    int userIndex;
    private Intent homeScreen, searchScreen, profileScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_view_layout);
        imageView = (ImageView) findViewById(R.id.recipeview_imageid);
        text_name = (TextView) findViewById(R.id.recipeview_name);
        text_description = (TextView) findViewById(R.id.recipeview_description);
        text_ingredients = (TextView) findViewById(R.id.recipeview_ingredients);
        text_instructions = (TextView) findViewById(R.id.recipeview_instructions);
        nutritionImage = (ImageView)findViewById(R.id.recipeview_nutritionimage);
        text_calories = (TextView) findViewById(R.id.recipeview_caloriestext);
        text_servings = (TextView) findViewById(R.id.recipeview_servingstext);
        text_cookingtime = (TextView) findViewById(R.id.recipeview_cookingtimetext);

        imageView.setImageResource(getIntent().getIntExtra("imageid",0));
        text_name.setText(getIntent().getStringExtra("name"));
        text_description.setText(getIntent().getStringExtra("description"));
        text_ingredients.setText(getIntent().getStringExtra("ingredients"));
        text_instructions.setText(getIntent().getStringExtra("instructions"));
        nutritionImage.setImageResource(getIntent().getIntExtra("nutrition",0));
        text_calories.setText(String.valueOf(getIntent().getIntExtra("numcalories",0)) + " cal");
        text_servings.setText(String.valueOf(getIntent().getIntExtra("numservings",0)) + " people");
        text_cookingtime.setText(String.valueOf(getIntent().getIntExtra("numtime",0)) + " min");

        text_description.setTextColor(Color.BLACK);
        text_instructions.setTextColor(Color.BLACK);
        text_ingredients.setTextColor(Color.BLACK);

        homeScreen =  new Intent(this, HomeActivity.class);

        searchScreen = new Intent(this, AdvancedSearch.class);

        profileScreen = new Intent(this, ProfileActivity.class);


        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.navigation_recipeview);
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

    }

    //Expand and collapse nutrition image when button "Nutritional Facts" is clicked inside a recipe view
    public void onClickNutrition(View view)
    {
        if(nutritionImage.isShown()){
            nutritionImage.setVisibility(View.GONE);
        }
        else{
            nutritionImage.setVisibility(View.VISIBLE);
            final ScrollView scrollview = (ScrollView)findViewById(R.id.recipeview_scrollview);
            scrollview.post(new Runnable() {
                @Override
                public void run() {
                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });


        }
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
                            finish(); //here to fix the problem when editing info in profile so that we cant go back from profile to recipeview
                            startActivity(profileScreen);
                            break;

                    }


                    return false;
                }
            };
}
