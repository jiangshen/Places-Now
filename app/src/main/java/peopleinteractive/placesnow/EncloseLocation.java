package peopleinteractive.placesnow;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wbtho on 7/21/2016.
 */
public class EncloseLocation {

    public Location loc;
    public String name;
    public String description;
    List<Comment> comments;

    public double latitude;
    public double longitude;

    public EncloseLocation() {}

    public EncloseLocation(Location location){
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        //this.name = location.    get name???
        this.description = location.toString();
        this.comments = new ArrayList<Comment>();
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }



    public void setDescription(EncloseLocation loc, String newDescription) {
        loc.description = newDescription;
    }

    public void addComment(EncloseLocation loc, Comment comment) {
        loc.comments.add(comment);
    }


}