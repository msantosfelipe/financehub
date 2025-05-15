CREATE TABLE expense_entry (
    id UUID PRIMARY KEY,
    reference_date DATE NOT NULL,
    category VARCHAR(255) NOT NULL,
    amount NUMERIC(15, 2) NOT NULL,
    description VARCHAR(255),
    is_fixed_expense BOOL
);

CREATE INDEX idx_expense_entry_reference_date ON expense_entry (reference_date);
