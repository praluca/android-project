package eu.ase.projectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eu.ase.projectandroid.database.DatabaseManager;

public class GraficActivity extends AppCompatActivity {
    DatabaseReference refff;
    int c1=0;
    int c2=0;
    int c3=0;
    int c4=0;
    int c5=0;
    int c6=0;
    int c7=0;
    EditText countryName;
    Button counter;
    TextView countryResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafic);
        countryName=findViewById(R.id.grafic_et_country_name);
        counter=findViewById(R.id.grafic_btn_getCountry);
        countryResult=findViewById(R.id.grafic_tv_numberOfCountry);

        counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void,Void,Integer>(){
                    String country=countryName.getText().toString();
                    @Override
                    protected Integer doInBackground(Void... voids) {
                        return DatabaseManager.getInstance(getApplicationContext()).getUserRegistrationDao().getNumberOfUsersByCountry(country);
                    }

                    @Override
                    protected void onPostExecute(Integer result) {
                        if(result!=null){
                            countryResult.setText("");
                            countryResult.setText(result+"");
                        }
                    }
                }.execute();
            }
        });

        refff= FirebaseDatabase.getInstance().getReference().child("UserR");

        refff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    UserR userR=data.getValue(UserR.class);
                    if(userR!=null){
                        if(userR.getCountry().equals(getString(R.string.Romania_title))){
                            c1++;
                        }else if(userR.getCountry().equals(getString(R.string.Olanda_title))){
                            c2++;
                        }else if(userR.getCountry().equals(getString(R.string.Rusia_title))){
                            c3++;
                        }else if(userR.getCountry().equals(getString(R.string.Bulgaria_title))){
                            c4++;
                        }else if(userR.getCountry().equals(getString(R.string.Franta_title))){
                            c5++;
                        }else if(userR.getCountry().equals(getString(R.string.MareaBritanie_title))){
                            c6++;
                        }else if(userR.getCountry().equals(getString(R.string.Italia_title))){
                            c7++;
                        }

                    }
                }
                drawPie(c1,c2,c3,c4,c5,c6,c7);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }
    public void drawPie(int c, int c2, int c3, int c4, int c5, int c6, int c7){
        AnimatedPieView animatedPieView=findViewById(R.id.grafic_pieView);
        AnimatedPieViewConfig config=new AnimatedPieViewConfig();
        config.startAngle(-90).addData(new SimplePieInfo(c, Color.parseColor("#ff0000"),getString(R.string.Romania_title))).
                addData(new SimplePieInfo(c2, Color.parseColor("#00ff00"),getString(R.string.Olanda_title))).
                addData(new SimplePieInfo(c3,Color.parseColor("#0000ff"),getString(R.string.Rusia_title))).
                addData(new SimplePieInfo(c4, Color.parseColor("#ffff00"),getString(R.string.Bulgaria_title))).
                addData(new SimplePieInfo(c5, Color.parseColor("#ff00bf"),getString(R.string.Franta_title))).
                addData(new SimplePieInfo(c6, Color.parseColor("#bf00ff"),getString(R.string.MareaBritanie_title))).
                addData(new SimplePieInfo(c7, Color.parseColor("#000000"),getString(R.string.Italia_title))).
                drawText(true).strokeMode(false).
                duration(2000).textSize(30);

        animatedPieView.applyConfig(config);
        animatedPieView.start();
    }



}
