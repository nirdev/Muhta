package model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by adirez18 on 20/01/2016.
 */
@ParseClassName("Contact")

public class Contact extends ParseObject {

    public String getUserId(){
        return  getString("userId");
    }

    public String getName() {
        return getString("name");
    }

    public String getAddedBy() {
        return getString("addedBy");
    }

    public String getPhoneNumber() {
        return getString("phoneNumber");
    }
}
