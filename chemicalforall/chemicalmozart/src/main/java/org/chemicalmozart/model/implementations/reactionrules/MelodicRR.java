/* 
	Copyright (C) 2012 Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChemicalLibSuper.

    ChemicalLibSuper is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChemicalLibSuper is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChemicalLibSuper.  If not, see <http://www.gnu.org/licenses/>
*/
package org.chemicalmozart.model.implementations.reactionrules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.print.attribute.standard.MediaSize.Other;

import org.chemicalmozart.model.implementations.ChordImpl;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.Note;
import org.chemicalmozart.model.implementations.Note.Type;
import org.chemicalmozart.model.implementations.Pitch;
import org.chemicalmozart.model.interfaces.Degree;

import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;




/**
 * This ReactionRule is used to create the melodic part of a bar. It takes the following parameters :
 * <br />
 * <ul>
 * 		<li>Note : Which is the Note we need to set</li>
 * 		<li>Pitch : Which is the pitch of the last note set.</li>
 * </ul>
 * The notes must hve a chord setted
 */
public class MelodicRR implements ReactionRule{
	/**
	 * The number of music notes in the current solution. This attribute must not react. It is set by the RythmicRR.
	 */
	@Dontreact private int _max;
	/**
	 * The current note to set. This note must have the position equal to _melodicNumber.
	 */
	private Note _note;
	/**
	 * The pitch is used to remember the last pitch we used in the melody. This allows the program to create some interesting melodic
	 * patterns.
	 */
	private Pitch _pitch;
	/**
	 * Represent the position of the current note that must be set. Every time the MelodiccRR reacts, this attribute must be increased.
	 */
	@Dontreact private int _melodicNumber;
	/**
	 * The current state of the reaction rule. The reaction rule must not succeed the compute select if this parameter is set to false
	 */
	@Dontreact	private boolean _activated;

	/**
	 * The compute result must assign the pitch of the Note. Let's say that the degree of ChordImpl is named deg. 
	 * There is for the moment 2 different cases :<br />
	 * - _note.get_type()==STRONG : The pitch must be chosen between the following degrees :
	 * 		<ul>
	 *	 		<li>deg</li>
	 *	 		<li>(deg+2)%8 + ((int)(deg+2)/(int)8)</li>
	 *			<li>(deg+4)%8 + ((int)(deg+4)/(int)8)</li>
	 *	 	</ul>
	 *	 	Then, the octave must be chosen to give the closest possible note with the new degree from _pitch.
	 * - In all the other cases :
	 * 		-> Choose a degree randomly
	 * 		-> Choose an octave close to the old one
	 */
	public Object[] computeResult() {
		if(_note.get_type()==Type.STRONG){
			int degreeValue = _note.get_chord().get_degrees().get_value();
			int notePitch;
			int randNumber = (int) ((Math.random())*3 %3);
			System.out.println("Rand : "+randNumber);
			switch(randNumber){
			case 0:
				notePitch = degreeValue;
				break;
			case 1: 
				notePitch = (degreeValue + 2 >7)?(degreeValue + 2)%8 + 1 : degreeValue + 2;
				break;
			default:
				notePitch = (degreeValue + 4 >7)?(degreeValue + 4)%8 + 1 : degreeValue + 4;
			}
			int octave = 0;
			if(Math.abs(notePitch - _pitch.getDegree().get_value())>3){
				if(notePitch>4)
					octave = _pitch.getOctave()+1;
				else
					octave = _pitch.getOctave() - 1;
			}else{
				octave = _pitch.getOctave();
			}
			_note.set_pitch(new Pitch(octave, new DegreeImpl(notePitch)));
		}
		else{//Not strong
			int notePitch = _pitch.getDegree().get_value();
			int randNumber = (int)(Math.random()*2 %2);
			if(randNumber==0){
				notePitch = (notePitch + 1 >7)?(notePitch + 1)%8 + 1 : notePitch + 1;
			}else{
				notePitch = (notePitch + 7 >7)?(notePitch + 7)%8 + 1 : notePitch + 7;
			}
			int octave = 0;
			if(Math.abs(notePitch - _pitch.getDegree().get_value())>3){
				if(notePitch>4)
					octave = _pitch.getOctave()+1;
				else
					octave = _pitch.getOctave() - 1;
			}else{
				octave = _pitch.getOctave();
			}
			_note.set_pitch(new Pitch(octave, new DegreeImpl(notePitch)));
		}
		_melodicNumber++;
		return new Object[]{_note, _note.get_pitch()};
	}
	/**
	 * Succeeds if the position of _note is equal to the melodic number and the MelodicRR is activated
	 */
	public boolean computeSelect() {
		//System.err.println("Compute select avec activated = "+_activated);
		//System.err.println("melodicNumber = "+_melodicNumber+" note.getPosition = "+_note.get_position()+" -> "+(_melodicNumber == _note.get_position() && _activated && _melodicNumber<_max));
		
		return _melodicNumber == _note.get_position() && _activated && _melodicNumber<_max;
	}

	/**
	 * @return the _max
	 */
	public int get_max() {
		return this._max;
	}
	/**
	 * @return the _melodicNumber
	 */
	public int get_melodicNumber() {
		return this._melodicNumber;
	}
	/**
	 * @return the _note
	 */
	public Note get_note() {
		return this._note;
	}
	/**
	 * @return the _pitch
	 */
	public Pitch get_pitch() {
		return this._pitch;
	}
	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}
	/**
	 * @return the state of the MelodicRR
	 */
	public boolean is_activated() {
		return this._activated;
	}

	/**
	 * @param state of the MelodicRR to set
	 */
	public void set_activated(boolean _activated) {
		this._activated = _activated;
	}
	/**
	 * @param _max the _max to set
	 */
	public void set_max(int _max) {
		this._max = _max;
	}
	/**
	 * @param _melodicNumber the _melodicNumber to set
	 */
	public void set_melodicNumber(int _melodicNumber) {
		this._melodicNumber = _melodicNumber;
	}
	/**
	 * @param _note the _note to set
	 */
	public void set_note(Note _note) {
		this._note = _note;
	}
	/**
	 * @param _pitch the _pitch to set
	 */
	public void set_pitch(Pitch _pitch) {
		this._pitch = _pitch;
	}
}