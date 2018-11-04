package groupdelta.trocatroca.Entities;

public class Chat {
    private String userID1;
    private String userID2;
    private String adID;

    public Chat() {
    }

    public String getUserID1() {
        return userID1;
    }
    public String getUserID2() {
        return userID2;
    }
    public String getAdID() {
        return adID;
    }

    public void setUserID1(String userID1) {
        this.userID1 = userID1;
    }
    public void setUserID2(String userID2) {
        this.userID2 = userID2;
    }
    public void setAdID(String adID) {
        this.adID = adID;
    }
}
