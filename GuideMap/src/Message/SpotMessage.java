package Message;

public class SpotMessage {
    private double x;
    private double y;
    private String name;
    private String nuture;
    private String phonenumber;
    private String introduction;

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setNuture(String nuture) {
        this.nuture = nuture;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getName() {
        return name;
    }

    public String getNuture() {
        return nuture;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && object.getClass() == SpotMessage.class) {
            SpotMessage spotMessage = (SpotMessage)object;
            return spotMessage.getName().equals(this.getName());
        }
        return false;
    }

    public SpotMessage tempnameSpot(String name) {
        SpotMessage spotMessage = new SpotMessage();
        spotMessage.setName(name);
        return spotMessage;
    }
}
