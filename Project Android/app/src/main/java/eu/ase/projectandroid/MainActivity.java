package eu.ase.projectandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import eu.ase.projectandroid.database.DatabaseManager;
import eu.ase.projectandroid.database.model.UserRegistration;
import eu.ase.projectandroid.database.service.UserRegistrationService;
//import eu.ase.projectandroid.database.service.UserService;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE=200;
    public static final int REQUEST_CODE_USER=102;
    public static final String MESSAGE="eu.ase.projectandroid.MESSAGE";
    public static final String KEY="userRegKey";
    Button btn;
    TextInputEditText username;
    TextInputEditText password;
    TextInputEditText phone;
    ArrayList<String> usernames=new ArrayList<>();
    ArrayList<String> passwords=new ArrayList<>();
    ArrayList<String > phones=new ArrayList<>();

    public static final String SETTINGS_KEY="MainActivity.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btn=findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    UserRegistration userLogin = new UserRegistration("", "", username.getText().toString(),
                            password.getText().toString(), "", "", "", phone.getText().toString());


                    new AsyncTask<UserRegistration, Void, UserRegistration>() {

                        @Override
                        protected UserRegistration doInBackground(UserRegistration... userRegistrations) {
                            return DatabaseManager.getInstance(getApplicationContext()).
                                    getUserRegistrationDao().getUserByCredentials(userRegistrations[0].getEmail(),
                                    userRegistrations[0].getParola(), userRegistrations[0].getNrTelefon());
                        }

                        @Override
                        protected void onPostExecute(UserRegistration userRegistration) {
                            if (userRegistration != null) {
                                Bundle bundle = new Bundle();
                                bundle.putParcelable(KEY, userRegistration);

                                Intent intent = new Intent(getApplicationContext(), ContactsActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.main_toast_message_error, Toast.LENGTH_LONG).show();
                            }
                        }
                    }.execute(userLogin);

                }
            }
        });
        initComponents();

    }

    public void initComponents(){
        username=findViewById(R.id.login_et_username);
        password=findViewById(R.id.login_et_password);
        phone=findViewById(R.id.login_et_phone);

    }

    public void addRegistration(View view) {
        Intent intent=new Intent(getApplicationContext(),RegistrationActivity.class);
        startActivityForResult(intent,REQUEST_CODE_USER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==REQUEST_CODE_USER){
            String usernameS=data.getStringExtra(RegistrationActivity.USERNAME_KEY);
            String passwordS=data.getStringExtra(RegistrationActivity.PASSWORD_KEY);
            String phoneS=data.getStringExtra(RegistrationActivity.PHONE_KEY);
            username.setText(usernameS);
            password.setText(passwordS);
            phone.setText(phoneS);

        }
    }


    public  Boolean validate(){
        Boolean valid=true;
        if(username.getText().toString()==null || username.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), R.string.main_activity_username_error,Toast.LENGTH_SHORT).show();
            valid=false;
        }
        if(password.getText().toString()==null || password.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), R.string.main_activity_password_error,Toast.LENGTH_SHORT).show();
            valid=false;
        }
        if(phone.getText().toString()==null || phone.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), R.string.main_activity_phone_error,Toast.LENGTH_SHORT).show();
            valid=false;
        }
        return valid;
    }


}
