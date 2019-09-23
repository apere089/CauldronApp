package fiu.team5cen4010.cauldron.HomeScreen;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.support.v7.app.ActionBar;
import android.support.design.widget.BottomNavigationView;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import fiu.team5cen4010.cauldron.Adapter.SearchAdapter;
import fiu.team5cen4010.cauldron.AdvancedSearch.AdvancedSearch;
import fiu.team5cen4010.cauldron.Database.Database;
import fiu.team5cen4010.cauldron.MainActivity;
import fiu.team5cen4010.cauldron.Profile.ProfileActivity;
import fiu.team5cen4010.cauldron.R;
import fiu.team5cen4010.cauldron.TitleActivity;
import fiu.team5cen4010.cauldron.UserDatabase;

public class HomeActivity extends AppCompatActivity {
    ////////////////////
    RecyclerView recyclerView, popularView;
    RecyclerView.LayoutManager layoutManager;
    LinearLayoutManager horizontalLayoutManager;
    SearchAdapter adapter, adapterPopular;
    ScrollView HomeScroller;
    MaterialSearchBar materialSearchBar;
    List<String> suggestList = new ArrayList<>();
    Database database;
    //////////////////

    private ActionBar toolbar;
    private Intent homeScreen, searchScreen, profileScreen;

    public static UserDatabase users;
    public static int userIndex;
    public static ArrayList<String> favorites;

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
        setContentView(R.layout.activity_home);

        HomeScroller = (ScrollView)findViewById(R.id.home_scroller);
        HomeScroller.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }

        });

        homeScreen = getIntent();

        searchScreen = new Intent(this, AdvancedSearch.class);


        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if(getIntent().hasExtra("userdatabase")){
            users = (UserDatabase) getIntent().getSerializableExtra("userdatabase");
            userIndex = (Integer) getIntent().getIntExtra("userindex", 0);
        }
        profileScreen = new Intent(this, ProfileActivity.class);
        profileScreen.putExtra("userdatabase",users);
        profileScreen.putExtra("userindex",userIndex);
        searchScreen.putExtra("userdatabase",users);
        searchScreen.putExtra("userindex",userIndex);

        favorites = users.getUserList().get(userIndex).getFavorites();

        //////////////////////////////////

        //Init View
        recyclerView = (RecyclerView)findViewById(R.id.recycler_search_home_new);
        horizontalLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setHasFixedSize(true);
        horizontalLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        popularView = (RecyclerView)findViewById(R.id.recycler_search_home_popular);
        layoutManager = new LinearLayoutManager(this);
        popularView.setLayoutManager(layoutManager);
        popularView.setHasFixedSize(true);


        materialSearchBar = (MaterialSearchBar)findViewById(R.id.search_bar_home);

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
                    //If close Search, just restore default
                    adapter = new SearchAdapter(getBaseContext(), database.getRecipesNew());
                    recyclerView.setAdapter(adapter);

                    adapterPopular =  new SearchAdapter(getBaseContext(), database.getRecipesPopular());
                    popularView.setAdapter(adapterPopular);
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

        adapter = new SearchAdapter(this, database.getRecipesNew());
        recyclerView.setAdapter(adapter);

        adapterPopular =  new SearchAdapter(this, database.getRecipesPopular());
        popularView.setAdapter(adapterPopular);
    }

    private void startSearch(String text) {

        Intent NameSearch = new Intent(this,MainActivity.class);

        //searchby is 4 for search bar (search recipe by name)
        int searchby = 4;
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


    //Once user is logged in, the back button won't close app so that it retains
    //user info until he/she decides to logout.
    //However, removing the app from 'recents' will close app
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    //Setting up onClick for the menu items in bottom nav bar
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){

                        case R.id.home:
                            finish(); //we clear previous activity which means back button will not get us to the old activity- Irrelevant for this since we also run "onBackPressed" but still efficient
                            startActivity(homeScreen);
                            break;
                        case R.id.search:
                            startActivity(searchScreen);
                            break;
                        case R.id.profile:
                            finish(); //here to fix the problem when editing info in profile so that we cant go back from profile to home
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


}