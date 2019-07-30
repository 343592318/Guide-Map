package Message;

import java.util.LinkedList;

public class LinkedNode {
    private int pre;
    private int pos;
    private int step;

    public LinkedNode(int pre, int pos, int step) {
        this.pos = pos;
        this.pre = pre;
        this.step = step;
    }

    public int getPos() {
        return pos;
    }

    public int getPre() {
        return pre;
    }

    public int getStep() {
        return step;
    }
}
