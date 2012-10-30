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
package org.koplabs.dbqueue.worker;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.koplabs.dbqueue.DBQueue;
import org.koplabs.dbqueue.ITask;
import org.koplabs.dbqueue.MessageObj;

/**
 *
 * @author bastiao
 */
public class JDBWorker extends Thread
{
    
    private DBQueue queue = null;
    
    private boolean askToDie = false;
    private ITask handler = null;
    private int numberOfMessages = Integer.MAX_VALUE;
    
    public JDBWorker(DBQueue queue, ITask handler)
    {
        this.handler = handler;
        this.queue = queue;
    }
    
    
    public JDBWorker(DBQueue queue, ITask handler, int numberOfMessages)
    {
        this(queue, handler);
        this.numberOfMessages = numberOfMessages;
    }
    
    
    @Override
    public void run()
    {
        
        queue.putEverythingPending();
        
        System.out.println("Size: " + queue.size());
        System.out.println("Size of pending " + queue.sizePending());
        System.out.println("Size of progress " +queue.sizeProgress());
        int nretries = 0;
        while(!askToDie)
        {
            MessageObj msg = null;
            
            msg = this.queue.take();
            if (msg.equals("stop"))
                break;
            try
            {
                this.handler.handlerMessage(msg);
            }
            catch (Exception e)
            {
            }
            while (queue.sizeProgress()>=numberOfMessages)
            {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(JDBWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
                nretries++;
                if (nretries>100)
                    break;
                
            }
            
            
        }
        
    }
    
    public void close()
    {
        this.askToDie = true;
        this.queue.add("stop");
    }

    public void update(Observable o, Object o1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
