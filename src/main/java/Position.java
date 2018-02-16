public class Position implements Cloneable{
    
    int x, y;

    Position(){
        x = 0;
        y = 0;
    }

    @Override
    protected Position clone() {
        Position p = null;

        try {
            p = (Position) super.clone();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return p;
    }
}
