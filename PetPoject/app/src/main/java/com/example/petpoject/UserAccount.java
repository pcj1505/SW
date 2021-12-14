package com.example.petpoject;


/*

UserInfo Class

*/

public class UserAccount
{

    public String get_idToken() {
        return _idToken;
    }

    public void set_idToken(String _idToken) {
        this._idToken = _idToken;
    }

    private String _idToken;        // Firebase Unique id // primary key
    private String _emailId;        // email Id
    private String _password;       // pw
    private String _pet;
    private String _title;
    private String _contents;



    public String get_emailId() {
        return _emailId;
    }

    public void set_emailId(String _emailId) {
        this._emailId = _emailId;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_pet() {
        return _pet;
    }

    public void set_pet(String pet) {
        this._pet = pet;
    }

    public String get_title(){return _title;}

    public void set_title(String title){this._title = title;}

    public String get_contents(){return _contents;}

    public void set_contents(String contents){this._contents = contents;}




    public UserAccount()
    {

    }


}
