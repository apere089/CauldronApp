package fiu.team5cen4010.cauldron;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity implements Serializable {

    static EditText usernameView;
    EditText passwordView;
    EditText confirmView;
    EditText pinView;
    EditText fullnameView;
    static EditText emailView;

    UserDatabase users;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameView = (EditText) findViewById(R.id.usernameInput);
        fullnameView = (EditText) findViewById(R.id.fullNameInput);
        emailView = (EditText) findViewById(R.id.emailInput);
        passwordView = (EditText) findViewById(R.id.passwordInput);
        confirmView = (EditText) findViewById(R.id.confirmInput);
        pinView =  (EditText) findViewById(R.id.recoverypinInput);

        users = (UserDatabase) getIntent().getSerializableExtra("userdatabase");
    }

    public void onClickRegister(View view){
        Intent signup_tologin = new Intent(this, TitleActivity.class);

        String username = usernameView.getText().toString();
        String fullname = fullnameView.getText().toString();
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        String confirm = confirmView.getText().toString();
        String pin = pinView.getText().toString();
        ArrayList<String> favorites = new ArrayList<String>();

        boolean isConfirmed = confirmPassword(password, confirm);
        boolean userinlist = userinList(username);

        if(username.trim().equals("")){
            usernameView.setError("Username is required");
        }
        else if(userinlist){
            usernameView.setError("Username is required");
            MessageBox("Username already in use!");
        }
        else if(!validateUsername(username)){
            usernameView.setError("You have entered an invalid username!\nA valid one must contain the following:\n(1) At least 3 characters.\n(2) At least a letter or a number.");
        }
        else if(fullname.trim().equals("")){
            fullnameView.setError("Full name is required");
        }
        else if(!validateFullName(fullname)){
            fullnameView.setError("Full name is invalid!\nPlease try again.");
        }
        else if(email.trim().equals("")){
            emailView.setError("E-mail is required");
        }
        else if(!validateEmail(email)){
            emailView.setError("E-mail is invalid!\nPlease try again.");
        }
        else if(password.trim().equals("")){
            passwordView.setError("Password is required");
        }
        else if(!validatePassword(password)){
            passwordView.setError("You have entered an invalid password!\nA valid one must contain the following:\n(1) At least 8 characters.\n(2) At least one uppercase letter.\n(3) At least one lowercase letter.\n(4) At least one number.\n(5) At least one special symbol.");
        }
        else if(confirm.trim().equals("")) {
            confirmView.setError("Confirm Password is required");
        }
        else if(!isConfirmed){
            confirmView.setError("Confirm Password must match");
            MessageBox("Passwords Do Not Match!");
        }
        else if(pin.trim().equals("")){
            pinView.setError("Recovery PIN is required");

        }else if(pin.length() != 4 || !pin.matches("\\d\\d\\d\\d")){
            pinView.setError("Recovery PIN must be 4 digits");
            MessageBox("Recovery PIN not allowed!");
        }
        else if(isConfirmed){

            String type = "register";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type,username,password,fullname,email,pin);
            //FOR NOW LETS CONSIDER NAME AND EMAIL STATIC

            //MessageBox("Registration Success!" + "\n" + "Login Now! Enjoy Cauldron!");
            //addAccount(username, password,pin,favorites);
            //signup_tologin.putExtra("databaseupdated",users);
            //startActivity(signup_tologin);
        }
    }

    public void MessageBox(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void addAccount(String username, String fullname,String email, String password, String pin, ArrayList<String> favorites){
         user = new User(username,fullname,email, password, pin, favorites);
         users.addUser(user);
    }

    public boolean confirmPassword(String pass, String confirm){
        if(pass.equals(confirm)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean validateUsername(String user){
        String pattern = "^[a-zA-Z0-9]{3,30}$";
        return user.matches(pattern);
    }

    public boolean validatePassword(String pass){
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[._$*()#@!%])[A-Za-z\\d._$*()#@!%]{8,}$";
        return pass.matches(pattern);
    }

    public boolean validateFullName(String name){
        String pattern = "^[-a-zA-Z]{1,30}(?: [-a-zA-Z]+){0,2}$"; //Allows for hyphens and only 2 blank spaces (between your first and last and between your 1st last and 2nd last)
        return name.matches(pattern);
    }

    public boolean validateEmail(String email){
        //String pattern = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+";

        // As per the HTML5 Specification
        String pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        return email.matches(pattern);
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

}
