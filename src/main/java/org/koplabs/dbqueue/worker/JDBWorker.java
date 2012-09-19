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
package org.koplabs.dbqueue.worker;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.koplabs.dbqueue.ITask;
import org.koplabs.dbqueue.MessageObj;

/**
 *
 * @author bastiao
 */
public class JDBWorker extends Thread implements Observer
{
    
    private BlockingQueue<MessageObj> queue = new LinkedBlockingQueue<MessageObj>();
    
    private boolean askToDie = false;
    private ITask handler = null;
    public JDBWorker(ITask handler)
    {
        this.handler = handler;
    }
    
    @Override
    public void run()
    {
        while(!askToDie)
        {
            MessageObj msg = null;
            try {
                msg = this.queue.take();
            } catch (InterruptedException ex) {
                Logger.getLogger(JDBWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.handler.handlerMessage(msg);
        }
        
    }
    
    public void close()
    {
        this.askToDie = true;
    }

    public void update(Observable o, Object arg) 
    {
        
        MessageObj obj = (MessageObj) arg;
        queue.add(obj);
    }
    
}
