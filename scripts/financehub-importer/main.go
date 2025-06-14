package main

import (
	"context"
	"flag"
	"fmt"
	"log"
	"time"

	"github.com/xuri/excelize/v2"
	"google.golang.org/api/option"
	"google.golang.org/api/sheets/v4"
)

const filePath = "resources/financehub_import.xlsx"

func main() {
	fmt.Println("##### FinanceHub Importer #####")

	loadEnvs()

	sheet := flag.String("sheet", "", "Sheet name to import (Incomes or Expenses)")
	flag.Parse()

	validateSheet(*sheet)
	fmt.Println("Starting import process of FinanceHub for sheet", *sheet)

	if shouldLoadSheetFromDrive(credsPath, spreadsheetID) {
		fmt.Println("Importing from Google Drive")
		rows, srv := loadSheetFromDrive(credsPath, spreadsheetID, *sheet)
		headersIndexMap := getHeadersIndexMap(rows[0])
		for i, row := range rows[1:] {
			if isRowAlreadyProcessed(headersIndexMap, row) {
				continue
			}
			rowIndex := i + 2
			col := headersIndexMap["Status"]
			status := "OK"
			if err := processRow(*sheet, headersIndexMap, row); err != nil {
				status = ""
				log.Printf("Error in line %d of sheet %s: %v", rowIndex, *sheet, err)
			}
			if err := setGoogleSheetStatus(srv, spreadsheetID, *sheet, rowIndex, col, status); err != nil {
				cell := fmt.Sprintf("%s%d", getColumnLetter(col), rowIndex)
				log.Printf("Failed to update status at %s: %v", cell, err)
			}
		}
	} else {
		fmt.Println("Importing from file")
		rows, f := loadSheetFromFile(*sheet)
		headersIndexMap := getHeadersIndexMap(rows[0])
		for i, row := range rows[1:] {
			if isRowAlreadyProcessed(headersIndexMap, row) {
				continue
			}
			rowIndex := i + 2
			cell, _ := excelize.CoordinatesToCellName(headersIndexMap["Status"]+1, rowIndex)
			err := processRow(*sheet, headersIndexMap, row)
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
	}

	fmt.Println("Import process finished.")
}

func shouldLoadSheetFromDrive(credsPath, spreadsheetId string) bool {
	return credsPath != "" && spreadsheetId != ""
}

func loadSheetFromDrive(credsPath, spreadsheetID, sheet string) ([][]string, *sheets.Service) {
	ctx := context.Background()

	srv, err := sheets.NewService(ctx, option.WithCredentialsFile(credsPath))
	if err != nil {
		log.Fatalf("Unable to create Sheets service: %v", err)
	}

	resp, err := srv.Spreadsheets.Values.Get(spreadsheetID, sheet).Do()
	if err != nil {
		log.Fatalf("Unable to read data from sheet '%s': %v", sheet, err)
	}

	return convertToStringRows(resp.Values), srv
}

func loadSheetFromFile(sheet string) ([][]string, *excelize.File) {
	f, err := excelize.OpenFile(filePath)
	if err != nil {
		log.Fatalf("Error opening file: %v", err)
	}

	rows, err := f.GetRows(sheet)
	if err != nil {
		log.Fatalf("Error reading tab '%s': %v", sheet, err)
	}

	return rows, f
}

func setGoogleSheetStatus(srv *sheets.Service, spreadsheetID, sheet string, row, col int, status string) error {
	colLetter := getColumnLetter(col)
	cell := fmt.Sprintf("%s!%s%d", sheet, colLetter, row)

	vr := &sheets.ValueRange{
		Values: [][]any{{status}},
	}

	_, err := srv.Spreadsheets.Values.Update(spreadsheetID, cell, vr).ValueInputOption("RAW").Do()
	time.Sleep(1 * time.Second) // requests per minute quota
	return err
}

func getColumnLetter(col int) string {
	letters := ""
	for col >= 0 {
		letters = string(rune('A'+(col%26))) + letters
		col = col/26 - 1
	}
	return letters
}
