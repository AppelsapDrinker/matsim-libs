/* *********************************************************************** *
 * project: org.matsim.*
 * BasicRouteImpl.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2007, 2008 by the members listed in the COPYING,  *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package org.matsim.core.basic.v01;

import java.util.List;

import org.apache.log4j.Logger;

import org.matsim.api.basic.v01.Id;
import org.matsim.api.basic.v01.population.BasicRoute;
import org.matsim.core.utils.misc.Time;

public class BasicRouteImpl implements BasicRoute {

	private final static Logger log = Logger.getLogger(BasicRouteImpl.class);
	
	private double dist = Double.NaN;

	private double travTime = Time.UNDEFINED_TIME;
	
	private Id startLinkId = null;
	private Id endLinkId = null;

	/**
	 * This constructor is only needed for backwards compatibility reasons and thus is
	 * set to deprecated. New code should make use of the constructor which sets the Ids of the
	 * start and the end link of a Route correctly.
	 */
	@Deprecated
	protected BasicRouteImpl() {}
	// deliberately protected.  kai, jul09
	
	public BasicRouteImpl(Id startLinkId, Id endLinkId){
		// yyyyyy this should be protected
		this.startLinkId = startLinkId;
		this.endLinkId = endLinkId;
	}
	
	
	public double getDistance() {
		return dist;
	}

	public final void setDistance(final double dist) {
		this.dist = dist;
	}

	public final double getTravelTime() {
		return this.travTime;
	}
	
	public final void setTravelTime(final double travTime) {
		this.travTime = travTime;
	}

	@Deprecated
	public void setLinkIds(List<Id> linkids) {
		log.error("BasicRouteImpl.setLinkIds() is deprecated and has no effect on the route!");
	}

	public Id getStartLinkId() {
		return this.startLinkId;
	}
	
	public Id getEndLinkId() {
		return this.endLinkId;
	}

}
