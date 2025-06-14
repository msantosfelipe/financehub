package main

import (
	"log"
	"os"

	"github.com/joho/godotenv"
)

var apiUrl, credsPath, spreadsheetID string

func loadEnvs() {
	err := godotenv.Load()
	if err != nil {
		log.Fatal("Erro ao carregar .env")
	}

	apiUrl = os.Getenv("API_URL")
	credsPath = os.Getenv("GOOGLE_CREDS_PATH")
	spreadsheetID = os.Getenv("SPREADSHEET_ID")
}
