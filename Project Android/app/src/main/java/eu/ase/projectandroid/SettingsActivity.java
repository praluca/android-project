package eu.ase.projectandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import eu.ase.projectandroid.database.DatabaseManager;
import eu.ase.projectandroid.database.model.UserRegistration;
import eu.ase.projectandroid.database.service.UserRegistrationService;

public class SettingsActivity extends AppCompatActivity {

    public static final String USER_LOGGED_KEY="settingsKey";
    Button btn_update;
    Button btn_delete;
    Button btn_change;
    UserRegistration userLoggedSettings;
    List<UserRegistration> userRegistrationsList=new ArrayList<>();
    Boolean flag=false;
    public static final int USER_LOGGED_CODE=123;
    TextView username_content;
    TextView phone_content;
    ImageView imgView;
    public  static final  int RESULT_LOAD_IMG=112;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btn_update = findViewById(R.id.settings_btn_update);
        btn_delete = findViewById(R.id.settings_btn_delete);
        btn_change = findViewById(R.id.settings_btn_change_picture);
        username_content=findViewById(R.id.settings_tv_username_content);
        phone_content=findViewById(R.id.settings_tv_phone_content);
        imgView=findViewById(R.id.imageView3);

        imgView.setTag("https://cdn3.iconfinder.com/data/icons/business-1-42/100/Untitled-2-14-512.png");


        final Bundle bundle = getIntent().getExtras();
        userLoggedSettings = bundle.getParcelable(ContactsActivity.USER_KEY_CONTACTS);
        username_content.setText(userLoggedSettings.getEmail());
        phone_content.setText(userLoggedSettings.getNrTelefon());


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=true;
                Intent intent=new Intent(getApplicationContext(),RegistrationActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putParcelable(USER_LOGGED_KEY,userLoggedSettings);
                intent.putExtras(bundle1);
                startActivityForResult(intent,USER_LOGGED_CODE);
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RegistrationActivity.class);
                //remove from database
                new AsyncTask<Void,Void,Integer>(){

                    @Override
                    protected Integer doInBackground(Void... voids) {
                       return DatabaseManager.getInstance(getApplicationContext()).getUserRegistrationDao().delete(userLoggedSettings);
                    }
                }.execute();
                startActivity(intent);
            }
        });
        getUsersRegistrationFromDatabase();

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == USER_LOGGED_CODE) {
            final Bundle bundle1 = data.getExtras();
            userLoggedSettings = bundle1.getParcelable(RegistrationActivity.USER_LOGGED_KEY);
            username_content.setText(userLoggedSettings.getEmail());
            phone_content.setText(userLoggedSettings.getNrTelefon());

        }else

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imgView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), R.string.settings_toast_error, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getUsersRegistrationFromDatabase(){
        new UserRegistrationService.GetAllUsers(getApplicationContext()){
            @Override
            protected void onPostExecute(List<UserRegistration> results) {
                if(results!=null){
                    userRegistrationsList.clear();
                    userRegistrationsList.addAll(results);
                    Toast.makeText(getApplicationContext(),userRegistrationsList.toString(),Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

}
