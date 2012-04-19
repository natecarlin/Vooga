package states;

import java.util.ArrayList;

/**
 * Data class used for holding all possible StateFactories. Iterated over by
 * the Enemy class during loading
 * @author James Pagliuca
 *
 */
public class StateFactories {
	
	private ArrayList<StateFactory> allStateFactories;
	
	public StateFactories(){
		allStateFactories = new ArrayList<StateFactory>();
		FullHealthState.FHStateFactory fh = new FullHealthState.FHStateFactory();
		LowHealthState.LHStateFactory lh = new LowHealthState.LHStateFactory();
		allStateFactories.add(fh);
		allStateFactories.add(lh);
	}
	
	public ArrayList<StateFactory> getAllStateFactories(){
		return allStateFactories;
	}

}
