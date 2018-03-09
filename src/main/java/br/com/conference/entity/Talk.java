package br.com.conference.entity;

import org.joda.time.DateTime;

public class Talk implements Comparable<Talk>{

	private String name;
	
	private int duration;
	
	private DateTime startTime;

	public Talk() {
		super();
	}

	public Talk(String name, int duration) {
		super();
		this.name = name.trim();
		this.duration = duration;
	}

	public DateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + duration;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Talk other = (Talk) obj;
		if (duration != other.duration)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public int compareTo(Talk o) {
		if(this.duration != o.duration) {
			return this.duration < o.duration ? -1 : 1;
		}else {
			return this.name.compareTo(o.name);
		}
	}
	
}
