import java.util.ArrayList;
import java.util.List;

public class Status implements Cloneable{

    private int hp;

    // x, y
    Position position = new Position();

    // Position history
    List<Position> hist = new ArrayList<>();

    Status(int x, int y) {
        hp = 36;
        setPostion(x, y);
    }
    
    public int getHp(){
        return this.hp;
    }
    
    public void setHp(int hp){
        this.hp = hp;     
    }

    public void setPostion(int x, int y) {
        this.position.x = x;
        this.position.y = y;
        hist.add(position);
    }

    @Override
    protected Status clone() {
        Status s = null;

        try {
            s = (Status) super.clone();
            s.hist = new ArrayList<>(this.hist);
            s.position = position.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }

}
