package lv.bank.cards.dbsynchronizer.utils;

public class ContactlessSqlQuery {

    /*
     * This query should be used to update pcd_cards.contactless value after card ctime changes in any of the following CMS tables:
     *
     * ISSUING_LT.izd_card_history
     * ISSUING_LT.izd_chip_changed_tags
     * ISSUING_LT.izd_chip_cmd
     *
     * Full list of tables that require new mappings:
     *
     * ISSUING_LT.izd_card_history
     * ISSUING_LT.izd_chip_changed_tags
     * ISSUING_LT.izd_chip_cmd
     * ISSUING_LT.izd_cards
     * ISSUING_LT.izd_shadow_ctrl
     * ISSUING_LT.izd_chip_tags
     * ISSUING_LT.izd_chip_cmd_hist
     *
     *
     */

    private static final String QUERY =
            "select " +
                    "   case " +
                    "     when nvl(d.chip_app_id,0) <> 216 " +
                    "     then null  " + // 'pre-contactless card'

                    "     when nvl(d.current_value,d.default_value) = 'DF01020000' " +
                    "      and nvl(d.pending_value,'DF01020000') = 'DF01020000'  " + // null or pending the same status
                    "     then 1  " + // 'CTLS enabled'

                    "     when nvl(d.current_value,d.default_value) = 'DF01020000' " +
                    "      and d.pending_value  = 'DF0102C000' " +
                    "      and d.pending_in_rtps = 0 " +
                    "     then 3  " + //'CTLS enabled, pending blocking (script is prepared in CMS)'

                    "     when nvl(d.current_value,d.default_value) = 'DF01020000' " +
                    "      and d.pending_value  = 'DF0102C000' " +
                    "      and d.pending_in_rtps = 1 " +
                    "     then 5  " + //'CTLS enabled, pending blocking (script is sent to RTPS)'

                    "     when nvl(d.current_value,d.default_value) = 'DF0102C000' " +
                    "      and nvl(d.pending_value,'DF0102C000') = 'DF0102C000'   " + // null or pending the same status
                    "     then 0  " + //'CTLS blocked'

                    "     when nvl(d.current_value,d.default_value) = 'DF0102C000' " +
                    "      and d.pending_value  = 'DF01020000' " +
                    "      and d.pending_in_rtps = 0 " +
                    "     then 2  " + //'CTLS blocked, pending activation (script is prepared in CMS)'

                    "     when nvl(d.current_value,d.default_value) = 'DF0102C000' " +
                    "      and d.pending_value  = 'DF01020000' " +
                    "      and d.pending_in_rtps = 1 " +
                    "     then 4  " + // 'CTLS blocked, pending activation (script is sent to RTPS)'

                    "      " + // what the crap is this?!
                    "      " + // else '??? - current_value ['||d.current_value||'] default_value ['||d.default_value||'] pending_value ['||d.pending_value||']'

                    "      " + // failover
                    "     else null " +
                    "  end as contactless " +
                    "from ( " +
                    "    select  " +
                    "        c.card " +
                    "       ,c.chip_app_id " +

                    "        " + // all cards are personalized with contactless ON
                    "        " + // regardless of CHIP data in OUT files
                    "       ,'DF01020000'                                                        as default_value " +
                    "       ,(  " + // get contactless tag value from latest completed script
                    "         select  " +
                    "             ct.tag_value " +
                    "         from  " +
                    "             izd_chip_changed_tags ct  " +
                    "             inner join izd_chip_tags t  " +
                    "                     on ct.bank_c = t.bank_c  " +
                    "                    and t.tag_id  = ct.tag_id " +
                    "         where  " +
                    "             ct.card        = c.card " +
                    "         and ct.sequence_nr = c.sequence_nr " +
                    "         and t.tag          = 'BF5B' " +
                    "         and ct.tag_value like 'DF0102%' " +
                    "         and ct.script_id   = ( " +
                    "                               select  " +
                    "                                   max(script_id)  " +
                    "                               from  " +
                    "                                   izd_chip_changed_tags  " +
                    "                               where  " +
                    "                                   card        = ct.card  " +
                    "                               and sequence_nr = ct.sequence_nr  " +
                    "                               and tag_id      = ct.tag_id  " +
                    "                               and completed   = 'Y' " +
                    "                              ) " +
                    "        )                                                                   as current_value " +
                    "      ,(  " + // get contactless tag value of latest pendig script
                    "          select  " +
                    "              ct.tag_value " +
                    "          from  " +
                    "              izd_chip_changed_tags ct " +
                    "              inner join izd_chip_tags t   " +
                    "                      on ct.bank_c = t.bank_c  " +
                    "                     and t.tag_id  = ct.tag_id " +
                    "          where  " +
                    "              ct.card        = c.card " +
                    "          and ct.sequence_nr = c.sequence_nr " +
                    "          and t.tag          = 'BF5B' " +
                    "          and ct.tag_value like 'DF0102%' " +
                    "           " + // process latest CTLS script
                    "          and ct.script_id   = ( " +
                    "                                select  " +
                    "                                    max(ct2.script_id)  " +
                    "                                from  " +
                    "                                    izd_chip_changed_tags ct2 " +
                    "                                where  " +
                    "                                    ct2.card        = ct.card  " +
                    "                                and ct2.sequence_nr = ct.sequence_nr  " +
                    "                                and ct2.tag_id      = ct.tag_id " +
                    "                                and ct.tag_value like 'DF0102%' " +
                    "                               ) " +
                    "           " + // exclude selection if latest script is not pending execution
                    "          and ct.completed = 'N' " +
                    "           " + // exclude selection if latest script was executed unsuccessfully
                    "          and not exists ( " +
                    "                          select  " +
                    "                              1 " +
                    "                          from  " +
                    "                              izd_chip_cmd_hist " +
                    "                          where " +
                    "                              card   = ct.card         " + // lieks? index?
                    "                          and seq_nr = ct.sequence_nr  " + // lieks? index?
                    "                          and p1 = 'BF'                " + // lieks? index?
                    "                          and p2 = '5B'                " + // lieks? index?
                    "                          and data like '%DF0102%'     " + // lieks? index?
                    "                          and exec_result = '0'  " +
                    "                          and cmd_id = ct.script_id " +
                    "                         ) " +
                    "        )                                                                   as pending_value " +
                    "      ,(  " + // check if latest script is sent to RTPS
                    "        select " +
                    "            case " +
                    "                when cc.ctime <= ( " +
                    "                                  select  " +
                    "                                      ctime  " +
                    "                                  from  " +
                    "                                       izd_shadow_ctrl  " +
                    "                                  where  " +
                    "                                       bank_c = cc.bank_c  " +
                    "                                   and groupc = cc.groupc " +
                    "                                 ) " +
                    "                then 1 " +
                    "                else 0 " +
                    "            end " +
                    "        from  " +
                    "            izd_chip_cmd cc " +
                    "        where " +
                    "            cc.bank_c = c.bank_c " +
                    "        and cc.groupc = c.groupc " +
                    "        and cc.cmd_id = ( " +
                    "                         select  " +
                    "                             max(ct.script_id)  " +
                    "                         from  " +
                    "                             izd_chip_changed_tags ct " +
                    "                             inner join izd_chip_tags t   " +
                    "                                     on ct.bank_c = t.bank_c  " +
                    "                                    and t.tag_id  = ct.tag_id " +
                    "                         where  " +
                    "                             ct.card        = c.card " +
                    "                         and ct.sequence_nr = c.sequence_nr " +
                    "                         and t.tag          = 'BF5B' " +
                    "                         and ct.tag_value like 'DF0102%' " +
                    "                        ) " +
                    "       )                                                                    as pending_in_rtps " +
                    "    from  " +
                    "        izd_cards c " +
                    "    where  " +
                    "        c.card = ':cardNumber' " +
                    ") d ";

    public static String getContaclessQuery(String card) {
        return QUERY.replace(":cardNumber", card);
    }
}
