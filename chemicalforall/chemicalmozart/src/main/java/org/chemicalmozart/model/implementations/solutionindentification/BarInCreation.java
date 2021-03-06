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

package org.chemicalmozart.model.implementations.solutionindentification;
/**
 * This class is used to identify a solution which represents a bar in creation.<br /><br />
 * This class memorize the state of the solution.
 * @see BarInCreationState
 * @author cedricandreolli
 *
 */
public class BarInCreation {
	/**
	 * This enumeration is used to define the current state of a barInCreation solution.<br />
	 * <br />
	 * It can take the following values :<br />
	 * <ul>
	 * 		<li><b>HARMONICRR</b> : means that the solution is waiting the action of a HarmonicRR</li>
	 * 		<li><b>RYTHMEHRR</b> : means that the solution is waiting the action of a RythmeHRR</li>
	 * </ul>
	 * @author cedricandreolli
	 *
	 */
	public enum BarInCreationState {HARMONICRR, RYTHMEHRR}

	/**
	 * Represents the current state of the BarInCreation solution
	 */
	BarInCreationState _state;

	/**
	 * @return the _state
	 */
	public BarInCreationState get_state() {
		return this._state;
	}

	/**
	 * @param _state the _state to set
	 */
	public void set_state(BarInCreationState _state) {
		this._state = _state;
	}

}