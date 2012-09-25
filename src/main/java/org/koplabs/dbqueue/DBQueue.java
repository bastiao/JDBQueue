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

import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * DBQueue is a persistence queue. The ideia is to avoid that requests 
 * are lost when a program crash. 
 * 
 * @author Luís A. Bastião Silva <luis.kop@gmail.com>
 */
public class DBQueue
{
    private IDBManager db = null;
    private String fileName = "queue.db";
    private Object monitorWaitingForNew = new Object();
    
    public DBQueue(String fileName)
    {
        this.fileName=  fileName;
        db = new SQLiteDBManager(fileName);
    }

    /**
     * Add new message to the Queue
     * 
     * @param e Message
     * @return nothing for now
     */
    public String add(String e) 
    {
        db.addMessage(e);
        synchronized(monitorWaitingForNew)
        {
            monitorWaitingForNew.notifyAll();
        }
        
        return "";
    }

    /**
     * Poll a message
     * @return returns a message. if no message in the queue null is returned
     */
    public MessageObj poll() 
    {
        return db.getPendingMessage();
    }
    
    
    /**
     * Blocking poll message from the queue. 
     * @return returns the message. If the queue does not have message, it blocks
     * waiting for new messages.
     */
    public MessageObj take() 
    {
        MessageObj r = null;
        while((r=db.getPendingMessage())==null)
        {
            synchronized(monitorWaitingForNew)
            {
                try {
                    monitorWaitingForNew.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(DBQueue.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        return r;
    }
    

    
    public List<MessageObj> getAllProgressTask()
    {
    
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /**
     * Completes a task.
     * @param id 
     */
    public void completedTask(String id)
    {
        db.removeMessage(id);
    }
    
    
    
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int size() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
   
    
    
    
}
