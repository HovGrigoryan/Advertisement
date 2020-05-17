package util;

import model.Category;
import model.Gender;
import model.Item;
import model.User;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class XLSXUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    public static List<Item> readItems(String path) {
        List<Item> result = new LinkedList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(path);
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                result.add(getItemFromRow(row));

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while importing items");
        }
        return result;

    }

    private static Item getItemFromRow(Row row) {
        Cell phoneNumber = row.getCell(4);
        String phoneNumberStr = phoneNumber.getCellType() == CellType.NUMERIC ?
                String.valueOf(Double.valueOf(phoneNumber.getNumericCellValue()).intValue()) : phoneNumber.getStringCellValue();
        return (Item.builder()
                .id((long) Double.valueOf(row.getCell(0).getNumericCellValue()).intValue())
                .title(row.getCell(1).getStringCellValue())
                .text(row.getCell(2).getStringCellValue())
                .price(row.getCell(3).getNumericCellValue())
                .user(User.builder().phoneNumber(phoneNumberStr).build())
                .category(Category.valueOf(row.getCell(5).getStringCellValue()))
                .createddate(row.getCell(6).getDateCellValue())
                .build());

    }

    public static void writeItems(String path, List<Item> items) {
        String filename = "Items_export " + "_" + System.nanoTime() + " .xlsx";
        File file = new File(path, filename);
        try {
            file.createNewFile();
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("items");
            writeItemsHeader(sheet.createRow(0));
            int rowIndex = 1;
            CellStyle dataCellStyle = workbook.createCellStyle();
            short df = workbook.createDataFormat().getFormat("dd-MM-yyyy hh:mm:ss");
            dataCellStyle.setDataFormat(df);
            for (Item item : items) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(item.getId());
                row.createCell(1).setCellValue(item.getTitle());
                row.createCell(2).setCellValue(item.getText());
                row.createCell(3).setCellValue(item.getPrice());
                row.createCell(4).setCellValue(item.getUser().getPhoneNumber());
                row.createCell(5).setCellValue(item.getCategory().name());
                Cell datecell = row.createCell(6);
                datecell.setCellValue(item.getCreateddate());
                datecell.setCellStyle(dataCellStyle);

            }
            workbook.write(new FileOutputStream(file));

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while exporting items");

        }

    }

    private static void writeItemsHeader(XSSFRow row) {
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("title");
        row.createCell(2).setCellValue("text");
        row.createCell(3).setCellValue("price");
        row.createCell(4).setCellValue("user phone");
        row.createCell(5).setCellValue("category");
        row.createCell(6).setCellValue("createDate");
    }


    public static List<User> readUsers(String path) {
        List<User> result = new LinkedList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(path);
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                result.add(getUserFromRow(row));

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while importing users");
        }
        return result;

    }

    private static User getUserFromRow(Row row) {
        Cell phoneNumber = row.getCell(4);
        String phoneNumberStr = phoneNumber.getCellType() == CellType.NUMERIC ?
                String.valueOf(Double.valueOf(phoneNumber.getNumericCellValue()).intValue()) : phoneNumber.getStringCellValue();
        Cell password = row.getCell(5);
        String passwordStr = password.getCellType() == CellType.NUMERIC ?
                String.valueOf(Double.valueOf(password.getNumericCellValue()).intValue()) : password.getStringCellValue();
        return (User.builder()
                .name(row.getCell(0).getStringCellValue())
                .surname(row.getCell(1).getStringCellValue())
                .age(Double.valueOf(row.getCell(2).getNumericCellValue()).intValue())
                .gender(Gender.valueOf(row.getCell(3).getStringCellValue()))
                .phoneNumber(phoneNumberStr)
                .password(passwordStr)
                .build());

    }
}
