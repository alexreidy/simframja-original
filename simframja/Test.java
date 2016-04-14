package simframja;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.awt.Color;
import java.awt.Point;

import static simframja.Util.*;

public class Test {
    
    static class MovingThing extends Projectile {
        
        public MovingThing() {
            super(0,0, new Vector2(rsign(rin(5)), rsign(rin(5))), null);
            for (int i = 0; i < 9; i++) {
                addEntity(new Rectangle(rin(20), rin(20), rin(10), rin(10), Color.GRAY));
            }
            setPosition(rin(700), rin(700));
            
        }
        
        @Override
        public void collideWith(Entity e) {
            die();
        }
        
    }
    
    private static void spawn(int n, List<Entity> l) {
        for (int i = 0; i < n; i++) {
            Projectile mt = new MovingThing();
            mt.seek(mposVec, 12);
            l.add(mt);
        }
    }

    static Point mpos = new Point();
    
    static Vector2 mposVec = new Vector2();
    
    public static void main(String[] args) {
        Canvas canvas = new Canvas(800, 700);
        canvas.displayInWindow("simframja");
        
        List<Entity> things = new LinkedList<>();
        
        spawn(1, things);
        
        long st = System.currentTimeMillis();
        
        while (true) {
            Point mp = canvas.getMousePosition();
            if (mp != null) mpos = mp;
            mposVec.x = mpos.x;
            mposVec.y = mpos.y;
            
            for (Iterator<Entity> it = things.iterator(); it.hasNext(); ) {
                Entity s = it.next();
                s.update(things);
                if (!s.isAlive() || !canvas.contains(s.getPosition())) {
                    it.remove();
                }
            }
            
            if (rn() > 0.2) {
                spawn(5, things);
            }
            
            if (things.size() == 0) {
                System.out.println(System.currentTimeMillis() - st);
                break;
            }
            
            try{Thread.sleep(10);}catch(Exception e){}
            
            canvas.render(things);
        }

    }

}