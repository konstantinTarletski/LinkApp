package lv.bank.cards.core.linkApp.impl;

import java.util.Iterator;

import lv.bank.cards.core.linkApp.impl.BaseDAOHibernate;
import lv.bank.cards.core.entity.linkApp.RtcuRequestTemplate;
import lv.bank.cards.core.linkApp.dao.TemplatesDAO;

public class TemplatesDAOHibernate extends BaseDAOHibernate implements TemplatesDAO {

		@Override
		public RtcuRequestTemplate findTemplateByName(String name) {
			Iterator<?> i = sf.getCurrentSession()
			.createQuery("from RtcuRequestTemplate where name='"+name+"'")
			.iterate();
			if (i.hasNext()){
				return (RtcuRequestTemplate) i.next();
			}
			return null;
		}
	}
