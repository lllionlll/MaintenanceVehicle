package io.maintenancevehicle.utils

import android.content.Context
import android.net.Uri
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

object ExcelFunction {

    fun readExcel(context: Context, uri: Uri): List<List<Any>> {
        val result: MutableList<MutableList<Any>> = mutableListOf()
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            try {
                val workbook = XSSFWorkbook(inputStream)
                val sheet = workbook.getSheetAt(0)

                for (rowIndex in 1 until sheet.physicalNumberOfRows) {
                    val row = sheet.getRow(rowIndex)
                    val rowData: MutableList<Any> = mutableListOf()

                    for (columnIndex in 0 until sheet.getRow(0).physicalNumberOfCells) {
                        val cell = row.getCell(columnIndex)
                        if (cell == null || cell.cellType == CellType.BLANK) {
                            rowData.add("")
                        } else {
                            rowData.add(cell.toString())
                        }
                    }
                    result.add(rowData)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return result
    }

    fun exportToExcel(data: List<List<Any>>, filePath: String) {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Sheet1")

        for ((rowCount, rowData) in data.withIndex()) {
            val row: Row = sheet.createRow(rowCount)
            for ((columnCount, cellData) in rowData.withIndex()) {
                val cell: Cell = row.createCell(columnCount)
                cell.setCellValue(cellData.toString())
            }
        }

        try {
            val dir = File(filePath)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val fileName = "${DateFunction.getCurrentDate().time}.xlsx"
            val file = File(dir, fileName)
            val outputStream = FileOutputStream(file)
            workbook.write(outputStream)
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}