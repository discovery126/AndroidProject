package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.database.MainDb;
import com.example.myapplication.database.User;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    public static final String AUTHORIZATION = "AUTHORIZATION";
    public static final String LOGIN = "LOGIN";
    public static final String ID = "ID";
    private EditText LoginText;
    private EditText PasswordText;
    private TextView registrationText;
    private Button EnterButton;
    private HashMap<String,String> users = new HashMap<>();
    private int id = 1;
    private MainDb db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginText = findViewById(R.id.editTextTextLogin);
        PasswordText = findViewById(R.id.editTextTextPassword);
        EnterButton = findViewById(R.id.button);
        registrationText = findViewById(R.id.registrationText);

        settings = getSharedPreferences(AUTHORIZATION,MODE_PRIVATE);
        editor = settings.edit();

        int checkedAuthorization = settings.getInt(ID,0);

        if (checkedAuthorization!=0) {
            Intent intent = new Intent(this, BottomActivity.class);
            startActivity(intent);
        }

        db = Room.databaseBuilder(getApplicationContext(), MainDb.class, "RecipeBD").build();

        String[] logins = getResources().getStringArray(R.array.logins);
        String[] passwords = getResources().getStringArray(R.array.passwords);
        for (int i = 0; i < 5; i++) {
            User newUser = new User(logins[i], passwords[i]);
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    User user = db.getUserDao().getUser(newUser.getLogin());
                    if (user == null) {
                        db.getUserDao().addUser(newUser);
                    }
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        registrationText.setOnClickListener(view -> {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_activity_id1, new RegistrationFragment())
                    .addToBackStack(MainActivity.class.getSimpleName())
                    .commit();
        });

    }

    public void EnterClickButton(View view) throws InterruptedException {
        String currentLogin = LoginText.getText().toString();
        String currentPass = PasswordText.getText().toString();

        Thread thread = new Thread(() -> {
            List<User> usersBd = db.getUserDao().getAllUser();
            for (User currentUser : usersBd) {
                users.put(currentUser.getLogin(), currentUser.getPassword());
                System.out.println(currentUser.getId() + "   " + currentUser.getLogin() + "  " + currentUser.getPassword() + '\n');
            }
            id = db.getUserDao().getUserId(currentLogin);
        });
        thread.start();
        thread.join();

        LoginText.setTextColor(getResources().getColor(R.color.white, null));
        PasswordText.setTextColor(getResources().getColor(R.color.white, null));
        boolean GoodEnter = false;

        if (users.containsKey(currentLogin)) {
            String RightPass = users.get(currentLogin);

            if (Objects.equals(RightPass, currentPass)) {
                GoodEnter = true;

                editor.putString(LOGIN,currentLogin);
                editor.putInt(ID,id);
                editor.apply();
                Intent intent = new Intent(this, BottomActivity.class);
                startActivity(intent);

            }
        }
        if (!GoodEnter) {
            LoginText.setTextColor(getResources().getColor(R.color.red, null));
            PasswordText.setTextColor(getResources().getColor(R.color.red, null));
        }

    }
}