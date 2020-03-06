package eu.ase.projectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mRecyclerView=findViewById(R.id.recyclerview_users);
        new FirebaseDatabaseHelper().readUsers(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void dataIsLoaded(List<UserR> userRS, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView,getApplicationContext(),userRS,keys);
            }

            @Override
            public void dataIsInserted() {

            }

            @Override
            public void dataIsUpdated() {

            }

            @Override
            public void dataIsDeleted() {

            }
        });
    }
}
