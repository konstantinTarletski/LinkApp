package lv.bank.cards.core.linkApp.dao;

import lv.bank.cards.core.entity.linkApp.RtcuRequestTemplate;

public interface TemplatesDAO {
	public RtcuRequestTemplate findTemplateByName(String name);
}
