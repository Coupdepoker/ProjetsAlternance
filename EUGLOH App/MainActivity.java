package com.example.euglohapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;
//Classe non utilisée
public class MainActivity extends AppCompatActivity  {

    private TextView mGreetingText;
    private EditText mNameInput;
    private EditText mPasswordInput;
    private Button mPlayButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGreetingText = (TextView) findViewById(R.id.Pres);
        mNameInput = (EditText) findViewById(R.id.editTextTextPersonName);
        mPasswordInput = (EditText) findViewById(R.id.bPassword);
        mPlayButton = (Button) findViewById(R.id.button1);

        mGreetingText.setMovementMethod(new ScrollingMovementMethod());
        mPlayButton.setEnabled(false);
        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPlayButton.setEnabled(charSequence.toString().length()!=0);


        }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int a =3;
                //System.out.println("a : "+a);
                Intent gameActivityIntent = new Intent(MainActivity.this,WebViewExample.class);
                startActivity(gameActivityIntent);
                //new JSoupTask().execute();


                // Let’s open a web page
                //driver.get("http://www.google.com");
                //System.setProperty("webdriver.firefox.driver", "path of driver");
                /**
                WebDriver driver=new FirefoxDriver();
                driver.get("http://www.google.com");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                driver.quit();
                **/






                final String username = mNameInput.getText().toString();
                final String password = mPasswordInput.getText().toString();
                /**
                final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";
                final String LOGIN_FORM_URL = "https://github.com/login";
                final String USERNAME = "yourUsername";
                final String PASSWORD = "yourPassword";


                try {
                    Connection.Response loginFormResponse = Jsoup.connect(LOGIN_FORM_URL)
                            .method(Connection.Method.GET)
                            .userAgent(USER_AGENT)
                            .execute();
                    Intent gameActivityIntent = new Intent(MainActivity.this,MainActivity2.class);
                    startActivity(gameActivityIntent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                **/

                /**
                try {
                    Document doc = (Document) Jsoup.connect("https://sso.u-psud.fr/cas/login").get();
                    Element title = doc.getElementById("title");
                    mGreetingText.setText(title.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                **/
                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                           // Writer file = new FileWriter("C:\\Users\\ramia\\Desktop\\Euglo.txt");
                           // file.write("data 1");
                            //file.close();
                            //mGreetingText.setText(response);
                            //System.out.println(response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                mGreetingText.setText("ok");
                                /**
                                String name = jsonResponse.getString("name");
                                int age = jsonResponse.getInt("age");

                                Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("age", age);
                                intent.putExtra("username", username);
                                LoginActivity.this.startActivity(intent);
                                 **/
                            } else {
                                mGreetingText.setText("fail");
                                /**
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                                 **/
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                //RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                //queue.add(loginRequest);

            }
        });


    }

    public void mView(){
        Intent gameActivityIntent = new Intent(MainActivity.this,MainActivity2.class);
        startActivity(gameActivityIntent);
    }


}