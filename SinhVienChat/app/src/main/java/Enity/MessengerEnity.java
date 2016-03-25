package Enity;

/**
 * Created by Kiên on 3/12/2016.
 */
public class MessengerEnity {
    private int avatar;
    private String name;


    public MessengerEnity() {
    }

    public MessengerEnity(int avatar, String name) {
        this.avatar = avatar;
        this.name = name;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
