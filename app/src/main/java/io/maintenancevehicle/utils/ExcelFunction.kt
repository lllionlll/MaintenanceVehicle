package io.maintenancevehicle.utils

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import kotlin.math.max

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
                            if (columnIndex == 5) {
                                val dateValue = cell.dateCellValue
                                rowData.add(
                                    DateFunction.formatDate(
                                        date = dateValue,
                                        formatType = Constants.FORMAT1
                                    )
                                )
                            } else {
                                rowData.add(cell.toString())
                            }
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

    fun exportToExcel(
        context: Context, data: List<List<Any>>,
        fileName: String,
        uri: Uri
    ) {
        val maxWidth = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Sheet1")
        for ((rowIndex, rowData) in data.withIndex()) {
            val row: Row = sheet.createRow(rowIndex)
            for ((columnIndex, cellData) in rowData.withIndex()) {
                val cell = row.createCell(columnIndex)
                if (columnIndex == 6 && cellData.toString() != "Ghi chú" && cellData.toString()
                        .isNotEmpty()
                ) {
                    cell.setCellValue("${cellData}(${data[rowIndex][0]})")
                } else {
                    if (columnIndex == 5 && cellData.toString() != "Ngày tạo") {
                        val cellStyle = workbook.createCellStyle()
                        cellStyle.dataFormat = 22
                        cell.setCellValue(
                            DateFunction.formatDate(
                                dateString = cellData.toString(),
                                formatType = Constants.FORMAT1
                            )
                        )
                        cell.cellStyle = cellStyle
                    } else {
                        cell.setCellValue(cellData.toString())
                    }
                }
                maxWidth[columnIndex] = max(cellData.toString().length, maxWidth[columnIndex])
                sheet.setColumnWidth(columnIndex, 256 * (maxWidth[columnIndex] + 2))
            }
        }

        try {
            val directory = DocumentFile.fromTreeUri(context, uri)
            val fileNameExcel = "$fileName.xlsx"
            val mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"

            directory?.createFile(mimeType, fileNameExcel)?.let { documentFile ->
                val outputStream = context.contentResolver.openOutputStream(documentFile.uri)
                outputStream?.use { stream ->
                    workbook.write(stream)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}