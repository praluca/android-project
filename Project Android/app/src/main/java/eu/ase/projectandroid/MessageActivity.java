package eu.ase.projectandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Insert;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import eu.ase.projectandroid.database.DatabaseManager;
import eu.ase.projectandroid.database.model.Message;
import eu.ase.projectandroid.database.model.UserRegistration;
import eu.ase.projectandroid.database.service.MessageService;

public class MessageActivity extends AppCompatActivity {
    public static final int TEXT_REQUEST = 1;
    // private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private static final String TEXT_STATE = "current_text";
    EditText editText;
    Button button;
    String phoneNo;
    String phone;
    Intent intent;
    TextView textView;
    TextView contentMessage;
    Button button_choose;
    ArrayList<String> mess = new ArrayList<>();
    ArrayList<eu.ase.projectandroid.database.model.Message> messages=new ArrayList<>();

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    String value;
    SharedPreferences.Editor editor;
    UserRegistration userLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_message);
        editText = findViewById(R.id.mesasge_editText);
        button = findViewById(R.id.message_button_send);


        //imi afiseaza numele din listview ul din activitatea anterioara(Contacts) intr-un textView din aceasta activitate
        intent = getIntent();
        phoneNo = intent.getStringExtra(ContactsActivity.EXTRA_MESSAGE);
        textView = findViewById(R.id.textViewMessage);
        textView.setText(phoneNo);
        textView.setBackgroundColor(Color.parseColor("#4caf50"));
        contentMessage = findViewById(R.id.textView2_contentMessage);
        contentMessage.setMovementMethod(new ScrollingMovementMethod());

        //imi afiseaza mesajul selectat din DeafaultMessageActivity
        button_choose = findViewById(R.id.message_btn_choose);
        Bundle bundle = getIntent().getExtras();
        userLogged=bundle.getParcelable(MainActivity.KEY);
        List<Long> list=new ArrayList<>();
        list.add(userLogged.getId());
        Log.d("useri", list.toString());

//        String mess = intent.getStringExtra(DefaultMessageActivity.MESSAGE_DEFAULT);
//        editText.setText(mess);


        if (savedInstanceState != null) {
            contentMessage.setText(savedInstanceState.getString(TEXT_STATE));
        }

        //imi salveaza continutul textviewului si dupa ce apas back button
        sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        value = sharedpreferences.getString(phoneNo, "");
        if(value!="") {
            contentMessage.setText(value);
            contentMessage.setGravity(Gravity.RIGHT);
        }

       // getAllMessages();
        //getMessages();
        //deleteMessages(3);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                String cont = data.getStringExtra(DefaultMessageActivity.MESSAGE_DEFAULT);
                editText.setText(cont);
            }
        }
    }

    public void sendMessage(View view) {
        //trimite mesajele intr-un textView
        mess.add(editText.getText().toString());
        contentMessage.setText(value+"");
        for (int i = 0; i < mess.size(); i++) {
            contentMessage.append(mess.get(i) + "   " + "\n");

        }
        editText.getText().clear();
        contentMessage.setGravity(Gravity.RIGHT);
        Toast.makeText(getApplicationContext(), R.string.message_sent, Toast.LENGTH_SHORT).show();


        editor = sharedpreferences.edit();
        editor.putString(phoneNo, contentMessage.getText().toString());
        editor.apply();
        Message message=new Message(mess.toString(),mess.size(),phoneNo);
        message.setUserId(userLogged.getId());
        new InsertMessage().execute(message);


    }

    private class InsertMessage extends AsyncTask<Message,Void,Long>{

        @Override
        protected Long doInBackground(Message... messages) {
            long maxId= DatabaseManager.getInstance(getApplicationContext()).getMessageDao().getMaxId();
            messages[0].setId(maxId+1);

            long id=DatabaseManager.getInstance(getApplicationContext()).getMessageDao().insertMessage(messages[0]);
            return  id;
        }
    }
//    private Message createMessage(){
//       String content=mess.toString();
//        int length=1;
//        String recipient=textView.getText().toString();
//        return new Message(content,length,recipient);
//    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_STATE, contentMessage.getText().toString());
    }

    public void chooseMessage(View view) {
        Intent intent = new Intent(getApplicationContext(), DefaultMessageActivity.class);
        startActivityForResult(intent, TEXT_REQUEST);
    }


//  private void getAllMessages(){
//        new MessageService.GetAllMessage(getApplicationContext()){
//            @Override
//            protected void onPostExecute(List<Message> results) {
//                if(results!=null){
//                    messages.clear();
//                    messages.addAll(results);
//                    //Toast.makeText(getApplicationContext(),messages.toString(),Toast.LENGTH_LONG).show();
//                }
//            }
//        }.execute();
//  }

//    private void getMessages(){
//        new MessageService.GetMessage(getApplicationContext()){
//            @Override
//            protected void onPostExecute(List<Message> results) {
//                if(results!=null){
//                    messages.clear();
//                    messages.addAll(results);
//                    //Toast.makeText(getApplicationContext(),messages.toString(),Toast.LENGTH_LONG).show();
//                }
//            }
//        }.execute();
//    }

//    private void deleteMessages(final Integer length){
//        new MessageService.DeleteAllMessages(getApplicationContext()){
//            @Override
//            protected void onPostExecute(Integer result) {
//                if(result==1){
//                    messages.remove(createMessage().getLength());
//                }
//            }
//        }.execute(createMessage());
//    }


}

