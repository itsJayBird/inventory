package homeInventory;

import java.util.Scanner;

public class Menu {

    public void showMenu() {
        Menu a = new Menu();
        String input = "";
        do {
            System.out.println(a.printMenu()); // first show the menu
            input = a.takeString();
            input = input.replaceAll("\\s+","");
            input = input.toUpperCase();
            a.selection(input);
        } while(input.contains("CLS")==false);
    }

    private void selection(String input) {
        switch(input) {
        case "ADD":
            addItem();
            break;
        case "SHW":
            showInventory();
            break;
        case "UPD":
            updateCount();
            break;
        case "RMV":
            removeItem();
            break;
        }
    }

    private void removeItem() {
        InventoryHandler now = new InventoryHandler();
        System.out.println("Which item are we removing?");
        String item = takeString();
        now.removeItem(item);
    }

    private void updateCount() {
        InventoryHandler now = new InventoryHandler();
        System.out.println("Which item are we updating?");
        String item = takeString();
        System.out.println("Are we going to (add)/(use)?");
        String func = takeString();
        System.out.println("How many " + item + "(s) " + "are we going to " + func);
        int num = takeNum();
        now.updateInventoryItem(item, num, func);
    }

    private void addItem() {
        InventoryHandler now = new InventoryHandler();
        System.out.println("What item are we adding?");
        String item = takeString();
        System.out.println("How many " + item + "(s)" + "are we adding?");
        int num = takeNum();
        now.setInventoryItem(item, num);
    }

    private void showInventory() {
        InventoryHandler now = new InventoryHandler();
        now.returnInventory();
    }

    @SuppressWarnings("resource")
    private String takeString() {
        Scanner in = new Scanner(System.in);
        in.useDelimiter("\n");
        String input = in.next();
        return input;
    }

    @SuppressWarnings("resource")
    private int takeNum() {
        Scanner in = new Scanner(System.in);
        int input = in.nextInt();
        return input;
    }

    private String printMenu() {
        String menu =      "---------------------------------------------------\n"
                + "|         add item to inventory - \"add\"           |\n"
                + "|         update item count     - \"upd\"           |\n"
                + "|         remove item           - \"rmv\"           |\n"
                + "|         show inventory        - \"shw\"           |\n"
                + "|         close                 - \"cls\"           |\n"
                + "---------------------------------------------------";
        return menu;
    }

}
