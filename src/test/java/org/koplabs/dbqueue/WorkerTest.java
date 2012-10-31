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

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;
import org.koplabs.dbqueue.worker.JDBWorker;

/**
 *
 * @author Luís A. Bastião Silva <luis.kop@gmail.com>
 */
public class WorkerTest {
    
    public WorkerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    class TestTask implements ITask
    {
        private DBQueue queue; 
        public TestTask(DBQueue queue )
        {
            this.queue = queue;
        }
        public void handlerMessage(MessageObj message) {
            
            System.out.println(message);
            queue.completedTask(message.getId());
            
        }
    
    }
    
    @Test
    public void workerTestQueue()
    {
        long startTime = System.currentTimeMillis();
        Queue q = new ConcurrentLinkedQueue();
        
        
        for (int i = 0 ; i<100 ; i++)
        {
            q.add("je1");
            q.add("je2");
            q.add("je3");
            q.add("je4");
            q.add("je5");
            
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Duration: " + duration + " ms");
    }
    
    
    
    @Test
    public void workerTestDBQueue()
    {
        long startTime = System.currentTimeMillis();
        DBQueue q = new DBQueue("queue.db");
        
        
        for (int i = 0 ; i<100 ; i++)
        {
            q.add("je1");
            q.add("je2");
            q.add("je3");
            q.add("je4");
            q.add("je5");
            
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Duration: " + duration + " ms");

    }
    
    
    
    //@Test
    public void workerTest()
    {
    
        DBQueue q = new DBQueue("queue.db");
        
        
        JDBWorker worker = new JDBWorker(q,new TestTask(q), 500);
        worker.start();
        
        for (int i = 0 ; i<10 ; i++)
        {
            q.add("je1");
            q.add("je2");
            q.add("je3");
            System.out.println("Size: " + q.size());
            System.out.println("Size of pending " + q.sizePending());
            System.out.println("Size of progress " +q.sizeProgress());
            q.add("je4");
            q.add("je5");
            
        }
        while(q.size()>0)
        {
             System.out.println("Size: " + q.size());
            System.out.println("Size of pending " + q.sizePending());
            System.out.println("Size of progress " +q.sizeProgress());
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(WorkerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Closing? " + q.size());
        worker.close();
        try {
            worker.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(WorkerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
