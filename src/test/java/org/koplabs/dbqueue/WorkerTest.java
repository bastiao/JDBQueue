package org.koplabs.dbqueue;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;
import org.koplabs.dbqueue.worker.JDBWorker;

/**
 *
 * @author bastiao
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

        public void handlerMessage(MessageObj message) {
            
            System.out.println(message);
            
        }
    
    }
    
    
    @Test
    public void workerTest()
    {
    
        DBQueue q = new DBQueue("queue.db");
        
        
        JDBWorker worker = new JDBWorker(q,new TestTask());
        worker.start();
        
        for (int i = 0 ; i<1000 ; i++)
        {
            q.add("je1");
            q.add("je2");
            q.add("je3");
            q.add("je4");
            q.add("je5");
        }
        worker.close();
        try {
            worker.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(WorkerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
