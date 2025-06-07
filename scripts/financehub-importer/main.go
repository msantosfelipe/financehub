package main

import (
	"flag"
	"fmt"
	"log"
	"os"

	"github.com/xuri/excelize/v2"
)

const filePath = "resources/financehub_import.xlsx"

func main() {
	sheet := flag.String("sheet", "", "Sheet name to import (Incomes or Expenses)")
	flag.Parse()

	validateSheet(*sheet)
	fmt.Println("\nStarting import process of FinanceHub for sheet", *sheet)

	f, err := excelize.OpenFile(filePath)
	if err != nil {
		log.Fatalf("Error opening file: %v", err)
	}

	rows, err := f.GetRows(*sheet)
	if err != nil {
		log.Fatalf("Error reading tab '%s': %v", *sheet, err)
	}

	headers := rows[0]
	colMap := make(map[string]int)
	for i, h := range headers {
		colMap[h] = i
	}

	for i, row := range rows[1:] {
		statusCol := colMap["Status"]
		if getString(row, statusCol) == "OK" {
			continue
		}

		rowIndex := i + 2

		var payload map[string]interface{}
		var host string
		switch *sheet {
		case Incomes:
			payload = buildIncomePayload(row, colMap)
			host = fmt.Sprintf("%s/financehub/api/v1/incomes", ApiUrl)
		case Expenses:
			payload = buildExpensesPayload(row, colMap)
			host = fmt.Sprintf("%s/financehub/api/v1/expenses", ApiUrl)
		default:
			os.Exit(1)
		}

		err := sendData(payload, host)

		cell, _ := excelize.CoordinatesToCellName(statusCol+1, rowIndex)

		if err != nil {
			log.Printf("Error in line %d of sheet %s: %v", rowIndex, *sheet, err)
			f.SetCellValue(*sheet, cell, "")
		} else {
			f.SetCellValue(*sheet, cell, "OK")
		}
	}

	if err := f.Save(); err != nil {
		log.Fatalf("Error saving in xml: %v", err)
	}

	fmt.Println("Import process finished.")
}
