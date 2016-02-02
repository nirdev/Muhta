package data;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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
        inflater = LayoutInflater.from(context);
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

        final ViewHolder mHolder;

        if(convertView == null){

            convertView = inflater.inflate(R.layout.list_row, parent, false);
            mHolder = new ViewHolder();

            mHolder.mText = (TextView) convertView.findViewById(R.id.contact_name);

            convertView.setTag(mHolder);
        }
        else{
            mHolder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, String> mContact;
        mContact = contacts.get(position);
//        Log.e("listPosition", "value: " +position);
        mHolder.mText.setText(mContact.get("name"));

        return convertView;
    }
    private class ViewHolder {
        private TextView mText;
        private ImageView mImage;
    }
}
