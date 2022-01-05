-- table
create table pcd_cards_pending_tokenization
(
    card                        varchar2(19 char) not null,
    wallet_device_id            varchar2(64 char) not null,
    token_pan                   varchar2(19 char),
    bank_app_device_id          varchar2(64 char),
    bank_app_push_id            varchar2(64 char),
    device_type                 varchar2(20 char),
    source                      varchar2(20 char),
    corr_id                     varchar2(15 char),
    rec_date                    date not null,
    ctime                       date not null,
    constraint pcd_pending_tokenization_pk
        primary key (card, wallet_device_id)
);

-- comments
comment on table pcd_cards_pending_tokenization is 'Cards pending in-app token provisioning';
comment on column pcd_cards_pending_tokenization.card is 'Card number, PAN, FLD_002';
comment on column pcd_cards_pending_tokenization.token_pan is 'Token PAN, FLD_126 [TOKN]';
comment on column pcd_cards_pending_tokenization.wallet_device_id is 'Device ID, FLD_126[TDID]';
comment on column pcd_cards_pending_tokenization.bank_app_device_id is 'Mobile application device ID';
comment on column pcd_cards_pending_tokenization.bank_app_push_id is 'Mobile application push ID';
comment on column pcd_cards_pending_tokenization.device_type is 'Device type';
comment on column pcd_cards_pending_tokenization.source is 'Provisioning request source: mib or rtps';
comment on column pcd_cards_pending_tokenization.corr_id is 'Life Cycle Trace ID - Unique ID for messages of one Token FLD_126[TCOR]';
comment on column pcd_cards_pending_tokenization.rec_date is 'Token provisioning request registration date';
comment on column pcd_cards_pending_tokenization.ctime is 'Last changes date and time';

-- constraints
create index pcd_pending_token_ind on pcd_cards_pending_tokenization (card);
create unique index pcd_pending_token_corr_id on pcd_cards_pending_tokenization (corr_id);

-- triggers
create trigger pcd_pending_tokenization_trg1
    before insert or update
    on pcd_cards_pending_tokenization
    for each row
declare
    new_ctime pcd_cards_pending_tokenization.ctime%type ;
begin
    select sysdate into new_ctime from dual;
    :new.ctime := new_ctime;
end;
