CREATE TABLE investments (
    id UUID PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    type VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    active BOOL NOT NULL
);

CREATE TABLE investments_entry (
    id UUID PRIMARY KEY,
    reference_date DATE NOT NULL,
    investment_id UUID NOT NULL REFERENCES investments(id),
    amount NUMERIC(15, 2) NOT NULL
);

CREATE INDEX idx_investments_entry_reference_date ON investments_entry (reference_date);
