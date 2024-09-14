package com.plant.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Plans {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int primaryKey;
	private String plansName;
	private String plansRs;
	private String timeDuration;
	private String UptoPots;
	private String packs;
	private String VisitsMonths;
	public Plans() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Plans(int primaryKey, String plansName, String plansRs, String timeDuration, String uptoPots, String packs,
			String visitsMonths) {
		super();
		this.primaryKey = primaryKey;
		this.plansName=plansName;
		this.plansRs = plansRs;
		this.timeDuration = timeDuration;
		UptoPots = uptoPots;
		this.packs = packs;
		VisitsMonths = visitsMonths;
	}
	public int getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(int primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getPlansName() {
		return plansName;
	}
	public void setPlansName(String plansName) {
		this.plansName = plansName;
	}
	public String getPlansRs() {
		return plansRs;
	}
	public void setPlansRs(String plansRs) {
		this.plansRs = plansRs;
	}
	public String getTimeDuration() {
		return timeDuration;
	}
	public void setTimeDuration(String timeDuration) {
		this.timeDuration = timeDuration;
	}
	public String getUptoPots() {
		return UptoPots;
	}
	public void setUptoPots(String uptoPots) {
		UptoPots = uptoPots;
	}
	public String getPacks() {
		return packs;
	}
	public void setPacks(String packs) {
		this.packs = packs;
	}
	public String getVisitsMonths() {
		return VisitsMonths;
	}
	public void setVisitsMonths(String visitsMonths) {
		VisitsMonths = visitsMonths;
	}
	@Override
	public String toString() {
		return "Plans [primaryKey=" + primaryKey + ", plansName=" + plansName + ", plansRs=" + plansRs
				+ ", timeDuration=" + timeDuration + ", UptoPots=" + UptoPots + ", packs=" + packs + ", VisitsMonths="
				+ VisitsMonths + "]";
	}
	
}
