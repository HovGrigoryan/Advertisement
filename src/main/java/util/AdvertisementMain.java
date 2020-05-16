package util;

import commands.Commands;
import model.Category;
import model.Gender;
import model.Item;
import model.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import storage.DataStorage;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.*;

public class AdvertisementMain implements Commands {

    private static Scanner scanner = new Scanner(System.in);
    private static DataStorage dataStorage = new DataStorage();
    private static User currentUser = null;


    public static void main(String[] args) throws IOException, ClassNotFoundException, EOFException {
        dataStorage.initData();
        boolean Isrun = true;
        while (Isrun) {
            Commands.printMainCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case EXIT:
                    Isrun = false;
                    break;
                case LOGIN:
                    loginUSer();
                    break;
                case REGISTER:
                    regsiterUser();
                    break;
                case IMPORT_USERS:
                    importFromXlsx();
                    break;
                default:
                    System.out.println("Wrong Command");
            }
        }


    }

    private static void importFromXlsx() {
        System.out.println("Please select xlsx path");
        String xlsxPath = scanner.nextLine();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(xlsxPath);
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                String name = row.getCell(0).getStringCellValue();
                String surname = row.getCell(1).getStringCellValue();
                Double age = row.getCell(2).getNumericCellValue();
                Gender gender = Gender.valueOf(row.getCell(3).getStringCellValue());
                Cell phoneNumber = row.getCell(4);
                String phoneNumberStr = phoneNumber.getCellType() == CellType.NUMERIC ?
                        String.valueOf(Double.valueOf(phoneNumber.getNumericCellValue()).intValue()) : phoneNumber.getStringCellValue();
                Cell password = row.getCell(5);
                String passwordStr = password.getCellType() == CellType.NUMERIC ?
                        String.valueOf(Double.valueOf(password.getNumericCellValue()).intValue()) : password.getStringCellValue();
                User user = new User();
                user.setName(name);
                user.setSurname(surname);
                user.setAge(age.intValue());
                user.setGender(gender);
                user.setPhoneNumber(phoneNumberStr);
                user.setPassword(passwordStr);
                System.out.println(user);
                dataStorage.add(user);
                System.out.println("Import was success!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while importing users");
        }
    }

    private static void regsiterUser() throws IOException {
        System.out.println("Please Input user data name, surname, gender(MALE,FEMALE), age, phoneNumber,password");
        try {
            String userDataStr = scanner.nextLine();
            String userDataArr[] = userDataStr.split(",");
            User userFromStorage = dataStorage.getUser(userDataArr[4]);
            if (userFromStorage == null) {
                User user = new User();
                user.setName(userDataArr[0]);
                user.setSurname(userDataArr[1]);
                user.setGender(Gender.valueOf(userDataArr[2].toUpperCase()));
                user.setAge(Integer.parseInt(userDataArr[3]));
                user.setPhoneNumber(userDataArr[4]);
                user.setPassword(userDataArr[5]);
                dataStorage.add(user);
                System.out.println("User was added");
            } else {
                System.out.println("User already exists!");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong Data ");
        }
    }

    private static void loginUSer() throws IOException {
        System.out.println("Please input PhoneNumber, password");
        try {
            String loginStr = scanner.nextLine();
            String[] loginArr = loginStr.split(",");
            User user = dataStorage.getUser(loginArr[0]);
            if (user != null && user.getPassword().equals(loginArr[1])) {
                currentUser = user;
                loginSucces();
            } else {
                System.out.println("Wrong phoneNumber or password");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong Data!");
        }
//        }catch (NullPointerException e){
//            System.out.println("mmmmmmmmm");
//        }
    }

    private static void loginSucces() throws IOException {
        System.out.println("Welcome " + currentUser.getName() + "!");
        boolean Isrun = true;
        while (Isrun) {
            Commands.printUserCommand();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case LOGOUT:
                    Isrun = false;
                    break;
                case ADD_NEW_AD:
                    addNewItem();
                    break;
                case PRINT_MY_ADS:
                    dataStorage.printItemsByUsers(currentUser);
                    break;
                case PRINT_ALL_ADS:
                    dataStorage.printItems();
                    break;
                case PRINT_ADS_BY_CATEGORY:
                    printByCategory();
                    break;
                case PRINT_ALL_ADS_SORT_BY_TITLE:
                    dataStorage.printItemsOrderByTitle();
                    break;
                case PRINT_ALL_ADS_SORT_BY_DATE:
                    dataStorage.printItemsOrderByDate();
                    break;
                case DELETE_MY_ALL_ADDS:
                    dataStorage.deleteItemsByUser(currentUser);
                    break;
                case DELETE_AD_BY_iD:
                    deleteById();
                    dataStorage.printItems();
                    break;
                case IMPORT_ITEMS:
                    imporItemsFromXlsx();
                    break;
                case EXPORT_ITEMS:
                    exportItemstoXlsx();
                    break;
                default:
                    System.out.println("Wrong Command");
            }
        }

    }

    private static void exportItemstoXlsx() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("items");
        List<Item> items = FileUtil.deserializeItemList();
        int rowCount = 0;
        Row row = sheet.createRow(rowCount++);

        row.createCell(0).setCellValue("title");
        row.createCell(1).setCellValue("text");
        row.createCell(2).setCellValue("price");
        row.createCell(3).setCellValue("category");
        row.createCell(4).setCellValue("date");

        for (Item item : items) {
            row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(item.getTitle());
            row.createCell(1).setCellValue(item.getText());
            row.createCell(2).setCellValue(item.getPrice());
            row.createCell(3).setCellValue(String.valueOf(item.getCategory()));
            CellStyle cellStyle = workbook.createCellStyle();
            CreationHelper creationHelper = workbook.getCreationHelper();
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("m/d//yy h:mm"));
            Cell dataCell = row.createCell(4);
            dataCell.setCellValue(item.getCreateddate());
            dataCell.setCellStyle(cellStyle);

        }
        try (FileOutputStream fileOutputStream = new FileOutputStream("src\\main\\resources\\itemexport.xlsx")) {
            workbook.write(fileOutputStream);
            System.out.println("Items was exported");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void imporItemsFromXlsx() {
        System.out.println("Please select xlsx path");
        String xlsxPath = scanner.nextLine();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(xlsxPath);
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                String title = row.getCell(0).getStringCellValue();
                String text = row.getCell(1).getStringCellValue();
                Double price = row.getCell(2).getNumericCellValue();
                Category category = Category.valueOf(row.getCell(3).getStringCellValue());
                Date date = row.getCell(4).getDateCellValue();
                Item item = new Item();
                item.setTitle(title);
                item.setText(text);
                item.setPrice(price);
                item.setCategory(category);
                item.setCreateddate(date);
                item.setUser(currentUser);
                System.out.println(item);
                dataStorage.add(item);
                System.out.println("item imported succeed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void deleteById() throws IOException {
        System.out.println("please choose id from list");
        dataStorage.printItemsByUsers(currentUser);
        long id = Long.parseLong(scanner.nextLine());
        Item itemById = dataStorage.getItemById(id);
        if (itemById != null && itemById.getUser().equals(currentUser)) {
            dataStorage.deleteItemsById(id);
        } else {
            System.out.println("Wrong id!");
        }

    }

    private static void printByCategory() {
        System.out.println("Please choose category name from list: " + Arrays.toString(Category.values()));
        try {
            String categoryStr = scanner.nextLine();
            Category category = Category.valueOf(categoryStr);
            dataStorage.printItemsByCategory(category);
        } catch (Exception e) {
            System.out.println("Wrong Category");
        }
    }

    private static void addNewItem() {
        System.out.println("Please input item data title, text, price, category");
        System.out.println("Please choose category name from list: " + Arrays.toString(Category.values()));
        try {
            String itemDataStr = scanner.nextLine();
            String[] itemDataArr = itemDataStr.split(",");
            Item item = new Item(itemDataArr[0], itemDataArr[1], Double.parseDouble(itemDataArr[2]), currentUser, Category.valueOf(itemDataArr[3]), new Date());
            dataStorage.add(item);
            System.out.println("Item was succesfully added  ");
        } catch (Exception e) {
            System.out.println("Wrong Data!");
        }
    }
}
