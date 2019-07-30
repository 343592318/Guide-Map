package Operation;

public class Clean {
    private boolean[] draw;
    private boolean[] draw2;

    public Clean(boolean[] draw, boolean[] draw2) {
        this.draw = draw;
        this.draw2 = draw2;
    }

    public void init() {
        for (int i = 0; i < draw.length; i++) {
            draw[i] = false;
        }
        for (int i = 0; i < draw2.length; i++) {
            draw2[i] = false;
        }
    }
}
