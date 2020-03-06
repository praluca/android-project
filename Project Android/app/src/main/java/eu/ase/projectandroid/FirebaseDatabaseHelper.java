package eu.ase.projectandroid;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceUser;
    private List<UserR> users=new ArrayList<>();

    public interface DataStatus{
        void dataIsLoaded(List<UserR> userRS,List<String> keys);
        void dataIsInserted();
        void dataIsUpdated();
        void dataIsDeleted();
    }
    public FirebaseDatabaseHelper() {
        mDatabase=FirebaseDatabase.getInstance();
        mReferenceUser=mDatabase.getReference("UserR");
    }
    public void readUsers(final DataStatus dataStatus){
        mReferenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                List<String> keys=new ArrayList<>();
                for(DataSnapshot keyNode:dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    UserR userR=keyNode.getValue(UserR.class);
                    users.add(userR);
                }
                dataStatus.dataIsLoaded(users,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
