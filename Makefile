up:
	docker compose up -d

down:
	docker compose down

# Importer
import: truncate-balances truncate-incomes truncate-expenses truncate-expense_category import-incomes import-expenses

build-importer:
	cd scripts/financehub-importer && go build -o financehub-importer .

import-incomes:
	cd scripts/financehub-importer && ./financehub-importer --sheet Incomes

import-expenses:
	cd scripts/financehub-importer && ./financehub-importer --sheet Expenses

import-incomes-local:
	cd scripts/financehub-importer && go run . --sheet Incomes

import-expenses-local:
	cd scripts/financehub-importer && go run . --sheet Expenses

truncate-balances:
	docker compose exec postgres psql -U root -d financehub -c "TRUNCATE TABLE balances RESTART IDENTITY CASCADE;"

truncate-incomes:
	docker compose exec postgres psql -U root -d financehub -c "TRUNCATE TABLE incomes_entry RESTART IDENTITY CASCADE;"

truncate-expenses:
	docker compose exec postgres psql -U root -d financehub -c "TRUNCATE TABLE expenses_entry RESTART IDENTITY CASCADE;"

truncate-expense_category:
	docker compose exec postgres psql -U root -d financehub -c "TRUNCATE TABLE expense_category RESTART IDENTITY CASCADE;"
