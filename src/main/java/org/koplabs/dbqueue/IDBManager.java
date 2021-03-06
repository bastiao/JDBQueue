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

/**
 * 
 * @author Luís A. Bastião Silva <luis.kop@gmail.com>
 */
public interface IDBManager 
{
    public MessageObj getPendingMessage();
    public MessageObj getProgressMessage();
    public void putEverythingPending() ;
    public void removeMessage(final String id);
    public void pendingTask(final String id);
    public String addMessage(String message);
    public int size(final String status);
    public MessageObj getPendingMessageContains(String s);
}
    