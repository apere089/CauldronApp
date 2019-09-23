package fiu.team5cen4010.cauldron;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {

    EditText usernameRecovery;
    EditText pinRecovery;

    UserDatabase users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        usernameRecovery = (EditText) findViewById(R.id.usernameRecovery);
        pinRecovery =  (EditText) findViewById(R.id.pinRecovery);

        users = (UserDatabase) getIntent().getSerializableExtra("userdatabase");
    }

    public boolean validateUsername(String user){
        String pattern = "^[a-zA-Z0-9]{3,30}$";
        return user.matches(pattern);
    }

    public void onClickGetPassword(View view) {
        String username = usernameRecovery.getText().toString();
        String pin = pinRecovery.getText().toString();

        //int id = indexofUser(username);

        if(username.trim().equals("")){
            usernameRecovery.setError("Username is required");
        }
        else if(pin.trim().equals("")){
            pinRecovery.setError("Recovery PIN is required");
        }
        else if(validateUsername(username) && pin.matches("\\d\\d\\d\\d")){
            String type = "forgotpw";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type,username,pin);
        }
        //else if(userinList(username) && users.getUserList().get(id).getPin().equals(pin)){
         //   MessageBox("Your password is: "+ users.getUserList().get(id).getPassword());
        //}
        else {
            MessageBox("Recovery Failed: Account not found!");
        }

    }

    public void MessageBox(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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

    public int indexofUser(String key){
        boolean found = false;
        int i = 0;
        int index = 0;
        while(!found && i < users.getUserList().size())
        {
            User u = users.getUserList().get(i);
            if(u.getName().equals(key))
            {
                found = true;
                index = i;
            }
            else {i++;}
        }
        return index;
    }
}
