alter table pcd_cards_pending_tokenization
    add wallet_account_id VARCHAR2(64 char);

comment on column pcd_cards_pending_tokenization.wallet_account_id is 'Google active Wallet ID. Matches Visa ClientWalletAccountID property used to identify Wallet Account Holder entity';

