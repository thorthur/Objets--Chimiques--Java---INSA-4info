/*
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of chemicalmozart.

    chemicalmozart is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    chemicalmozart is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with chemicalmozart.  If not, see <http://www.gnu.org/licenses/>
*/

package org.chemicalmozart.model.implementations.reactionrules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.QuaterLeft;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;
import org.junit.Test;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;

public class HarmonicRR1Test extends TestCase{
	private boolean _isBarInCreationPresent = false;
	private boolean _isPickOneRRPresent = false;
	private boolean _isRythmeRRPresent = false;
	private boolean _isSolutionPresent = false;
	private boolean _isSolutionValid = false;

	private Solution setRR(HarmonicRR1 rr){
		rr.set_degree(new DegreeImpl(1));
		//create the BarInCreation solution
		Solution sol = new Solution();
		BarInCreation bic = new BarInCreation();
		sol.add(bic);
		sol.add(new QuaterLeft(4));
		sol.add(new BarNumber(1));

		//Instantiate a SubSolution object for the RR
		SubSolutionElements e = new  SubSolutionElements();
		List<Class<? extends Object>> l = new ArrayList<Class<? extends Object>>();
		l.add(BarInCreation.class);
		e.setTypeList(l);
		List<Object> ll = new ArrayList<Object>();
		ll.add(sol);
		ll.add(bic);
		e.setElements(ll);
		SubSolution<SubSolutionElements> subsol = new SubSolution<SubSolutionElements>(e);
		rr.set_barInCreationSolution(subsol);
		subsol.setSolution(sol);
		return sol;
	}

	@Test
	public void testBarInCreationPresent(){
		HarmonicRR1 rr = new HarmonicRR1();
		Solution sol = this.setRR(rr);

		rr.computeResult();
		this._isBarInCreationPresent = false;
		//Test the content of the BarInCreation Solution
		for(Object o : sol){
			if(o instanceof BarInCreation)
				this._isBarInCreationPresent = true;
		}
		assertTrue("The RR doesn't put the BarInCreation object back into the BarInCreation solution", this._isBarInCreationPresent);
	}


	@Test
	public void testPickOneRRPresent(){
		HarmonicRR1 rr = new HarmonicRR1();
		this.setRR(rr);

		Object[] result = rr.computeResult();
		this._isPickOneRRPresent = false;
		//Test the returned elements
		for(Object o : result){
			if(o instanceof PickOneRR){
				this._isPickOneRRPresent = true;
				break;
			}
		}
		assertTrue("The RR doesn't return a PickOneRR", this._isPickOneRRPresent);
	}

	@Test
	public void testSolutionPresent(){
		HarmonicRR1 rr = new HarmonicRR1();
		this.setRR(rr);

		Object[] result = rr.computeResult();
		//Test the returned elements
		this._isSolutionPresent = false;
		for(Object o : result){
			if(o instanceof Solution){
				this._isSolutionPresent = true;
				break;
			}
		}
		assertTrue("The Solution which contains all possible Degree is not present", this._isSolutionPresent);
	}

	@Test
	public void testSolutionValid(){
		HarmonicRR1 rr = new HarmonicRR1();
		this.setRR(rr);

		Object[] result = rr.computeResult();
		this._isSolutionValid = false;
		for(Object o : result){
			if(o instanceof Solution){
				Iterator<Object> it = ((Solution) o).iterator();
				while(it.hasNext()){
					Object o2 = it.next();
					if(o2 instanceof DegreeImpl){
						this._isSolutionValid = true;
						break;
					}
				}
			}
		}
		assertTrue("The Solution which contains all possible Degree is not valid", this._isSolutionValid);
	}
}
