package dataDriven;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelDataDriver {

	public Map<Integer, List<String>> getData() {
		Map<Integer, List<String>> mapData = new HashMap<Integer, List<String>>();
		try {
			int index = 0;
			FileInputStream fis = new FileInputStream("./data/Book1.xlsx");
			XSSFWorkbook workBook = new XSSFWorkbook(fis);
			int sheets = workBook.getNumberOfSheets();
			System.out.println(sheets);
			for (int sheet = 0; sheet < sheets; sheet++) {
				if (workBook.getSheetName(sheet).equalsIgnoreCase("Sheet1")) {
					XSSFSheet actualSheet = workBook.getSheetAt(sheet);
					Iterator<Row> rows = actualSheet.iterator();
					Row firstRow = rows.next();
					Iterator<Cell> columnHeaders = firstRow.cellIterator();
					while (rows.hasNext()) {
						List<String> listOfBookDetails = new ArrayList<String>();
						Row nextRows = rows.next();
						Iterator<Cell> cellItr = nextRows.cellIterator();
						while (cellItr.hasNext()) {
							Cell eachCell = cellItr.next();
							if (eachCell.getCellType() == CellType._NONE) {
								break;
							} else if (eachCell.getCellType() == CellType.STRING) {
								listOfBookDetails.add(eachCell.getStringCellValue());
							} else if (eachCell.getCellType() == CellType.NUMERIC) {
								listOfBookDetails.add(NumberToTextConverter.toText(eachCell.getNumericCellValue()));
							}
						}
						mapData.put(index, listOfBookDetails);
						index++;
					}
					System.out.println(mapData);
					System.out.println(mapData.size());
					for (int map = 0; map < mapData.size(); map++) {
						List<String> mapValue = mapData.get(map);
						System.out.println(map + "-->" + mapValue);
					}
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return mapData;
	}

}
