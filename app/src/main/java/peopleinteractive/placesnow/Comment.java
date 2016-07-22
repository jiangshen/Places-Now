package peopleinteractive.placesnow;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Maggie on 7/21/2016.
 */
public class Comment {
    private String time;
    private String info;
    private int score;
    private boolean toggleUp;
    private boolean toggleDown;


    Comment() {}

    Comment(String info) {
        score = 0;
        this.info = info;
        Long timeLong = System.currentTimeMillis()/1000;
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm");
        Date date = new Date();
        time = sdf.format(date);
        toggleUp = false;
        toggleDown = false;
    }

    public void addToScore() {
        score++;
    }
    public void minusToScore() {
        score--;
    }

    public String getTime() {
        return time;
    }
    public int getScore() {
        return score;
    }
    public String getInfo() {
        return info;
    }

    public void setScore(int score) { this.score = score; }
    public boolean getToggleUp() {
        return toggleUp;
    }

    public boolean getToggleDown() {
        return toggleDown;
    }

    public void changeToggleUp() {
        toggleUp = !toggleUp;
    }

    public void changeToggleDown() {
        toggleDown = !toggleDown;
    }

    @Override
    public String toString() {
        return getScore() + "\n" + getInfo() + "\n" + getTime();
    }
}
