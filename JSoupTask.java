package com.example.euglohapp;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.w3c.dom.Document;

import java.io.IOException;
//Classe Non Utilisée
public class JSoupTask extends AsyncTask<Void, Void, Document> {
    @Override
    protected Document doInBackground(Void... voids) {
        // # Constants used in this example
        final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";
        final String LOGIN_FORM_URL = "https://github.com/login";
        final String USERNAME = "rami.aggoun@gmail.com";
        final String PASSWORD = "Ta";

// # Go to login page
        Connection.Response loginFormResponse = null;
        try {
            loginFormResponse = Jsoup.connect(LOGIN_FORM_URL)
                    .method(Connection.Method.GET)
                    .userAgent(USER_AGENT)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = null;
        try {
            //Element a = new Element"<title>AYOUB</title>";
            //loginFormResponse.parse().getElementsByTag("TITLE").add;
            title =  loginFormResponse.parse().getElementsByTag("TITLE").get(0).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(title);

        try {
            String loginField = loginFormResponse.parse().getElementById("login_field").toString(); //  .select("#login_field").first();
            //System.out.println("loginField : " + loginField);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            loginFormResponse.parse().getElementById("login_field").val(USERNAME);
            loginFormResponse.parse().getElementById("password").val(PASSWORD);
            System.out.println("login_fieldRami1 : "+loginFormResponse.parse().getElementById("login_field"));
            loginFormResponse.parse().getElementById("login_field").html("<input type=\"text\" name=\"login\" id=\"login_field\" value=\"rami.aggoun@gmail.com\" class=\"form-control input-block\" autocapitalize=\"none\" autocorrect=\"off\" autocomplete=\"username\">");
            //String bb = loginFormResponse.parse().getElementById("login_field").val().toString();
            //System.out.println("loginField2 : " + bb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //checkElement("Login Field", loginField);
        //loginField.val(USERNAME);

// # Fill the login form
// ## Find the form first...
        /**
        FormElement loginForm = null;
        try {
            loginForm = (FormElement)loginFormResponse.parse().select("div#login > form").first();
        } catch (IOException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        }
        //checkElement("Login Form", loginForm);

// ## ... then "type" the username ...
        Element loginField = loginForm.select("#login_field").first();
        checkElement("Login Field", loginField);
        loginField.val(USERNAME);

// ## ... and "type" the password
        Element passwordField = loginForm.select("#password").first();
        checkElement("Password Field", passwordField);
        passwordField.val(PASSWORD);

        **/
// # Now send the form for login
        Element loginForm = null;
        FormElement loginFF = null;
        try {
            loginForm = loginFormResponse.parse().getElementsByClass("btn btn-primary btn-block").get(0);
            loginFF = (FormElement) loginFormResponse.parse().getElementsByAttributeValue("action","/session").get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Connection.Response loginActionResponse = null;
        try {
            loginActionResponse = loginFF.submit()
                    .cookies(loginFormResponse.cookies())
                    .userAgent(USER_AGENT)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(loginFormResponse.parse().html());
            System.out.println("login_fieldRami2 : "+loginFormResponse.parse().getElementById("login_field"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void checkElement(String name, Element elem) {
        if (elem == null) {
            throw new RuntimeException("Unable to find " + name);
        }
    }
}
