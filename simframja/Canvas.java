package simframja;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JPanel {
    
    //private Visual[] visuals;
    
    private List<? extends Visual> visuals;
        
    private JFrame window;
    
    public Canvas(int width, int height) {
    	super();
    	setSize(width, height);
    }
    
    public JFrame displayInWindow(String title) {
    	if (window != null) return window;
    	
    	window = new JFrame(title);
    	window.setSize(getWidth(), getHeight());
    	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	window.setResizable(false);
    	window.add(this);
    	window.setVisible(true);
    	
    	return window;
    }
    
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	if (visuals == null) return;
    	
        Graphics2D g2 = (Graphics2D) g;
        synchronized(this) {
            for (Visual visual : visuals) {
                for (VisualElement elem : visual.visualElements()) {
                    g2.setColor(elem.color());
                    for (Shape s : elem.shapes()) {
                        g2.fill(s);
                    }
                }
            }
        }
        
    }
    
    public void render() { repaint(); }
    
    /*
    public synchronized void render(Visual[] visuals) {
        this.visuals = visuals;
        render();
    }*/
    
    public synchronized void render(List<? extends Visual> visuals) {
        this.visuals = new ArrayList<Visual>(visuals);
        render();
    }
    
    public boolean contains(Vector2 point) {
        return point.x > 0 && point.x < getWidth() &&
        	   point.y > 0 && point.y < getHeight();
    }
    
}