package fiu.team5cen4010.cauldron.Profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fiu.team5cen4010.cauldron.Adapter.SearchAdapter;
import fiu.team5cen4010.cauldron.AdvancedSearch.AdvancedSearch;
import fiu.team5cen4010.cauldron.BackgroundWorker;
import fiu.team5cen4010.cauldron.Database.Database;
import fiu.team5cen4010.cauldron.HomeScreen.HomeActivity;
import fiu.team5cen4010.cauldron.MainActivity;
import fiu.team5cen4010.cauldron.R;
import fiu.team5cen4010.cauldron.SignUpActivity;
import fiu.team5cen4010.cauldron.TitleActivity;
import fiu.team5cen4010.cauldron.User;
import fiu.team5cen4010.cauldron.UserDatabase;

public class ProfileActivity extends AppCompatActivity {

    TextView welcome_TV, username_TV, password_TV, pin_TV;
    public static UserDatabase users;
    public static int userIndex;
    public static String newusername;

    EditText pin;

    private Intent homeScreen, searchScreen, profileScreen;

    TextView editUsername, editPassword, editPin;
    Dialog dialogUsername, dialogPassword, dialogPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.navigation_profile);
        bottomNav.setSelectedItemId(R.id.profile);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        homeScreen = new Intent(this, HomeActivity.class);

        searchScreen = new Intent(this, AdvancedSearch.class);

        profileScreen = getIntent();

        if(getIntent().hasExtra("userdatabase")){
            users = (UserDatabase) getIntent().getSerializableExtra("userdatabase");
            userIndex = (Integer) getIntent().getIntExtra("userindex", 0);
        }

        homeScreen.putExtra("userdatabase",users);
        homeScreen.putExtra("userindex",userIndex);
        searchScreen.putExtra("userdatabase",users);
        searchScreen.putExtra("userindex",userIndex);

        //MessageBox(users.getUserList().get(userIndex).getName());
        String setWelcome = users.getUserList().get(userIndex).getFullname();
        String setUsername = users.getUserList().get(userIndex).getName();

        String setPassword="";
        String getPassword = users.getUserList().get(userIndex).getPassword();
        for(int i = 0; i < getPassword.length(); i++){
            setPassword = setPassword + "*";
        }

        String setPin="";
        String getPin = users.getUserList().get(userIndex).getPin();
        for(int i = 0; i < getPin.length(); i++){
            setPin = setPin + "*";
        }

        welcome_TV = (TextView)findViewById(R.id.welcome_profile_activity);
        welcome_TV.setText("Welcome :)\n"+setWelcome);
        username_TV = (TextView)findViewById(R.id.username_profile);
        username_TV.setText("Username: "+setUsername);
        password_TV = (TextView)findViewById(R.id.password_profile);
        password_TV.setText("Password: "+setPassword);
        pin_TV = (TextView)findViewById(R.id.pin_profile);
        pin_TV.setText("Pin: "+setPin);

        pin = (EditText)findViewById(R.id.edittext_for_pin_profile);

        //Dialog to edit username
        editUsername = (TextView)findViewById(R.id.editusername);
        editUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUsername = new Dialog(ProfileActivity.this);
                dialogUsername.setContentView(R.layout.dialog_edit_username);
                final EditText getPin = (EditText)dialogUsername.findViewById(R.id.editusername_getPin);
                final EditText getUsername = (EditText)dialogUsername.findViewById(R.id.editusername_getUsername);
                final EditText confirmUsername = (EditText)dialogUsername.findViewById(R.id.editusername_confirmUsername);
                Button saveUsername = (Button)dialogUsername.findViewById(R.id.editusername_save);

                getPin.setEnabled(true);
                getUsername.setEnabled(true);
                confirmUsername.setEnabled(true);
                saveUsername.setEnabled(true);

                saveUsername.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(getPin.getText().toString().trim().equals(""))
                        {
                            getPin.setError("Enter security PIN");
                        }
                        else if(!getPin.getText().toString().equals(users.getUserList().get(userIndex).getPin()))
                        {
                            getPin.setError("Enter security PIN");
                            MessageBox("Sorry, that is not your security PIN!");
                        }
                        else if(getUsername.getText().toString().trim().equals(""))
                        {
                            getUsername.setError("Enter new username");
                        }
                        else if(!validateUsername(getUsername.getText().toString())){
                            getUsername.setError("You have entered an invalid username!\nA valid one must contain the following:\n(1) At least 3 characters.\n(2) At least a letter or a number.");
                        }
                        else if(userinList(getUsername.getText().toString()))
                        {
                            getUsername.setError("Enter new username");
                            MessageBox("Username already in use!");
                        }
                        else if(confirmUsername.getText().toString().trim().equals(""))
                        {
                            confirmUsername.setError("Confirm new username");
                        }
                        else if(!getUsername.getText().toString().equals(confirmUsername.getText().toString()))
                        {
                            confirmUsername.setError("Confirm new username");
                            MessageBox("New username must match!");
                        }else{
                            String current_username = users.getUserList().get(userIndex).getName();
                            String type = "updateuserinfo";
                            String toupdate = "username";
                            String new_username = getUsername.getText().toString();
                            newusername = new_username;
                            BackgroundWorker backgroundWorker = new BackgroundWorker(ProfileActivity.this);
                            backgroundWorker.execute(type,toupdate,current_username, new_username);


                                //users.getUserList().get(userIndex).setName(getUsername.getText().toString());
                               /// MessageBox("Username successfully changed!");
                                //dialogUsername.cancel();
                                //finish();
                                //startActivity(getIntent());




                        }

                    }
                });
                //Show the dialog, please
                dialogUsername.show();
            }
        });

        //Dialog to edit password
        editPassword = (TextView)findViewById(R.id.editpassword);
        editPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPassword = new Dialog(ProfileActivity.this);
                dialogPassword.setContentView(R.layout.dialog_edit_password);
                final EditText getcurrentpw = (EditText)dialogPassword.findViewById(R.id.editpassword_getcurrentpw);
                final EditText getnewpw = (EditText)dialogPassword.findViewById(R.id.editpassword_getnewpw);
                final EditText confirmnewpw = (EditText)dialogPassword.findViewById(R.id.editpassword_confirmnewpw);
                Button savePassword = (Button)dialogPassword.findViewById(R.id.editpassword_save);

                getcurrentpw.setEnabled(true);
                getnewpw.setEnabled(true);
                confirmnewpw.setEnabled(true);
                savePassword.setEnabled(true);

                savePassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(getcurrentpw.getText().toString().trim().equals(""))
                        {
                            getcurrentpw.setError("Enter current password");
                        }
                        else if(!getcurrentpw.getText().toString().equals(users.getUserList().get(userIndex).getPassword()))
                        {
                            getcurrentpw.setError("Enter current password");
                            MessageBox("Sorry, that is not your current password!");
                        }
                        else if(getnewpw.getText().toString().trim().equals(""))
                        {
                            getnewpw.setError("Enter new password");
                        }
                        else if(!validatePassword(getnewpw.getText().toString())){
                            getnewpw.setError("You have entered an invalid password!\nA valid one must contain the following:\n(1) At least 8 characters.\n(2) At least one uppercase letter.\n(3) At least one lowercase letter.\n(4) At least one number.\n(5) At least one special symbol.");
                        }
                        else if(confirmnewpw.getText().toString().trim().equals(""))
                        {
                            confirmnewpw.setError("Confirm new password");
                        }
                        else if(!getnewpw.getText().toString().equals(confirmnewpw.getText().toString()))
                        {
                            confirmnewpw.setError("Confirm new password");
                            MessageBox("New password must match!");
                        }else{
                            String current_username = users.getUserList().get(userIndex).getName();
                            users.getUserList().get(userIndex).setPassword(getnewpw.getText().toString());
                            MessageBox("Password successfully changed!");
                            dialogPassword.cancel();
                            finish();

                            String type = "updateuserinfo";
                            String toupdate = "password";
                            String new_password = getnewpw.getText().toString();
                            BackgroundWorker backgroundWorker = new BackgroundWorker(ProfileActivity.this);
                            backgroundWorker.execute(type,toupdate,current_username,new_password);

                            startActivity(getIntent());
                        }

                    }
                });
                //Show the dialog, please
                dialogPassword.show();
            }
        });

        //Dialog to edit PIN
        editPin = (TextView)findViewById(R.id.editpin);
        editPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPin = new Dialog(ProfileActivity.this);
                dialogPin.setContentView(R.layout.dialog_edit_pin);
                final EditText getcurrentPin = (EditText)dialogPin.findViewById(R.id.editpin_getcurrentPin);
                final EditText getnewPin = (EditText)dialogPin.findViewById(R.id.editpin_getnewPin);
                final EditText confirmnewPin = (EditText)dialogPin.findViewById(R.id.editpin_confirmnewPin);
                Button savePin = (Button)dialogPin.findViewById(R.id.editpin_save);

                getcurrentPin.setEnabled(true);
                getnewPin.setEnabled(true);
                confirmnewPin.setEnabled(true);
                savePin.setEnabled(true);

                savePin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(getcurrentPin.getText().toString().trim().equals(""))
                        {
                            getcurrentPin.setError("Enter current PIN");
                        }
                        else if(!getcurrentPin.getText().toString().equals(users.getUserList().get(userIndex).getPin()))
                        {
                            getcurrentPin.setError("Enter current PIN");
                            MessageBox("Sorry, that is not your current PIN!");
                        }
                        else if(getnewPin.getText().toString().trim().equals(""))
                        {
                            getnewPin.setError("Enter new PIN");
                        }
                        else if(getnewPin.length() != 4){
                            getnewPin.setError("PIN must be 4 digits");
                            MessageBox("PIN not allowed!");
                        }
                        else if(confirmnewPin.getText().toString().trim().equals(""))
                        {
                            confirmnewPin.setError("Confirm new PIN");
                        }
                        else if(confirmnewPin.length() != 4){
                            confirmnewPin.setError("PIN must be 4 digits");
                            MessageBox("PIN not allowed!");
                        }
                        else if(!getnewPin.getText().toString().equals(confirmnewPin.getText().toString()))
                        {
                            confirmnewPin.setError("Confirm new PIN");
                            MessageBox("New PIN must match!");
                        }else{
                            String current_username = users.getUserList().get(userIndex).getName();
                            users.getUserList().get(userIndex).setPin(getnewPin.getText().toString());
                            MessageBox("PIN successfully changed!");
                            dialogPin.cancel();
                            finish();

                            String type = "updateuserinfo";
                            String toupdate = "pin";
                            String new_pin = getnewPin.getText().toString();
                            BackgroundWorker backgroundWorker = new BackgroundWorker(ProfileActivity.this);
                            backgroundWorker.execute(type,toupdate,current_username,new_pin);

                            startActivity(getIntent());
                        }

                    }
                });
                //Show the dialog, please
                dialogPin.show();
            }
        });


    }

    //Allows the back button to minimize the app instead of going back to previous activity
    //so that user info can only be passed safely through intents from search bar buttons
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public boolean validateUsername(String user){
        String pattern = "^[a-zA-Z0-9]{3,30}$";
        return user.matches(pattern);
    }

    public boolean validatePassword(String pass){
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[._$*()#@!%])[A-Za-z\\d._$*()#@!%]{8,}$";
        return pass.matches(pattern);
    }

    public boolean userinList(String key){
        boolean found = false;
        int i = 0;
        while(!found && i < users.getUserList().size())
        {
            User u = users.getUserList().get(i);
            if(u.getName().equals(key))
            {
                found = true;
            }
            else {i++;}
        }
        return found;
    }

    public void MessageBox(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onClickLogOut(View view) {
        Intent logout = new Intent(this,TitleActivity.class);
        MessageBox("You have been successfully logged out!");
        logout.putExtra("databaseupdated",users);
        startActivity(logout);
    }

    public void onClickDeleteAccount(View view) {
        String getPin = pin.getText().toString();
        if(getPin.equals(users.getUserList().get(userIndex).getPin()))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
            builder.setMessage("Do you really want to proceed with the account deletion?").setTitle("Warning")
                    .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent logoutAndDelete = new Intent(getApplicationContext(),TitleActivity.class);


                            String current_username = users.getUserList().get(userIndex).getName();
                            users.getUserList().remove(userIndex);
                            logoutAndDelete.putExtra("databaseupdated",users);
                            MessageBox("Account Deleted!");

                            String type = "updateuserinfo";
                            String toupdate = "deleteuser";
                            BackgroundWorker backgroundWorker = new BackgroundWorker(getApplicationContext());
                            backgroundWorker.execute(type,toupdate,current_username,current_username);

                            startActivity(logoutAndDelete);
                        }
                    }).setNegativeButton("KEEP",null);

            AlertDialog alert = builder.create();
            alert.show();
        }else if(getPin.trim().equals("")){
            pin.setError("PIN number is required");

        }else{
            pin.setError("PIN number is required");
            MessageBox("PIN does not match!");
        }
    }

    //Setting up onClick for the menu items in bottom nav bar
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){

                        case R.id.home:
                            homeScreen.putExtra("userdatabase",users);
                            startActivity(homeScreen);
                            break;
                        case R.id.search:
                            searchScreen.putExtra("userdatabase",users);
                            startActivity(searchScreen);
                            break;
                        case R.id.profile:
                            finish();
                            startActivity(profileScreen);
                            break;

                    }


                    return false;
                }
            };

    public void onClickShowFavorites(View view) {
        Intent getFavorites = new Intent(this,MainActivity.class);

        //searchby is 5 to show Favorites (Saved Recipes)
        int searchby = 5;

        //String type = "getfavorites";
        //String username = users.getUserList().get(userIndex).getName();
        //BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        //backgroundWorker.execute(type,username);

        ArrayList<String> favorites =  users.getUserList().get(userIndex).getFavorites();
        getFavorites.putExtra("searchby",searchby);
        getFavorites.putExtra("userdatabase",users);
        getFavorites.putExtra("userindex",userIndex);

        if(!favorites.isEmpty())
        {
            startActivity(getFavorites);
        }else{
            MessageBox("You have no favorite recipe yet!");
        }

    }

}
