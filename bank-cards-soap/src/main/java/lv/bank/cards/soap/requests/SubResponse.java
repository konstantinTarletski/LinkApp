package lv.bank.cards.soap.requests;

public class SubResponse extends ResponseElement {
	
	private SubResponse(boolean maskPan) {
		super("done", maskPan);
	}

	public static SubResponse forRequest(SubRequest r) {
		SubResponse subResponse = new SubResponse(r.isMaskPan());

		if (r.getDoId() != null) {
			subResponse.addAttribute("doId",r.getDoId());
		}
		subResponse.setOutTemplateName(r.getOutTemplateName());
		return subResponse;
	}
}
