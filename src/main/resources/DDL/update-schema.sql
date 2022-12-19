CREATE TABLE account_details (
  account_number BINARY(16) NOT NULL,
   first_name VARCHAR(255),
   last_name VARCHAR(255),
   date_of_birth date,
   address VARCHAR(255),
   opening_balance DECIMAL,
   closing_balance DECIMAL,
   created_at date,
   updated_at date,
   CONSTRAINT pk_account_details PRIMARY KEY (account_number)
);

CREATE TABLE card_details (
  card_number BINARY(16) NOT NULL,
   type VARCHAR(255),
   account_number BINARY(16),
   CONSTRAINT pk_card_details PRIMARY KEY (card_number)
);

CREATE TABLE transactions (
  transaction_id BINARY(16) NOT NULL,
   transaction_amount DECIMAL,
   transaction_fee DECIMAL,
   status VARCHAR(255),
   transaction_type VARCHAR(255),
   transaction_request VARCHAR(255),
   account_number BINARY(16),
   created_at date,
   updated_at date,
   CONSTRAINT pk_transactions PRIMARY KEY (transaction_id)
);

ALTER TABLE card_details ADD CONSTRAINT FK_CARD_DETAILS_ON_ACCOUNT_NUMBER FOREIGN KEY (account_number) REFERENCES account_details (account_number);

ALTER TABLE transactions ADD CONSTRAINT FK_TRANSACTIONS_ON_ACCOUNT_NUMBER FOREIGN KEY (account_number) REFERENCES account_details (account_number);