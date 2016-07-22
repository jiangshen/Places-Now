
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
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by wbtho on 7/21/2016.
 */
public class DataManager {

    private static final String mRef = "https://places-now.firebaseio.com/";

    private static final Firebase REF = new Firebase(mRef);

    public static EncloseLocation currentLoc;

    public static List<EncloseLocation> locList;


    public static void addLocation(final String name) {
        Firebase locRef = REF.child("locations");
        //if (loc.)
        EncloseLocation toAdd = new EncloseLocation(name);

        Firebase nameRef = locRef.child(toAdd.getName());

        Log.d("FIRE", toAdd.getName() + " aaaaa");

        // .child(toAdd.LL.toString()) for when we put LatLng back into this
        nameRef.child("description").setValue(toAdd.getDescription(), new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                }
            }
        });

        nameRef.child("comments").setValue(toAdd.getComments(), new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                }
            }
        });
    }

    public static void addComment(final String name, Comment comment) {
        Firebase comRef = new Firebase(mRef + "locations/" + name + "/comments");

        Firebase unique = comRef.push();

        unique.child("time").setValue(comment.getTime(), new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                }
            }
        });

        unique.child("info").setValue(comment.getInfo(), new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                }
            }
        });

        unique.child("score").setValue(comment.getScore(), new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                }
            }
        });


    }
}