package eu.ase.projectandroid.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import eu.ase.projectandroid.R;

public class CustomAdapter extends SimpleCursorAdapter {

    private Context mContext;
    private Context appContext;
    private int layout;
    private Cursor cr;
    private final LayoutInflater inflater;

    public CustomAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context,layout,c,from,to);
        this.layout=layout;
        this.mContext = context;
        this.inflater= LayoutInflater.from(context);
        this.cr=c;
    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        TextView name=(TextView)view.findViewById(R.id.tv_cursoradapter);
        TextView phone=(TextView)view.findViewById(R.id.tv_cursoradapter2);
        ImageView image=(ImageView) view.findViewById(R.id.iv_contacts);

        int name_index=cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME);
        int phone_index=cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);

        name.setText(cursor.getString(name_index));
        phone.setText(cursor.getString(phone_index));
        image.setImageResource(R.drawable.ic_person_black_24dp);

    }

}
