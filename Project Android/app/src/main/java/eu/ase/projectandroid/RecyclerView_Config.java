package eu.ase.projectandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private UserAdapter userAdapter;

    public void setConfig(RecyclerView recyclerView,Context context,List<UserR> users, List<String> keys){
        mContext=context;
        userAdapter=new UserAdapter(users,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(userAdapter);

    }

    class UserItemView extends RecyclerView.ViewHolder {
        private TextView mUsername;
        private TextView mPhone;
        private TextView mCountry;
        private TextView mSex;


        private String key;

        public UserItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.user_list_item,parent,false));
            mUsername=(TextView)itemView.findViewById(R.id.tv_username);
            mPhone=(TextView)itemView.findViewById(R.id.tv_phone);
            mCountry=(TextView)itemView.findViewById(R.id.tv_country);
            mSex=(TextView)itemView.findViewById(R.id.tv_sex);

        }
        public void bind(UserR user, String key){
            mUsername.setText(user.getUsername());
            mPhone.setText(user.getPhone());
            mCountry.setText(user.getCountry());
            mSex.setText(user.getSex());
        }

    }

    class UserAdapter extends RecyclerView.Adapter<UserItemView>{
        private List<UserR> userList;
        private List<String> keys;

        public UserAdapter(List<UserR> userList, List<String> keys) {
            this.userList = userList;
            this.keys = keys;
        }

        @NonNull
        @Override
        public UserItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new UserItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull UserItemView holder, int position) {
            holder.bind(userList.get(position),keys.get(position));
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }
    }
}
