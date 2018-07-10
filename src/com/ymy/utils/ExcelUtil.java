package com.ymy.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelUtil {
	// 显示的导出表的标�?
	private String title;
	// 导出表的列名
	private String[] rowName;

	private List<Object[]> dataList = new ArrayList<Object[]>();

	HttpServletResponse response;

	// 构�?�方法，传入要导出的数据
	public ExcelUtil(String title, String[] rowName, List<Object[]> dataList) {
		this.dataList = dataList;
		this.rowName = rowName;
		this.title = title;
	}

	public static void main(String[] args) {
	
	}

	/*
	 * 导出数据
	 */
	public HSSFWorkbook export() throws Exception {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对�?
			HSSFSheet sheet = workbook.createSheet(title); // 创建工作�?

			// 产生表格标题�?
			HSSFRow rowm = sheet.createRow(0);
			HSSFCell cellTiltle = rowm.createCell(0);

			// sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方�? - 在下�? - 可扩展�??
			HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);// 获取列头样式对象
			HSSFCellStyle style = this.getStyle(workbook); // 单元格样式对�?

			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length - 1)));
			cellTiltle.setCellStyle(columnTopStyle);
			cellTiltle.setCellValue(title);

			// 定义�?�?列数
			int columnNum = rowName.length;
			HSSFRow rowRowName = sheet.createRow(2); // 在索�?2的位置创建行(�?顶端的行�?始的第二�?)

			// 将列头设置到sheet的单元格�?
			for (int n = 0; n < columnNum; n++) {
				HSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
				cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
				HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
				cellRowName.setCellValue(text); // 设置列头单元格的�?
				cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样�?
			}

			// 将查询出的数据设置到sheet对应的单元格�?
			for (int i = 0; i < dataList.size(); i++) {

				Object[] obj = dataList.get(i);// 遍历每个对象
				HSSFRow row = sheet.createRow(i + 3);// 创建�?�?的行�?

				for (int j = 0; j < obj.length; j++) {
					HSSFCell cell = null; // 设置单元格的数据类型
					if (j == 0) {
						cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(i + 1);
					} else {
						cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
						if (!"".equals(obj[j]) && obj[j] != null) {
							cell.setCellValue(obj[j].toString()); // 设置单元格的�?
						}
					}
					cell.setCellStyle(style); // 设置单元格样�?
				}
			}
			// 让列宽随�?导出的列长自动�?�应
			for (int colNum = 0; colNum < columnNum; colNum++) {
				int columnWidth = sheet.getColumnWidth(colNum) / 256;
				for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
					HSSFRow currentRow;
					// 当前行未被使用过
					if (sheet.getRow(rowNum) == null) {
						currentRow = sheet.createRow(rowNum);
					} else {
						currentRow = sheet.getRow(rowNum);
					}
					if (currentRow.getCell(colNum) != null) {
						HSSFCell currentCell = currentRow.getCell(colNum);
						if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							int length = currentCell.getStringCellValue().getBytes().length;
							if (columnWidth < length) {
								columnWidth = length;
							}
						}
					}
				}
				if (colNum == 0) {
					sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
				} else {
					sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
				}
			}

			if (workbook != null) {
				return workbook;
				// String fileName = "Excel-" +
				// String.valueOf(System.currentTimeMillis()).substring(4, 13) +
				// ".xls";
				// String headStr = "attachment; filename=\"" + fileName + "\"";
				// response = getResponse();
				// response.setContentType("APPLICATION/OCTET-STREAM");
				// response.setHeader("Content-Disposition", headStr);
				// OutputStream out = response.getOutputStream();
				// workbook.write(out);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 列头单元格样�?
	 */
	public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 11);
		// 字体加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边�?;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜�?;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边�?;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜�?;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边�?;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜�?;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边�?;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜�?;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字�?;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;

	}

	/*
	 * 列数据信息单元格样式
	 */
	public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		// font.setFontHeightInPoints((short)10);
		// 字体加粗
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边�?;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜�?;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边�?;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜�?;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边�?;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜�?;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边�?;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜�?;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字�?;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;

	}
}
