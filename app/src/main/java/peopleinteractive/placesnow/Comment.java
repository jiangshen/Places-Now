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

    Comment(String info) {
        score = 0;
        this.info = info;
        Long timeLong = System.currentTimeMillis()/1000;
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm");
        Date date = new Date();
        time = sdf.format(date);
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

    @Override
    public String toString() {
        return getScore() + "\n" + getInfo() + "\n" + getTime();
    }
}
