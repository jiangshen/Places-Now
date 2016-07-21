
package peopleinteractive.placesnow;
import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import java.util.List;

/**
 * Created by wbtho on 7/21/2016.
 */
public class DataManager {


    private static final Firebase REF = new Firebase("https://places-now.firebaseio.com/");

    public static EncloseLocation currentLoc;

    public static List<EncloseLocation> locList;


    public void addLocation(final Location loc, final MapMain context, final Runnable runnable) {
        Firebase locRef = REF.child("locations");
        EncloseLocation toAdd = new EncloseLocation(loc);

        Firebase nameRef = locRef.child(toAdd.getName()).push();

        //Firebase GPSRef = nameRef.child();

//        locRef.cre


    }







}