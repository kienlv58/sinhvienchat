package Enity;

/**
 * Created by Kiên on 3/15/2016.
 */
public class FriendEnity{
    private String avatar;
    private String name;
    private String adrss;

    public FriendEnity() {
    }

    public FriendEnity(String avatar, String name, String adrss) {
        this.avatar = avatar;
        this.name = name;
        this.adrss = adrss;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdrss() {
        return adrss;
    }

    public void setAdrss(String adrss) {
        this.adrss = adrss;
    }

}
