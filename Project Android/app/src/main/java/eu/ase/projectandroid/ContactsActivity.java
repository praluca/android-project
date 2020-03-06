package eu.ase.projectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import eu.ase.projectandroid.database.DatabaseManager;
import eu.ase.projectandroid.database.model.UserRegistration;
import eu.ase.projectandroid.database.service.UserRegistrationService;
//import eu.ase.projectandroid.database.service.UserService;
import eu.ase.projectandroid.util.CustomAdapter;
import eu.ase.projectandroid.util.Persoana;

public class ContactsActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "eu.ase.projectandroid.MESSAGE";
    public static final String USER_KEY_CONTACTS="userKeyContacts";
    ListView listView;
    Cursor cursor;
    ArrayList<UserRegistration> userRegistrations=new ArrayList<>();

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    private UserRegistration userRLogged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        //au fost prezentate la faza anaterioara, inca functioneaza doar ca au fost inlocuite cu elemente din baza de date

        //intent = getIntent();
//        String message = intent.getStringExtra(MainActivity.MESSAGE);
//        if (message != null) {
//            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//        }
//        else {
////            Persoana persoana = intent.getParcelableExtra(RegistrationActivity.MESS);
////            if (persoana != null) {
////                Toast.makeText(getApplicationContext(),
////                        persoana.toString(),
////                        Toast.LENGTH_SHORT).show();
//            }
//        }

        //Toast.makeText(getApplicationContext(), "Te-ai conectat cu userul: "+userRLogged.getEmail(),Toast.LENGTH_LONG).show();
        final Bundle bundle=getIntent().getExtras();
        userRLogged=bundle.getParcelable(MainActivity.KEY);
        List<Long> list=new ArrayList<>();
        list.add(userRLogged.getId());
        Log.d("test", userRLogged.getEmail());

        listView = findViewById(R.id.list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        }else {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            startManagingCursor(cursor);
            String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone._ID};
            int[] to = {android.R.id.text1, android.R.id.text2};
            int a[] = new int[]{R.id.tv_cursoradapter, R.id.tv_cursoradapter2};
            CustomAdapter adapter = new CustomAdapter(this, R.layout.customadapter, cursor, new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER}, a);
            listView.setAdapter(adapter);
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    String message = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                   intent.putExtra(EXTRA_MESSAGE, message);
                    Bundle bundle1=new Bundle();
                    bundle1.putParcelable(MainActivity.KEY,userRLogged);
                    intent.putExtras(bundle);
                    startActivity(intent);
            }
        });

        new AsyncTask<Void,Void,UserRegistration>(){


            @Override
            protected UserRegistration doInBackground(Void... voids) {

                return DatabaseManager.getInstance(getApplicationContext()).getUserRegistrationDao().getUserById(userRLogged.getId());
            }

            @Override
            protected void onPostExecute(UserRegistration result) {
                if(result!=null){
                    Toast.makeText(getApplicationContext(),"Te-ai conectat cu userul: "+result.getEmail()+" si parola "+result.getParola(),
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Eroare",Toast.LENGTH_LONG).show();
                }

            }
        }.execute();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                startManagingCursor(cursor);
                String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone._ID};
                int[] to = {android.R.id.text1, android.R.id.text2};
                listView = findViewById(R.id.list);
                int a[] = new int[]{R.id.tv_cursoradapter, R.id.tv_cursoradapter2};
                CustomAdapter adapter = new CustomAdapter(this, R.layout.customadapter, cursor, new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER}, a);
                listView.setAdapter(adapter);

            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_option:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.info_option:
                Intent intent1 = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(intent1);
                break;
            case R.id.sent_messages_option:
                Intent intent2 = new Intent(getApplicationContext(), SentMessagesActivity.class);
                startActivity(intent2);
                break;
            case R.id.istoric_mesaje_option:
                Intent intent3=new Intent(getApplicationContext(),IstoricMesajeActivity.class);
                startActivity(intent3);
                break;
            case R.id.grafic_option:
                Intent intent4=new Intent(getApplicationContext(),GraficActivity.class);
                startActivity(intent4);
                break;
            case R.id.maps_option:
                Intent intent5=new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(intent5);
                break;
            case R.id.settings_option:
                Intent intent6=new Intent(getApplicationContext(),SettingsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable(USER_KEY_CONTACTS,userRLogged);
                intent6.putExtras(bundle);
                startActivity(intent6);
                break;
            case R.id.users_option:
                Intent intent7=new Intent(getApplicationContext(),AdminActivity.class);
                startActivity(intent7);
                break;
            default:
                break;
        }
        return true;
    }
}
