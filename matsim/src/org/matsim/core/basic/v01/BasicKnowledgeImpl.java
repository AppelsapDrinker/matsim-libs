package org.matsim.core.basic.v01;

import java.util.ArrayList;
import java.util.List;

import org.matsim.core.basic.v01.facilities.BasicActivityOption;


public class BasicKnowledgeImpl implements BasicKnowledge<BasicActivityOption> {

	private List<BasicActivityOption> activities;
	private String description;
	
	protected BasicKnowledgeImpl() {
		// this is deliberately protected.  kai, jul09
	}
	
	public void addActivity(BasicActivityOption activity) {
		if (this.activities == null) {
			this.activities = new ArrayList<BasicActivityOption>();
		}
		this.activities.add(activity);
	}

	public List<BasicActivityOption> getActivities() {
		return this.activities;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

}
