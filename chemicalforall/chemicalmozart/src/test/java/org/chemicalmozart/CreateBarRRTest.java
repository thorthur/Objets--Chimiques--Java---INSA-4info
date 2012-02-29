package org.chemicalmozart;

import java.util.Iterator;

import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.QuaterPerBar;
import org.chemicalmozart.model.implementations.reactionrules.CreateBarRR;
import org.chemicalmozart.model.implementations.reactionrules.MoveToResultRR;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;

import fr.insa.rennes.info.chemical.backend.Solution;
import junit.framework.TestCase;

public class CreateBarRRTest extends TestCase {

	public void testComputeResult() {
		boolean rrMissing = true;
		boolean solPresent = false;
		boolean moveToResultPresent = false;
		boolean isBarNumberPresent = false;
		boolean isBarNumberValid = false;
		
		Solution sol = new Solution();
		BarNumber measureNumber = new BarNumber(1);
		QuaterPerBar quaterPerBar = new QuaterPerBar(4);
		sol.add(measureNumber);
		sol.add(quaterPerBar);
		sol.add(new CreateBarRR());
		sol.react();
		while(!sol.is_inert());
		Iterator<Object> it = sol.iterator();
		while(it.hasNext()){
			Object o = it.next();
			if(o instanceof Solution){//Test if the bar in creation is in the main solution
				Iterator<Object> it2 = ((Solution) o).iterator();
				while(it2.hasNext()){
					Object o2 = it2.next();
					if(o2 instanceof BarInCreation){
						solPresent = true;
						break;
					}
				}
			}else if(o instanceof CreateBarRR){//There must not be any CreateMeasureRR
				rrMissing = false;
			}else if(o instanceof MoveToResultRR){
				moveToResultPresent = true;
			}else if(o instanceof BarNumber){
				isBarNumberPresent = true;
				if(((BarNumber) o).getValue() == 2)
					isBarNumberValid = true;
			}
		}
		assertTrue("Tests if CreateBarRR has been deleted", rrMissing);
		assertTrue("Tests if the new bar has been found", solPresent);
		assertTrue("Tests if the MoveToResultRR is present", moveToResultPresent);
		assertTrue("Tests if the quaterPerBar is still present in the main solution", sol.contains(quaterPerBar));
		assertTrue("Tests if the BarNumber is present in the main solution", isBarNumberPresent);
		assertTrue("Tests if the BarNumber is valid in the main solution", isBarNumberValid);
	}

}