CREATE TABLE expenses_entry (
    id UUID PRIMARY KEY,
    reference_date DATE NOT NULL,
    category VARCHAR(255) NOT NULL,
    amount NUMERIC(15, 2) NOT NULL,
    description VARCHAR(255),
    is_fixed_expense BOOL NOT NULL
);

CREATE INDEX idx_expenses_entry_reference_date ON expenses_entry (reference_date);
