
package peopleinteractive.placesnow;
import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;


import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wbtho on 7/21/2016.
 */
public class DataManager {

    private static Map<String, ArrayList<Comment>> commentMap = new HashMap<>();

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
        if(!comment.equals("")) {
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


    public static ArrayList<Comment> recieveComments(String name) {
        Firebase comRef = new Firebase(mRef + "locations/" + name + "/comments");

        final ArrayList<Comment> comList = new ArrayList<>();

        comRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Comment comment = snapshot.getValue(Comment.class);
                    comList.add(comment);
                }
            }
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }
}