/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.koplabs.dbqueue.worker;

import org.koplabs.dbqueue.MessageObj;

/**
 *
 * @author bastiao
 */
public interface ITake
{

    
    public MessageObj take();
}
