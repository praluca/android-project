package eu.ase.projectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import eu.ase.projectandroid.database.DatabaseManager;
import eu.ase.projectandroid.database.model.UserRegistration;
import eu.ase.projectandroid.database.service.UserRegistrationService;
import eu.ase.projectandroid.util.Persoana;

public class RegistrationActivity extends AppCompatActivity {

    TextInputEditText firstname;
    TextInputEditText lastname;
    TextInputEditText email;
    TextInputEditText password;
    TextInputEditText confirmPassword;
    Spinner country;
    RadioGroup rg_sex;
    Button btn;
    TextInputEditText phone;
    ScrollView scrollView;
    private ArrayList<Persoana> personList=new ArrayList<>();
    private ArrayList<UserRegistration> usersList=new ArrayList<>();
    public static final String MESS="eu.ase.projectAndroid.MESSAGE";
    ArrayList<String> userRListCountry=new ArrayList<>();

    DatabaseReference refff;
    UserR userR;

    UserRegistration userLogged;

    public static final String USERNAME_KEY="usernameKey";
    public static final String PASSWORD_KEY="passwordKey";
    public static final String PHONE_KEY="phoneKey";
    public static final String USER_LOGGED_KEY="userKeyRegistration";

    ArrayList<String> usernames=new ArrayList<>();
    ArrayList<String> passwords=new ArrayList<>();
    ArrayList<String > phones=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initComponets();

        final Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            userLogged=bundle.getParcelable(SettingsActivity.USER_LOGGED_KEY);
            setUserData();
        }else{
            userLogged=null;
        }

        userR=new UserR();
        refff= FirebaseDatabase.getInstance().getReference().child("UserR");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()  ) {
                    //inserare users in firebase
                    String nume=lastname.getText().toString().trim();
                    String prenume=firstname.getText().toString().trim();
                    String mail=email.getText().toString().trim();
                    String parola=password.getText().toString().trim();
                    String confirmareParola=confirmPassword.getText().toString().trim();
                    String tara=country.getSelectedItem().toString();
                    RadioButton getItem=findViewById(rg_sex.getCheckedRadioButtonId());
                    String sex=getItem.getText().toString();
                    String numar=phone.getText().toString().trim();
                    userR.setFirstname(prenume);
                    userR.setLastname(nume);
                    userR.setUsername(mail);
                    userR.setPassword(parola);
                    userR.setConfirmPassword(confirmareParola);
                    userR.setCountry(tara);
                    userR.setSex(sex);
                    userR.setPhone(numar);
                    refff.push().setValue(userR);

                    if(userLogged==null) {
                        if(!usernames.contains(email.getText().toString()) && !phones.contains(phone.getText().toString())){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        //Persoana persoana=createPerson();
                        //intent.putExtra(MESS,persoana);
                        intent.putExtra(USERNAME_KEY, email.getText().toString());
                        intent.putExtra(PASSWORD_KEY, password.getText().toString());
                        intent.putExtra(PHONE_KEY, phone.getText().toString());
                        // startActivity(intent);
                        //inseram userul in baza de date
                        new InsertUserRegistration().execute(createUserRegistration());
                        setResult(RESULT_OK, intent);
                        finish();}
                        else{
                            Toast.makeText(getApplicationContext(),"Username ul exista deja",Toast.LENGTH_LONG).show();
                        }
                    }
                   else {

                        long id=userLogged.getId();
                        userLogged=createUserRegistration();
                        userLogged.setId(id);

                        Intent intent2=new Intent(getApplicationContext(),SettingsActivity.class);
                        Bundle bundle1=new Bundle();
                        bundle1.putParcelable(USER_LOGGED_KEY,userLogged);
                        intent2.putExtras(bundle1);
                        //startActivity(intent2);

                        new UpdateUser().execute(userLogged);
                        setResult(RESULT_OK, intent2);
                        finish();
                    }

                }
            }
        });

        //ReadFromFireBase();
        //ReadCountryFromFireBase();
        getUsernameFromDatabase();
        getPhonesFromDatabase();
    }
    private void getUsernameFromDatabase(){
        new UserRegistrationService.GetUsernameI(getApplicationContext()){
            @Override
            protected void onPostExecute(List<String> results) {
                if(results!=null){
                    usernames.clear();
                    usernames.addAll(results);
                }
            }
        }.execute();
    }
    private void getPasswordFromDatabase(){
        new UserRegistrationService.GetPasswordI(getApplicationContext()){
            @Override
            protected void onPostExecute(List<String> results) {
                if(results!=null)
                {
                    passwords.clear();
                    passwords.addAll(results);
                }

            }
        }.execute();
    }

    private void getPhonesFromDatabase(){
        new UserRegistrationService.GetPhoneI(getApplicationContext()){
            @Override
            protected void onPostExecute(List<String> results) {
                if(results!=null){
                    phones.clear();
                    phones.addAll(results);
                }
            }
        }.execute();
    }
    private void setUserData(){
        firstname.setText(userLogged.getPrenume());
        lastname.setText(userLogged.getNume());
        email.setText(userLogged.getEmail());
        password.setText(userLogged.getParola());
        password.setText(userLogged.getConfirmareParola());
        phone.setText(userLogged.getNrTelefon());
    }

    private class InsertUserRegistration extends AsyncTask<UserRegistration,Void,Long>{

        @Override
        protected Long doInBackground(UserRegistration... userRegistrations) {
            long maxId= DatabaseManager.getInstance(getApplicationContext()).getUserRegistrationDao().getMaxId();
            userRegistrations[0].setId(maxId+1);
            return DatabaseManager.getInstance(getApplicationContext()).getUserRegistrationDao().insert(userRegistrations[0]);
        }
    }
    private class UpdateUser extends AsyncTask<UserRegistration, Void, Integer> {

        @Override
        protected Integer doInBackground(UserRegistration... users) {
            return DatabaseManager.getInstance(getApplicationContext()).getUserRegistrationDao().update(users[0]);
        }
    }



    public UserRegistration createUserRegistration(){
        String nume=lastname.getText().toString();
        String prenume=firstname.getText().toString();
        String mail=email.getText().toString();
        String parola=password.getText().toString();
        String confirmareParola=confirmPassword.getText().toString();
        String tara=country.getSelectedItem().toString();
        RadioButton getItem=findViewById(rg_sex.getCheckedRadioButtonId());
        String sex=getItem.getText().toString();
        String numar=phone.getText().toString();
        return new UserRegistration(prenume,nume,mail,parola,confirmareParola,tara,sex,numar);

    }
    private void initComponets(){
        firstname=findViewById(R.id.registration_et_firstname);
        lastname=findViewById(R.id.registration_et_lastname);
        email=findViewById(R.id.registration_et_email);
        password=findViewById(R.id.registration_et_password);
        confirmPassword=findViewById(R.id.registration_et_confirm_password);
        country=findViewById(R.id.registration_spn_country);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.spinner_items,android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(adapter);
        rg_sex=findViewById(R.id.registration_rg_sex);
        btn=findViewById(R.id.registration_btn_singin);
        phone=findViewById(R.id.registration_et_phone);
        scrollView=findViewById(R.id.scroll_registration);

    }

    private Boolean validate(){
        Boolean validate=true;
        if(firstname.getText().toString()==null || firstname.getText().toString().isEmpty())
        {
            validate=false;
            Toast.makeText(getApplicationContext(), R.string.registration_firstaname_error,Toast.LENGTH_SHORT).show();
        } else if(lastname.getText().toString()==null || lastname.getText().toString().isEmpty()){
            validate=false;
            Toast.makeText(getApplicationContext(), R.string.registration_lastname_error,Toast.LENGTH_SHORT).show();
        } else if(email.getText().toString()==null || email.getText().toString().isEmpty()){
            validate=false;
            Toast.makeText(getApplicationContext(), R.string.registration_email_error,Toast.LENGTH_SHORT).show();
        }else if(password.getText().toString()==null || password.getText().toString().isEmpty())
        {
            validate=false;
            Toast.makeText(getApplicationContext(), R.string.registration_password_error, Toast.LENGTH_SHORT).show();
        }else if(confirmPassword.getText().toString()==null || confirmPassword.getText().toString().isEmpty()
        || !(confirmPassword.getText().toString().equals(password.getText().toString()))){
            validate=false;
            Toast.makeText(getApplicationContext(), R.string.registration_confirm_password_error,Toast.LENGTH_SHORT).show();
        }else if(phone.getText().toString()==null || phone.getText().toString().isEmpty()){
            validate=false;
            Toast.makeText(getApplicationContext(), R.string.registration_phone_error, Toast.LENGTH_SHORT).show();
        }
        return validate;
    }
    private Persoana createPerson()
    {
        String prenume=firstname.getText().toString();
        String nume=lastname.getText().toString();
        String mail=email.getText().toString();
        String parola=password.getText().toString();
        String confirmaParola=password.getText().toString();
        String tara=country.getSelectedItem().toString();
        RadioButton getItem=findViewById(rg_sex.getCheckedRadioButtonId());
        String sex=getItem.getText().toString();
        int numar=Integer.parseInt(phone.getText().toString());
        return new Persoana(prenume,nume,mail,parola,confirmaParola,tara,sex,numar);
    }



    //afisare useri din firebase
    private void ReadFromFireBase(){
        refff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<UserR> userRList=new ArrayList<>();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    UserR userR=data.getValue(UserR.class);
                    if(userR!=null){
                        userRList.add(userR);
                    }
                }
                Log.d("useri",userRList.toString()+"\n");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    //afisare tari din firebase
    private void ReadCountryFromFireBase(){
        refff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data:dataSnapshot.getChildren()){
                    UserR userR=data.getValue(UserR.class);
                    if(userR!=null){
                        userRListCountry.add(userR.getCountry());
                    }
                }
                Toast.makeText(getApplicationContext(),userRListCountry.toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }
}
