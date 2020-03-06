package eu.ase.projectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import eu.ase.projectandroid.database.DatabaseManager;
import eu.ase.projectandroid.database.dao.MessageDao;
import eu.ase.projectandroid.database.model.Message;
import eu.ase.projectandroid.database.model.UserRegistration;
import eu.ase.projectandroid.database.service.MessageService;


public class IstoricMesajeActivity extends AppCompatActivity {
    private  static final String FILENAME="text.txt";
    ArrayList<Message> messages=new ArrayList<>();
    ArrayList<Message> messages5=new ArrayList<>();
    TextView mesaje;
    Button upload;
    Button uploadFrom;
    EditText name;
    Button btnSave;
    List<String>contents=new ArrayList<>();
    ArrayList<Message> messagesList=new ArrayList<>();
    UserRegistration userRegistrationLogged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istoric_mesaje);
        mesaje=findViewById(R.id.istoric_mesaje_tv);
        upload=findViewById(R.id.istoric_mesaje_btn);
        uploadFrom=findViewById(R.id.istoric_mesaje_btn_name);
        name=findViewById(R.id.editText4);
        btnSave=findViewById(R.id.istoric_mesaje_btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToFile(mesaje.getText().toString());
                String textFromFileString =  readFromFile();

                if ( mesaje.getText().toString().equals(textFromFileString) )
                    Toast.makeText(getApplicationContext(), R.string.istoric_mesaje_toast_message, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), R.string.istoric_mesaje_toast_error_message, Toast.LENGTH_SHORT).show();
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllMessages();
            }
        });

        uploadFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new  AsyncTask<Void,Void,List<String>>(){
                    String name1=name.getText().toString().trim();
                    @Override
                    protected List<String> doInBackground(Void... voids) {
                        return DatabaseManager.getInstance(getApplicationContext()).getMessageDao().getMessagesR(name1);
                    }

                    @Override
                    protected void onPostExecute(List<String> results) {
                        if(results!=null){
                            contents.clear();
                            contents.addAll(results);
                            mesaje.setText("");
                            mesaje.setText(contents.toString());
                        }
                    }
                }.execute();
            }
        });

    }

    private void getAllMessages(){
        new MessageService.GetAllMessage(getApplicationContext()){
            @Override
            protected void onPostExecute(List<Message> results) {
                if(results!=null){
                    messages.clear();
                    messages.addAll(results);
                    Toast.makeText(getApplicationContext(),messages.toString(),Toast.LENGTH_LONG).show();
                    mesaje.setText(messages.toString());
                }
            }
        }.execute();
    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(FILENAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("TAG", "File write failed: " + e.toString());
        }

    }

    private String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = openFileInput(FILENAME);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("TAG", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("TAG", "Can not read file: " + e.toString());
        }

        return ret;
    }

}
