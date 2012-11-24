package org.koplabs.dbqueue.worker;

import org.koplabs.dbqueue.DBQueue;
import org.koplabs.dbqueue.MessageObj;

/**
 *
 * @author bastiao
 */
public class Take implements ITake
{

    private DBQueue queue;
    public Take(DBQueue queue)
    {
        this.queue = queue;
    }

    public MessageObj take() 
    {
        
        return queue.take();
    }
    
    
    
}
