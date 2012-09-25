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
 *  along with PACScloud.  If not, see <http://www.gnu.org/licenses/>.
 */


package org.koplabs.dbqueue;

import java.io.File;
import org.junit.*;

/**
 *
 * @author Luís A. Bastião Silva <luis.kop@gmail.com>
 */
public class DBJUnitTest {
    
    
    private SQLiteDBManager db = null;
    
    public DBJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        db = new SQLiteDBManager("queue.db");
    }
    
    @After
    public void tearDown() 
    {
        db = null;
        File f = new File("queue.db");
        f.delete();
    }
    
    @Test
    public void testInsert() {
        System.out.println("Adding");
        db.addMessage("test");
        System.out.println("Get");
        db.getPendingMessage();
        
    }
    @Test
    public void testPoll() {
        System.out.println("Adding");
        db.addMessage("test");
        System.out.println("Get");
        db.getPendingMessage();
        
    }
    
    @Test
    public void testRemove()
    {
        System.out.println("Adding");
        db.addMessage("test");
        System.out.println("Get");
        db.getPendingMessage();
        
    }

}
