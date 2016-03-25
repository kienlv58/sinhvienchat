package Enity;

import java.io.Serializable;

/**
 * Created by Kiên on 3/15/2016.
 */
public class ProfileEnity implements Serializable {
    private String UID;
    private String avatar;
    private String email;

    private String name;
    private String birth;
    private String adress;
    private String school;
    private String friends;

    public ProfileEnity() {
    }
    public ProfileEnity(String UID, String avatar, String email, String name, String birth, String adress, String school, String friends) {
        this.UID = UID;
        this.avatar = avatar;
        this.email = email;
        this.name = name;
        this.birth = birth;
        this.adress = adress;
        this.school = school;
        this.friends = friends;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }
}
