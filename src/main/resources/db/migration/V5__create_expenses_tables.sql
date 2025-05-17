CREATE TABLE expense_category (
    id UUID PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

INSERT INTO expense_category (id, name)
VALUES (gen_random_uuid(), 'Other')
ON CONFLICT (name) DO NOTHING;


CREATE TABLE expenses_entry (
    id UUID PRIMARY KEY,
    reference_date DATE NOT NULL,
    category_id UUID NOT NULL REFERENCES expense_category(id),
    amount NUMERIC(15, 2) NOT NULL,
    description VARCHAR(255),
    is_fixed_expense BOOL NOT NULL
);

CREATE INDEX idx_expenses_entry_reference_date ON expenses_entry (reference_date);
