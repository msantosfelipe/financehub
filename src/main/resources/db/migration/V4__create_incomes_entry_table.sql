CREATE TABLE incomes_entry (
    id UUID PRIMARY KEY,
    reference_date DATE NOT NULL,
    gross_amount NUMERIC(15, 2) NOT NULL,
    discount_amount NUMERIC(15, 2) NOT NULL,
    net_amount NUMERIC(15, 2) NOT NULL,
    type VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

CREATE INDEX idx_incomes_entry_reference_date ON incomes_entry (reference_date);
