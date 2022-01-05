alter table pcd_cards_pending_tokenization
    add token_ref_id VARCHAR2(64 char);

comment on column pcd_cards_pending_tokenization.token_ref_id is 'Token reference Id FLD_126[TREF] ';

