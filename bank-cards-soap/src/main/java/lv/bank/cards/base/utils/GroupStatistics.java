package lv.bank.cards.base.utils;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class GroupStatistics implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private String regEx;
	private long requests;
	private long errors;

	public GroupStatistics(String name, String regEx) {
		this.name = name;
		this.regEx = regEx;
	}
	
	public void incrementRequests() {
		requests++;
	}
	
	public long getAndResetRequests() {
		long l = requests;
		requests = 0L;
		return l;
	}
	
	public void incrementErrors() {
		errors++;
	}
	
	public long getAndResetErrors() {
		long l = errors;
		errors = 0L;
		return l;
	}
	
}
