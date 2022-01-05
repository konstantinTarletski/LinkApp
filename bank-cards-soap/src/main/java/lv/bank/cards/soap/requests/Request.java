package lv.bank.cards.soap.requests;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Request {

	@Getter
	List<SubRequest> subRequests = new ArrayList<>();

	public Request() {
		super();
	}

	public void addSubRequest(SubRequest sr) {
		subRequests.add(sr);
	}

}
