/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2017 by the members listed in the COPYING,        *
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

package org.matsim.contrib.drt.optimizer.insertion;

import java.util.List;
import java.util.Optional;

import org.matsim.contrib.drt.optimizer.VehicleData;
import org.matsim.contrib.drt.optimizer.insertion.InsertionGenerator.Insertion;
import org.matsim.contrib.drt.optimizer.insertion.PathDataProvider.PathDataSet;
import org.matsim.contrib.drt.passenger.DrtRequest;
import org.matsim.contrib.dvrp.path.OneToManyPathSearch.PathData;

/**
 * @author michalm
 */
public class SingleVehicleInsertionProblem {
	public static class BestInsertion {
		public final InsertionWithPathData insertion;
		public final VehicleData.Entry vehicleEntry;
		public final double cost;

		public BestInsertion(InsertionWithPathData insertion, VehicleData.Entry vehicleEntry, double cost) {
			this.insertion = insertion;
			this.vehicleEntry = vehicleEntry;
			this.cost = cost;
		}
	}

	private final InsertionCostCalculator costCalculator;
	private final PathDataProvider pathDataProvider;

	public SingleVehicleInsertionProblem(PathDataProvider pathDataProvider, InsertionCostCalculator costCalculator) {
		this.pathDataProvider = pathDataProvider;
		this.costCalculator = costCalculator;
	}

	public Optional<BestInsertion> findBestInsertion(DrtRequest drtRequest, VehicleData.Entry vEntry,
			List<Insertion> insertions) {
		PathDataSet set = pathDataProvider.getPathDataSet(drtRequest, vEntry);
		int stopCount = vEntry.stops.size();

		double minCost = InsertionCostCalculator.INFEASIBLE_SOLUTION_COST;
		InsertionWithPathData bestInsertion = null;
		for (Insertion i : insertions) {
			InsertionWithPathData insertion = createInsertionWithPathData(i, set, stopCount);
			double cost = costCalculator.calculate(drtRequest, vEntry, insertion);
			if (cost < minCost) {
				bestInsertion = insertion;
				minCost = cost;
			}
		}

		return minCost == InsertionCostCalculator.INFEASIBLE_SOLUTION_COST ?
				Optional.empty() :
				Optional.of(new BestInsertion(bestInsertion, vEntry, minCost));
	}

	private InsertionWithPathData createInsertionWithPathData(Insertion insertion, PathDataSet set, int stopCount) {
		int i = insertion.pickupIdx;
		int j = insertion.dropoffIdx;

		// i -> pickup
		PathData toPickup = set.pathsToPickup[i]; // i -> pickup
		PathData fromPickup = set.pathsFromPickup[i == j ? 0 : i + 1]; // pickup -> (dropoff | i+1)
		PathData toDropoff = i == j ? null // pickup followed by dropoff
				: set.pathsToDropoff[j]; // j -> dropoff
		PathData fromDropoff = j == stopCount ? null // dropoff inserted at the end
				: set.pathsFromDropoff[j + 1];
		return new InsertionWithPathData(i, j, toPickup, fromPickup, toDropoff, fromDropoff);
	}
}
