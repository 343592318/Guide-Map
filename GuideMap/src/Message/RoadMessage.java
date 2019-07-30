package Message;

public class RoadMessage {
    private int x1, x2, y1, y2;
    private SpotMessage start_spot;
    private SpotMessage end_spot;
    private String name;
    private int length;
    private int meihua;
    private int lvhua;

    public RoadMessage() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLvhua(int lvhua) {
        this.lvhua = lvhua;
    }

    public void setMeihua(int meihua) {
        this.meihua = meihua;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public int getLvhua() {
        return lvhua;
    }

    public int getMeihua() {
        return meihua;
    }

    public void setData(int x1, int x2, int y1, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public void setStart_spot(SpotMessage start_spot) {
        this.start_spot = start_spot;
    }

    public void setEnd_spot(SpotMessage end_spot) {
        this.end_spot = end_spot;
    }

    public SpotMessage getStart_spot() {
        return start_spot;
    }

    public SpotMessage getEnd_spot() {
        return end_spot;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && object.getClass() == SpotMessage.class) {
            RoadMessage roadMessage = (RoadMessage) object;
            return roadMessage.getName().equals(this.getName());
        }
        return false;
    }

    public RoadMessage tempnameRoad(String name) {
        RoadMessage roadMessage = new RoadMessage();
        roadMessage.setName(name);
        return roadMessage;
    }
}
