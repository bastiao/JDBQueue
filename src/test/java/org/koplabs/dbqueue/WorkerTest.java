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
    
        
        JDBWorker worker = new JDBWorker(new TestTask());
        worker.start();
        
        DBQueue q = new DBQueue("queue.db");
        
        
        worker.close();
        try {
            worker.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(WorkerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
