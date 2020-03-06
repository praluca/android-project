package eu.ase.projectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import eu.ase.projectandroid.util.MesageAdapter;
import eu.ase.projectandroid.util.Mesaj;

public class DefaultMessageActivity extends AppCompatActivity {

    public static final String MESSAGE_DEFAULT="defaultMessageActivity";
    private ListView listView;
    private MesageAdapter mesageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_message);

        listView=findViewById(R.id.messages_list);
        final ArrayList<Mesaj> mesaje=new ArrayList<>();

        mesaje.add(new Mesaj(R.drawable.ic_message_black_24dp,getString(R.string.def_message_hello_title),getString(R.string.def_message_hello_content)));
        mesaje.add(new Mesaj(R.drawable.ic_message_black_24dp,getString(R.string.def_message_title_bye),getString(R.string.def_message_content_bye)));
        mesaje.add(new Mesaj(R.drawable.ic_message_black_24dp,getString(R.string.def_message_title_busy),getString(R.string.def_message_bussy_content)));
        mesaje.add(new Mesaj(R.drawable.ic_message_black_24dp,getString(R.string.def_message_birthday_title),getString(R.string.def_message_content_birthday)));
        mesageAdapter=new MesageAdapter(getApplicationContext(),0,mesaje);
        listView.setAdapter(mesageAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message=mesaje.get(position).getmMesaj();
                Intent intent=new Intent();
                intent.putExtra(MESSAGE_DEFAULT,message);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
