package Enity;

/**
 * Created by Kiên on 3/13/2016.
 */
public class ConntentMessEnity {;
    private String msg;
    private String img;
    private String key;

    public ConntentMessEnity(String msg, String img, String key) {
        this.msg = msg;
        this.img = img;
        this.key = key;
    }

    public ConntentMessEnity() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
