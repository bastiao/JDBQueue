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

import java.io.File;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Luís A. Bastião Silva <luis.kop@gmail.com>
 */
public class DBQueueJUnitTest {
    
    public DBQueueJUnitTest() 
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception 
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception 
    {
        
        File f = new File("queue.db");
        f.delete();
    }
    
    @Before
    public void setUp() 
    {
    }
    
    @After
    public void tearDown() 
    {
    }
    
    @Test
    public void testAdd()
    {
        DBQueue q = new DBQueue("queue.db");
        q.add("ss");
        
        assertEquals("ss",q.poll().getMsg());
    }
    
    
    
    @Test
    public void testMassiveAdd()
    {
        
        DBQueue q = new DBQueue("queue.db");
        for (int i = 0 ; i<1000; i++)
        {
            q.add("ss");
        }
        for (int i = 0 ; i<1000; i++)
        {
            assertEquals("ss",q.poll().getMsg());
        }
        
        
    }
    
    
    
}
