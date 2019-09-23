package fiu.team5cen4010.cauldron;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String username;
    private String fullname;
    private String email;
    private String password;
    private String pin;
    private ArrayList<String> favorites;


    public User(String username, String fullname, String email, String password, String pin, ArrayList<String> favorites){
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.pin = pin;
        this.favorites =  favorites;
    }

    public ArrayList<String> getFavorites() { return favorites; }

    public String getFullname() { return fullname; }

    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
