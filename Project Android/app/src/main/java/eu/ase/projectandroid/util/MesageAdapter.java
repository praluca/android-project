package eu.ase.projectandroid.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import eu.ase.projectandroid.R;

public class MesageAdapter extends ArrayAdapter<Mesaj> {
   private  Context mContext;
   private List<Mesaj> mMesaje=new ArrayList<>();


    public MesageAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Mesaj> mesaje) {
        super(context, 0, mesaje);

        mContext=context;
        mMesaje=mesaje;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem=convertView;

        if(listItem==null)
        {
            listItem= LayoutInflater.from(mContext).inflate(R.layout.lv_rows,parent,false);
        }

        Mesaj mesajCurent=mMesaje.get(position);

        ImageView imaage=listItem.findViewById(R.id.iv_adapter_image);
        imaage.setImageResource(mesajCurent.getmImageDrawable());

        TextView titlu=listItem.findViewById(R.id.tv_adapter_name);
        titlu.setText(mesajCurent.getmTitlu());

        TextView mesaj=listItem.findViewById(R.id.tv_adapter_mesage);
        mesaj.setText(mesajCurent.getmMesaj());

        return listItem;

    }
}
