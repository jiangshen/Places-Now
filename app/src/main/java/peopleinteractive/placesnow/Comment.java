package peopleinteractive.placesnow;

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
        time = timeLong.toString();
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

    @Override
    public String toString() {
//        StringBuilder comment = new StringBuilder();
//        comment.append(getScore() + "\n" + getInfo() + "\n" + getTime());
//        return comment.toString();
        return getScore() + "\n" + getInfo() + "\n" + getTime();
    }
}
