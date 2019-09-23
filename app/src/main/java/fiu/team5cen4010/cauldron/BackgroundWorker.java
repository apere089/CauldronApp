package fiu.team5cen4010.cauldron;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import fiu.team5cen4010.cauldron.HomeScreen.HomeActivity;
import fiu.team5cen4010.cauldron.Profile.ProfileActivity;

/**
 * Created by ProgrammingKnowledge on 1/5/2016.
 */
public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    public BackgroundWorker(Context ctx) {
        context = ctx;
    }
    String login_username,login_password,hell;
    String reg_username, reg_password, reg_name, reg_email, reg_pin;
    String forgotpw_user, forgotpw_pin;
    String current_username, update_user_info;
    String username_getfavorites;
    String username_setfavorites, favorite_recipe;
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        String login_url = "https://thecauldronapp.000webhostapp.com/PHPMain/login.php";                  //http://cauldron.onthewifi.com/login.php              THESE WORK ONLY IF XAMPP SERVER IS RUNNING ON LOCALHOST
        String register_url = "https://thecauldronapp.000webhostapp.com/PHPMain/register.php";            //http://cauldron.onthewifi.com/register.php
        String forgotpw_url = "https://thecauldronapp.000webhostapp.com/PHPMain/forgotPassword.php";            //http://cauldron.onthewifi.com/forgotpw.php
        String update_url = "https://thecauldronapp.000webhostapp.com/PHPMain/updateuserinfo.php";        //http://cauldron.onthewifi.com/updateuserinfo.php
        String setfavorites_url = "https://thecauldronapp.000webhostapp.com/PHPMain/setfavorites.php";    //http://cauldron.onthewifi.com/setfavorites.php

        //String login_url = "http://cauldron.onthewifi.com/login.php ";
        //String register_url = "http://cauldron.onthewifi.com/register.php";
        //String forgotpw_url = "http://cauldron.onthewifi.com/forgotpw.php";
        //String update_url = "http://cauldron.onthewifi.com/updateuserinfo.php";
        //String setfavorites_url = "http://cauldron.onthewifi.com/setfavorites.php";

        //String login_url = "http://192.168.0.117/login.php ";
        //String register_url = "http://192.168.0.117/register.php";
        //String forgotpw_url = "http://192.168.0.117/forgotpw.php";
        //String update_url = "http://192.168.0.117/updateuserinfo.php";
        //String getfavorites_url = "http://192.168.0.117/getfavorites.php";
        //String setfavorites_url = "http://192.168.0.117/setfavorites.php";

        if(type.equals("login")){
            try {
                login_username = params[1];
                login_password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                String header = "Basic " + new String(android.util.Base64.encode("cauldronuserCB:androidLife000".getBytes(), android.util.Base64.NO_WRAP));
                httpURLConnection.addRequestProperty("Authorization", header);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(login_username,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(login_password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("register")){
            try {
                reg_username = params[1];
                reg_password = params[2];
                reg_name = params[3];
                reg_email = params[4];
                reg_pin = params[5];

                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                String header = "Basic " + new String(android.util.Base64.encode("cauldronuserCB:androidLife000".getBytes(), android.util.Base64.NO_WRAP));
                httpURLConnection.addRequestProperty("Authorization", header);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(reg_username,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(reg_password,"UTF-8")+"&"
                        +URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(reg_name,"UTF-8")+"&"
                        +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(reg_email,"UTF-8")+"&"
                        +URLEncoder.encode("pin","UTF-8")+"="+URLEncoder.encode(reg_pin,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(type.equals("forgotpw")){
            try {
                forgotpw_user = params[1];
                forgotpw_pin = params[2];

                URL url = new URL(forgotpw_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                String header = "Basic " + new String(android.util.Base64.encode("cauldronuserCB:androidLife000".getBytes(), android.util.Base64.NO_WRAP));
                httpURLConnection.addRequestProperty("Authorization", header);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(forgotpw_user,"UTF-8")+"&"
                        +URLEncoder.encode("pin","UTF-8")+"="+URLEncoder.encode(forgotpw_pin,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(type.equals("updateuserinfo")){
            try {
                String toupdate = params[1];//username or password or pin
                current_username = params[2];//username of the user to be updated
                update_user_info = params[3];//new username or new password or new pin

                URL url = new URL(update_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                String header = "Basic " + new String(android.util.Base64.encode("cauldronuserCB:androidLife000".getBytes(), android.util.Base64.NO_WRAP));
                httpURLConnection.addRequestProperty("Authorization", header);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                if(toupdate == "username")
                {
                  String post_data = URLEncoder.encode("currentuser","UTF-8")+"="+URLEncoder.encode(current_username,"UTF-8")+"&"
                          +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(update_user_info,"UTF-8");
                    bufferedWriter.write(post_data);
                }
                else if(toupdate == "password")
                {
                    String post_data = URLEncoder.encode("currentuser","UTF-8")+"="+URLEncoder.encode(current_username,"UTF-8")+"&"
                            +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(update_user_info,"UTF-8");
                    bufferedWriter.write(post_data);
                }
                else if(toupdate == "pin")
                {
                    String post_data = URLEncoder.encode("currentuser","UTF-8")+"="+URLEncoder.encode(current_username,"UTF-8")+"&"
                            +URLEncoder.encode("pin","UTF-8")+"="+URLEncoder.encode(update_user_info,"UTF-8");
                    bufferedWriter.write(post_data);
                }
                else if(toupdate == "deleteuser")
                {
                    String post_data = URLEncoder.encode("deleteuser","UTF-8")+"="+URLEncoder.encode(current_username,"UTF-8");
                    bufferedWriter.write(post_data);
                }

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(type.equals("setfavorites")){
            try {
                username_setfavorites = params[1];//username to insert/update in "favorites" table
                favorite_recipe = params[2];//recipe name to insert as favorite for username above
                URL url = new URL(setfavorites_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                String header = "Basic " + new String(android.util.Base64.encode("cauldronuserCB:androidLife000".getBytes(), android.util.Base64.NO_WRAP));
                httpURLConnection.addRequestProperty("Authorization", header);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username_setfavorites,"UTF-8")+"&"
                        +URLEncoder.encode("favorite","UTF-8")+"="+URLEncoder.encode(favorite_recipe,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
       // alertDialog.setMessage(result);
       // alertDialog.show();

        if(result.contains("Connection failed:"))
        {
            MessageBox("SERVER_STATUS: Connection failed...");
        }
        else if(result.contains("Login Success!"))
        {
            String str = result.substring(50);//Connection successful!Login Success!Got Favorites!/No Favorites!!   ->> 50 characters there with Got Favorites! OR No Favorites!!
            String[] arrOfStr = str.split(",");//arrOfStr[0]=name, arrOfStr[1]=email, arrOfStr[2]=pin, arrOfStr[>=3]=favorites if str.contains("Got Favorites!") else arrOfStr.length is 3, then the for loop below fails
            UserDatabase users = new UserDatabase();
            ArrayList<String> favorites = new ArrayList<>();
            for(int i=3; i<arrOfStr.length;i++)
            {
                favorites.add(arrOfStr[i]);
            }
            users.addUser(new User(login_username,arrOfStr[0],arrOfStr[1], login_password,arrOfStr[2], favorites));
            int id = indexofUser(login_username,users);
            Intent intent_name = new Intent();
            intent_name.setClass(context.getApplicationContext(),HomeActivity.class);
            intent_name.putExtra("userdatabase", users);
            intent_name.putExtra("userindex",id);
            MessageBox("Login Success!\n"+"Welcome: <"+login_username+">");
            context.startActivity(intent_name);

        }
        else if(result.contains("Login Failed!"))
        {
            MessageBox("Login Failed: Account not found!");
        }
        else if(result.contains("Gotit Password"))
        {
            //MessageBox("Your password is: "+ result.substring(36));
            MessageBox("Password reset link was sent to your email:\n"+ result.substring(14)); //Gotit Password//->> 14 characters there
        }
        else if(result.contains("Failed Password!"))
        {
            MessageBox("Recovery Failed: Account not found!");
        }
        else if(result.contains("Registration Success!"))
        {
            UserDatabase users = new UserDatabase();
            users.addUser(new User(reg_username,reg_name,reg_email, reg_password,reg_pin, new ArrayList<String>()));
            Intent intent_name = new Intent();
            intent_name.setClass(context.getApplicationContext(),TitleActivity.class);
            intent_name.putExtra("databaseupdated",users);
            MessageBox("Registration Success!" + "\n" + "Login Now! Enjoy Cauldron!");
            context.startActivity(intent_name);
        }
        else if(result.contains("Registration Failed!"))
        {
            MessageBox("Registration Failed: Account not created!");
        }
        else if(result.contains("User Exists!"))
        {
            SignUpActivity obj = new SignUpActivity();
            obj.usernameView.setError("Username is required");
            MessageBox("Username already in use!");
        }
        else if(result.contains("Email Exists!"))
        {
            SignUpActivity obj = new SignUpActivity();
            obj.emailView.setError("E-mail is required");
            MessageBox("E-mail already in use!");
        }
        else if(result.contains("Update Username Success!"))
        {
            ProfileActivity pa = new ProfileActivity();
            pa.users.getUserList().get(pa.userIndex).setName(pa.newusername);
            Intent intent_name = new Intent();
            intent_name.setClass(context.getApplicationContext(),ProfileActivity.class);
            MessageBox("Username successfully changed!");
            context.startActivity(intent_name);
        }
        else if(result.contains("Delete User Failed!"))
        {
            MessageBox("Delete User Failed!: Account not deleted!");
        }
        else if(result.contains("Favorite Not Set!"))
        {
            MessageBox("Favorite recipe could not be added to database!");
        }
        else if(result.contains("Account Locked!"))
        {
            MessageBox("Login Failed: Account Lock Activated!");
        }
        else if(result.contains("Still Gone!"))
        {
            MessageBox("Account Locked! Please try again later.");
        }

        /*
        else if(result.contains("Got Favorites!"))
        {
            String str = result.substring(36);//Connection successful!Got Favorites!   ->> 36 characters there
            String[] arrOfStr = str.split(",");//Get all recipes names
            ArrayList<String> arr = new ArrayList<>();
            for(int i=0; i<arrOfStr.length;i++)
            {
                arr.add(arrOfStr[i]);
            }
            ProfileActivity pa = new ProfileActivity();
            pa.users.getUserList().get(pa.userIndex).setFavorites(arr);
            Intent getFavorites = new Intent(context.getApplicationContext(),MainActivity.class);
            int searchby = 5;
            getFavorites.putExtra("searchby",searchby);
            getFavorites.putExtra("userdatabase",pa.users);
            getFavorites.putExtra("userindex",pa.userIndex);

            if(!arr.isEmpty())
            {
                context.startActivity(getFavorites);
            }else{
                MessageBox("You have no favorite recipe yet!");
            }


             //alertDialog.setMessage(str);
             //alertDialog.show();
        }
        else if(result.contains("No Favorites!!"))
        {
            MessageBox("You have no favorite recipe yet!");
        }
*/
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public void MessageBox(String message)
    {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public int indexofUser(String key, UserDatabase users){
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