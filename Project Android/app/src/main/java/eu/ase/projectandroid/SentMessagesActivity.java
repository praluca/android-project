package eu.ase.projectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import eu.ase.projectandroid.network.HttpManager;
import eu.ase.projectandroid.network.HttpResponse;
import eu.ase.projectandroid.network.JsonParser;
import eu.ase.projectandroid.network.Message;

public class SentMessagesActivity extends AppCompatActivity {

    TextView textView;
    private static final String URL="https://api.myjson.com/bins/kl8d2";
    Button btnSent;
    Button btnReceived;
    private HttpResponse httpResponse;
    private ListView lvResponse;
    private List<Message> selectedResponse = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_messages);
        textView=findViewById(R.id.sent_messages_tv);

        new HttpManager(){
            @Override
            protected void onPostExecute(String s) {
                httpResponse= JsonParser.parseJson(s);
              //  Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                if(httpResponse!=null){
                    Toast.makeText(getApplicationContext(),httpResponse.toString(),Toast.LENGTH_LONG).show();
                }
                textView.setText(httpResponse.toString());
            }
        }.execute(URL);


        btnSent=findViewById(R.id.sentMessages_btn_sent_message);
        btnReceived=findViewById(R.id.sentMessages_btn_received);
        lvResponse=findViewById(R.id.sentMessages_lv);


        ArrayAdapter<Message> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, selectedResponse);
        lvResponse.setAdapter(adapter);

        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(httpResponse!=null && httpResponse.getMesaj2()!=null){
                   selectResponse(httpResponse.getMesaj2());
                }
            }
        });

        btnReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(httpResponse!=null && httpResponse.getMesaj1()!=null){
                    selectResponse(httpResponse.getMesaj1());
                }
            }
        });
    }
    private void selectResponse(List<Message> list) {
        selectedResponse.clear();
        selectedResponse.addAll(list);
        ArrayAdapter<Message>adapter = (ArrayAdapter<Message>)
                lvResponse.getAdapter();
        adapter.notifyDataSetChanged();
    }
}
