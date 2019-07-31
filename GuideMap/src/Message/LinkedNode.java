package Message;

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

    public void setPre(int pre) {
        this.pre = pre;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof LinkedNode)) return false;
        final LinkedNode other = (LinkedNode) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getPre() != other.getPre()) return false;
        if (this.getPos() != other.getPos()) return false;
        if (this.getStep() != other.getStep()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof LinkedNode;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getPre();
        result = result * PRIME + this.getPos();
        result = result * PRIME + this.getStep();
        return result;
    }

    public String toString() {
        return "LinkedNode(pre=" + this.getPre() + ", pos=" + this.getPos() + ", step=" + this.getStep() + ")";
    }
}
