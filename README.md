# Introduction #

The basic idea of JDBQueue is to have a persistence Queue. Then, if the program need to restart, the queue is restored.
We use a database because serialize a Queue objet is not faster, for each insertion.

This messages in the queue has 2 states:
- PENDING: It waits for a worker poll the message. 
- PROGRESS: A worker is trying to work on the message. 

There is a third-party state, that it is when the message are handled and it is 
completed. The work do completeTask and the message are removed from the 
DBQueue (persistence queue). 

# Example #

In order to handle the message an implementation of ITask should be provided.
Moreover, to poll the messages automatically, a JDBWorker need to be created.


    DBQueue q = new DBQueue("queue.db");
    JDBWorker worker = new JDBWorker(q,new TestTask());
    worker.start();

    q.add("Test");
    q.add("Test2");
    q.poll();
    q.take();

    worker.join();



# Notes #

This work is still working in progress and used by a project. Generalization might be necessary to other cases. Feel free to contribute back to it.

# Author #


Luís A. Bastião Silva <luis.kop@gmail.com>



# Licence #

Copyright   2012 - Luís A. Bastião Silva

JDBQueue is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

JDBQueue is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with PACScloud.  If not, see <http://www.gnu.org/licenses/>.

