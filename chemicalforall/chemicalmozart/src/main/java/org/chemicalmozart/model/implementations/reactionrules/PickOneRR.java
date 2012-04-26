package org.chemicalmozart.model.implementations.reactionrules;

import java.util.ArrayList;
import java.util.List;

import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.QuaterLeft;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;
import org.chemicalmozart.model.implementations.solutionindentification.Temporary;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;


/**
 * This ReactionRule is used to pick a Degree in the temporary solution and move it
 * to the main solution. It also create a GarbageRR to remove the Temporary Bar from the main Solution
 * This rule disappears once it has react.<br />
 */
public class PickOneRR implements ReactionRule{

	/**
	 * This value represents the temporary bar which contains the possible following degrees.<br />
	 * It must contain :<br />
	 * <ul>
	 * 	<li>a <b>Temporary</b> object as identifier</li>
	 * 	<li>At least, one <b>DegreeImpl</b></li>
	 * </ul>
	 */
	private SubSolution<SubSolutionElements> _temporaryBar;
	/**
	 * This value represents the current bar in creation. This bar is a solution which must contain :<br />
	 * <ul>
	 * 	<li>a <BarInCreation</b> object as identifier</li>
	 * </ul>
	 */
	private SubSolution<SubSolutionElements> _barInCreation;


	/**
	 * The constructor is used to set the type of the element we want to match in
	 * the subsolutions _temporaryBar and _barInCreation.
	 */
	public PickOneRR(){
		super();
		
		SubSolutionElements eltsSolTemporary = new SubSolutionElements();
		List<Class<? extends Object>> typeListSolTemporary = new ArrayList<Class<? extends Object>>();
		typeListSolTemporary.add(Temporary.class);
		typeListSolTemporary.add(DegreeImpl.class);
		eltsSolTemporary.setTypeList(typeListSolTemporary);
		_temporaryBar = new SubSolution<SubSolutionElements>(eltsSolTemporary);
		
		SubSolutionElements eltsSolInCreation = new SubSolutionElements();
		List<Class<? extends Object>> typeListSolInCreation = new ArrayList<Class<? extends Object>>();
		typeListSolInCreation.add(BarInCreation.class);
		eltsSolInCreation.setTypeList(typeListSolInCreation);
		_barInCreation = new SubSolution<SubSolutionElements>(eltsSolInCreation);
	}

	/**
	 * It just returns the chosen DegreeImpl into the parent solution and a GarbageRR to remove the temporary solution.<br />
	 * It must also put back the BarInCreation into the concerned solution because due to the reaction, it will be consumed.
	 * @return the chosen DegreeImpl, a GarbageRR
	 */
	public Object[] computeResult() {
		Object chosenDegree = _temporaryBar.getElements().get(1);
		
		Solution inCreationSolution = _barInCreation.getSolution();
		BarInCreation babar = new BarInCreation();
		inCreationSolution.add(babar);
		
		return new Object[]{chosenDegree, new GarbageRR()};
	}

	/**
	 * The computeSelect always succeed
	 * @TODO check that the trick is working
	 */
	public boolean computeSelect() {
		return true;
	}

	/**
	 * @return the _barInCreation
	 */
	public SubSolution<SubSolutionElements> get_barInCreation() {
		return this._barInCreation;
	}

	/**
	 * @return the _temporaryBar
	 */
	public SubSolution<SubSolutionElements> get_temporaryBar() {
		return this._temporaryBar;
	}

	/**
	 * This is a one-shot rule
	 */
	public Multiplicity getMultiplicity() {
		return Multiplicity.ONE_SHOT;
	}

	/**
	 * @param _barInCreation the _barInCreation to set
	 */
	public void set_barInCreation(SubSolution<SubSolutionElements> _barInCreation) {
		this._barInCreation = _barInCreation;
	}

	/**
	 * @param _temporaryBar the _temporaryBar to set
	 */
	public void set_temporaryBar(SubSolution<SubSolutionElements> _temporaryBar) {
		this._temporaryBar = _temporaryBar;
	}
}
