/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChLoe.

    ChLoe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChLoe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChLoe.  If not, see <http://www.gnu.org/licenses/>
*/
package morph;
import fr.insa.rennes.info.chemical.example.chemorphing.backend.Pool;
import junit.framework.TestCase;

/**
 * @author ArthurTemple
 * Unit tests for Chemorphing's Pool class
 */

public class PoolTest extends TestCase {

	private Pool pool;
	
	/**
	 * @param name
	 */
	public PoolTest(String name) {
		super(name);
		this.pool = new Pool();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link Pool#loadPool()}.
	 */
	public void testLoad() {
		assertTrue(this.pool.loadPool());
	}

	/**
	 * Test method for {@link Pool#savePool()}.
	 */
	public void testSave() {
		this.pool.loadPool();
		assertTrue(this.pool.savePool());
	}


}