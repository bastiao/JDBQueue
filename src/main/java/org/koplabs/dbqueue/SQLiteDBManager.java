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


import com.almworks.sqlite4java.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Luís A. Bastião Silva <luis.kop@gmail.com>
 */


public class SQLiteDBManager implements IDBManager 
{
    
    private String filename = "queue.db";
    private SQLiteQueue queue = null;
    
    private final String createServicePool = "CREATE TABLE ServicePool("
            + "IDService INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "status varchar(255) NOT NULL,"
            + "message varchar(255) NOT NULL)";
    
    
    

    public SQLiteDBManager(String filename) 
    {

        this.filename = filename;
        File dbFile = new File(this.filename);

        Logger.getLogger("com.almworks.sqlite4java").setLevel(Level.OFF);

        ThreadFactory thFact = new ThreadFactory() {

            public Thread newThread(Runnable r) {
                Thread th = new Thread(r);
                th.setPriority(Thread.NORM_PRIORITY);
                return th;
            }
        };
          
        if (!dbFile.exists()) {
            queue = new SQLiteQueue(dbFile, thFact);

            queue.start();

            queue.execute(new SQLiteJob<Object>()
            {

                protected Object job(SQLiteConnection db) throws SQLiteException 
                {
                    db.exec(createServicePool);
                    
                    return null;
                }
            });
            
        } else {
            queue = new SQLiteQueue(dbFile, thFact);
            queue.start();
            
        }

    }
    
    
    
    public int size(final String status)
    {
    
        Integer result = queue.execute(new SQLiteJob<Integer >() 
        {

            protected Integer  job(SQLiteConnection db) throws SQLiteException {
                Integer result2 = 0;
                try {
                    
                    SQLiteStatement st = db.prepare(
                            "SELECT count(*) FROM ServicePool"
                            + " WHERE status = '"+status+"' ");
                    try
                    {
                       st.step();
                       result2 = st.columnInt(0);
                        
                    } finally {
                        st.dispose();
                    }
   
                } catch (SQLiteException ex) {
                    ex.printStackTrace();
                }

                return result2;
            }
        }).complete();
        
        return result;
        
        
    }
    
    
    
    public String addMessage(final String message) {
     
        String result = queue.execute(new SQLiteJob<String >() 
        {

            protected String  job(SQLiteConnection db) throws SQLiteException {
                String result2 = "";
                try {
                    
                    SQLiteStatement st = db.prepare("INSERT INTO ServicePool"
                            + " (status, message) VALUES (?, ?)");
                    try
                    {
                        st.bind(1, "PENDING");
                        st.bind(2, message);
                        st.step();
                        
                        SQLiteStatement st2 = db.prepare("SELECT * FROM ServicePool "
                            + "ORDER BY IDService DESC LIMIT 1");
                        st2.step();
                        result2= st2.columnString(0);
                        
                        st2.dispose();
                        
                        
                        
                    } finally {
                        st.dispose();
                    }
   
                } catch (SQLiteException ex) {
                    ex.printStackTrace();
                }

                return result2;
            }
        }).complete();
        
        return result;
    }
    
    public void removeMessage(final String id)
    {
        queue.execute(new SQLiteJob<Object>() 
        {

            protected Object job(SQLiteConnection db) throws SQLiteException
            {
                try 
                {
                    SQLiteStatement st = db.prepare("DELETE FROM ServicePool WHERE IDService=?");
                    try 
                    {
                        st.bind(1, id);
                        st.step();
                    } 

                    finally 
                    {
                        st.dispose();
                    }
                }
                catch (SQLiteException ex) {
                    Logger.getLogger(SQLiteDBManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
                
        });
    }
    
    
    
    public void putEverythingPending() {
     
        MessageObj result = queue.execute(new SQLiteJob<MessageObj >() 
        {

            protected MessageObj  job(SQLiteConnection db) throws SQLiteException {
                List<String> result = new ArrayList<String>();
                String id = "";

                try {
                    SQLiteStatement st = db.prepare("SELECT * FROM ServicePool "
                            + "WHERE status = 'PENDING' LIMIT 1");
                    st.step();
                    while (st.hasRow())
                    {
                        id = st.columnString(0);
                        result.add(st.columnString(2));
                        st.dispose();
                        SQLiteStatement st2 = db.prepare("UPDATE ServicePool SET status='PENDING' WHERE status='PROGRESS");
                        
                        
                        st2.step();
                        st2.dispose();

                    }
                    


                    

                } catch (SQLiteException ex) {
                    Logger.getLogger(SQLiteDBManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
        }).complete();
        
        
    }

    
    

    public MessageObj getPendingMessage() {
     
        MessageObj result = queue.execute(new SQLiteJob<MessageObj >() 
        {

            protected MessageObj  job(SQLiteConnection db) throws SQLiteException {
                List<String> result = new ArrayList<String>();
                String id = "";

                try {
                    SQLiteStatement st = db.prepare("SELECT * FROM ServicePool "
                            + "WHERE status = 'PENDING' LIMIT 1");
                    st.step();
                    if (st.hasRow())
                    {
                        id = st.columnString(0);
                        result.add(st.columnString(2));
                        st.dispose();
                    }
                    
                    SQLiteStatement st2 = db.prepare("UPDATE ServicePool SET status='PROGRESS' WHERE IDService=?");

                    st2.bind(1, id);
                    st2.step();
                    st2.dispose();

                    

                } catch (SQLiteException ex) {
                    Logger.getLogger(SQLiteDBManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                MessageObj msg = new MessageObj();
                msg.setId(id);
                msg.setMsg(result.get(0));
                return msg;
            }
        }).complete();
        
        return result;
    }

    public MessageObj getProgressMessage() {
        
        MessageObj result = queue.execute(new SQLiteJob<MessageObj >() 
        {

            protected MessageObj  job(SQLiteConnection db) throws SQLiteException {
                List<String> result = new ArrayList<String>();
                String id = "";

                try {
                    System.out.println("Status");
                    SQLiteStatement st = db.prepare("SELECT * FROM ServicePool "
                            + "WHERE status = 'PROGRESS' LIMIT 1");
                    System.out.println("Status11");
                    st.step();
                    if (st.hasRow())
                    {
                        id = st.columnString(0);
                        result.add(st.columnString(2));
                        st.dispose();
                    }
                    System.out.println("Status2");
                    

                } catch (SQLiteException ex) {
                    Logger.getLogger(SQLiteDBManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                MessageObj msg = new MessageObj();
                msg.setId(id);
                msg.setMsg(result.get(0));
                return msg;
            }
        }).complete();
        
        return result;
        
    }
    
    
}
