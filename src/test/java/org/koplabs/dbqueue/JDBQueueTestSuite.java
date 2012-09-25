/*  Copyright   2012 - Luís A. Bastião Silva
 *
 *  This file is part of JDBQueue.
 *
 *  JDBQueue is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  JDBQueue is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with JDBQueue.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.koplabs.dbqueue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Luís A. Bastião Silva <luis.kop@gmail.com>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({org.koplabs.dbqueue.DBJUnitTest.class, 
    org.koplabs.dbqueue.DBQueueJUnitTest.class})
public class JDBQueueTestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
