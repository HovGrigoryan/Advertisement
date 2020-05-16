package commands;

public interface Commands {

    int EXIT = 0;
    int LOGIN = 1;
    int REGISTER = 2;
    int IMPORT_USERS = 3;



    int LOGOUT = 0;
    int ADD_NEW_AD = 1;
    int PRINT_MY_ADS = 2;
    int PRINT_ALL_ADS = 3;
    int PRINT_ADS_BY_CATEGORY = 4;
    int PRINT_ALL_ADS_SORT_BY_TITLE = 5;
    int PRINT_ALL_ADS_SORT_BY_DATE  = 6;
    int DELETE_MY_ALL_ADDS = 7;
    int DELETE_AD_BY_iD = 8;
    int IMPORT_ITEMS = 9;
    int EXPORT_ITEMS = 10;


    static void printMainCommands() {
        System.out.println("Please enter " + EXIT + " for EXIT");
        System.out.println("Please enter " + LOGIN + " for LOGIN");
        System.out.println("Please enter " + REGISTER + " for REGISTER");
        System.out.println("Please enter " + IMPORT_USERS + " for IMPORT_USERS");


    }
    static void printUserCommand() {
        System.out.println("Please enter " + LOGOUT + "for LOGOUT");
        System.out.println("Please enter " + ADD_NEW_AD + " for add new AD");
        System.out.println("Please enter " + PRINT_MY_ADS + "for print your all ADs");
        System.out.println("Please enter " + PRINT_ALL_ADS + "for print all  ADs");
        System.out.println("Please enter " + PRINT_ADS_BY_CATEGORY + "for print AD by Category");
        System.out.println("Please enter " + PRINT_ALL_ADS_SORT_BY_TITLE + "for print all AD by Title Sort");
        System.out.println("Please enter " + PRINT_ALL_ADS_SORT_BY_DATE + "for print all AD by Date Sort");
        System.out.println("Please enter " + DELETE_MY_ALL_ADDS + "for delete my all AD ");
        System.out.println("Please enter " + DELETE_AD_BY_iD + "for delete my all AD by ID ");
        System.out.println("Please enter " + IMPORT_ITEMS + "for IMPORT_ITEMS ");
        System.out.println("Please enter " + EXPORT_ITEMS + "for EXPORT_ITEMS ");

    }
}
