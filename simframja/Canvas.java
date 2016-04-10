package simframja;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

/**
 * A JPanel designed for 2D graphics rendering.
 */
public class Canvas extends JPanel {
    
    /**
     * The current list of Visuals to render.
     * The render() method, which reassigns this member, is synchronized
     * with the render loop in paintComponent(), so the list
     * will never be pulled out from under while rendering.
     */
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
    public synchronized void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	if (visuals == null) return;
    	
        Graphics2D g2 = (Graphics2D) g;
        
        for (Visual visual : visuals) {
            for (VisualElement elem : visual.visualElements()) {
                g2.setColor(elem.color());
                for (Shape s : elem.shapes()) {
                    g2.fill(s);
                }
            }
        }
        
    }
    
    private void render() { repaint(); }
    
    /**
     * Renders the given visuals.
     * @param visuals the batch of Visuals to render next
     */
    public synchronized void render(List<? extends Visual> visuals) {
        /*
         * Canvas actually doesn't crash if you remove all synchronization.
         * But without it, render(visuals) will be called in vain, because
         * repaint() doesn't actively do any repainting and doesn't block; it just
         * notifies the Component that it should repaint when possible. And it's
         * not possible until it finishes its current paint job.
         * So, without synchronization, there's nothing to prevent tons of state updates
         * and an equivalent number of calls to render(visuals) during just
         * a single paint job. See what happens if you add
         * a Thread.sleep(1000) call inside paintComponent().
         */
        this.visuals = new ArrayList<>(visuals);
        render();
    }
    
    /**
     * Returns true if point is inside canvas
     * @param point the point to test
     */
    public boolean contains(Vector2 point) {
        return point.x > 0 && point.x < getWidth() &&
        	   point.y > 0 && point.y < getHeight();
    }
    
}