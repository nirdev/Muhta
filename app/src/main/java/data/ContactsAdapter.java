package data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.muhta.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by adirez18 on 20/01/2016.
 */
public class ContactsAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<HashMap<String,String>> contacts;
    private static LayoutInflater inflater = null;

    public ContactsAdapter (Context context, ArrayList<HashMap<String, String>>  data){
        mContext = context;
        contacts = data;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        //inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(convertView == null){

            view = inflater.inflate(R.layout.list_row, parent, false);

            TextView name = (TextView) view.findViewById(R.id.contact_name);

            HashMap<String, String> mContact;

            mContact = contacts.get(position);

            name.setText(mContact.get("name"));
        }

        return view;
    }
}
