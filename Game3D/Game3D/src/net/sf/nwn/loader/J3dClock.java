
/*
 * $RCSfile: J3dClock.java,v $
 *
 * Copyright 2005-2008 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 *
 * $Revision: 1.5 $
 * $Date: 2008/02/28 20:17:25 $
 * $State: Exp $
 */

//package javax.media.j3d;
package net.sf.nwn.loader;

/**
 * Utility class to provide a more accurate replacement for
 * System.currentTimeMillis().
 */
class J3dClock {

    private static long deltaTime;
    private static final long nsecPerMsec = 1000000;

    /**
     * Private constructor, since no instance should ever be created.
     */
    private J3dClock() {
    }
    
    /**
     * Returns the current time in milliseconds. This is a more
     * accurate version of System.currentTimeMillis and should be used in
     * its place.
     *
     * @return the current time in milliseconds.
     */
    static long currentTimeMillis() {
        return (System.nanoTime() / nsecPerMsec) + deltaTime;
    }

    static {
        // Call time methods once without using their values to ensure that
        // the methods are "warmed up". We need to make sure that the actual
        // calls that we use take place as close together as possible in time.
        System.currentTimeMillis();
        System.nanoTime();

        // Compute deltaTime between System.currentTimeMillis()
        // and the high-res timer, use a synchronized block to force both calls
        // to be made before the integer divide
        long baseTime, baseTimerValue;
        synchronized (J3dClock.class) {
            baseTime = System.currentTimeMillis();
            baseTimerValue = System.nanoTime();
        }
        deltaTime = baseTime - (baseTimerValue / nsecPerMsec);
    }
}
