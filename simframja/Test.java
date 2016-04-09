package simframja;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

import static simframja.Util.*;

public class Test {
    
    static class MovingThing extends Projectile {
        
        public MovingThing() {
            super(0,0, new Vector2(rsign(rin(5)), rsign(rin(5))), null);
            for (int i = 0; i < 9; i++) {
                Rectangle2D shp = new Rectangle2D.Double();
                shp.setFrame((int)(rsign(rin(30))), (int)(rsign(rin(30))), (int)(rin(15)), (int)(rin(15)));
                ShapeEntity s = new MyRectangle(shp);
                s.setPosition(rin(5), rin(5));
                addEntity(s);
            }
            setPosition(rin(700), rin(700));
        }
        
        @Override
        public void collideWith(Entity e) {
            die();
        }
        
    }
    
    static class MyRectangle extends ShapeEntity {
        public MyRectangle(Rectangle2D shape) {
            addShape(shape);
            setColor(Color.GRAY);
        }

        @Override
        public void collideWith(Entity e) {
            // TODO Auto-generated method stub
            
        }
        
        public boolean collisionsEnabled() {
            return true;
        }
        
    }
    
    private static void spawn(int n, List<Entity> l) {
        for (int i = 0; i < n; i++) {
            l.add(new MovingThing());
        }
    }

    public static void main(String[] args) {
        Canvas canvas = new Canvas(800, 700);
        canvas.displayInWindow("simframja");
        
        List<Entity> things = new LinkedList<>();
        
        spawn(1, things);
        
        long st = System.currentTimeMillis();
        
        while (true) {
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