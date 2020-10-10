package Helper.Dashboard;

import GraphStreamCoreHelper.GraphSim;

import java.io.File;

import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.Viewer;
/**
 * Maing logic controller which the UI calls
 * @author brandon
 *
 */
public class MainLogic { 


	private GraphSim gs;

	public MainLogic()
	{

	}

	public Viewer simulate_graph(Graph g)
	{
		gs = new GraphSim("Graph");
                 gs.CreateGraph(g);
		gs.initSim();
		return gs.get_display();


	}

	public Graph getGraph()
	{
		return gs.getGraph();
	}

}
