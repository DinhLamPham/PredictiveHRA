package Helper.Dashboard;

import FormTemplate.SNAFrame;
import Helper.Common.MessageHelper;
import Helper.Common.Variables;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.ViewerListener;
import org.graphstream.ui.swingViewer.ViewerPipe;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
/**
 * Listener to handle click of nodes.
 * The custom graph stream library uses a tight loop to pump mouse click events
 * to the graph stream lib, but we take a signal based approach to this by using hte native java 
 * mouse listener, and on each event trigger the pump to the graph stream library
 * @author brandon
 *
 */
public class NodeClickListener implements ViewerListener , MouseInputListener, MouseWheelListener, KeyListener { 
	public boolean loop = true;
	private ViewerPipe vpipe = null;
	private View vw = null;
	private Graph graph = null;
        private String filePath="/ini/nodeid.txt";

	/**
	 * Constructor
	 * @param vpipe - Viewer Pipe of the graph UI
	 * @param vw - View of the current graph in swing
	 * @param g - graph object for the current graph in use
	 */
	public NodeClickListener(ViewerPipe vpipe, View vw, Graph g) {
		this.loop=true;
		this.vpipe = vpipe;
		this.vw = vw;
		this.graph = g;
		// Keep piping back while grph is out to hook mouse clicks
		this.vw.addMouseListener(this);
                this.vw.addMouseWheelListener(this);
                this.vw.addKeyListener(this);

	}


	/**
	 * Close the view when graph is no longer needed and detach all listners
	 * @param id - not used, but inherited by interface
	 */
	public void viewClosed(String id) {
		loop = false;
		vw.removeMouseListener(this);


	}
	/**
	 * Button push hook to label nodes/edges
	 * @param id - string id of node
	 */
	public void buttonPushed(String id)    
        {
            if(Variables.frmSNA != null)
                SNAFrame.lblCurrentNodeName.setText(id);

        }


	@Override
	/**
	 * Mouse release event to pump on release
	 */
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		vpipe.pump();
	}

	public void buttonReleased(String id) {
            
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
            if(e.getClickCount()==2)
            {
                vw.getCamera().resetView();
                vw.getCamera().setViewPercent(1.05);
            }
            
                

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Inherited function unused
	 */
	@Override
	public void mousePressed(MouseEvent e) {
//		 TODO Auto-generated method stub
//		vpipe.pump();
//		System.out.println("Pump it!");
	}


	/**
	 * Inherited function unused
	 */
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Inherited function unused
	 */
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        GraphZoomHelper.mouseWheelMoved(e, vw);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        GraphZoomHelper.keyPressed(e, vw);
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


}
