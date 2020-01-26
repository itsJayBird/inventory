package homeInventory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class InventoryHandler {

    private String[] items = {};
    private int[] count = {};

    public void setInventoryItem(String item, int num) {
        // first check if save has been created
        File checkSave = new File("/home/jaybird/workspace/HomeInventory/inventory.sav");
        boolean exists = checkSave.exists();
        if(exists==false) saveInventory(items, count);
        openInventory();
        // then update the inventory with new item
        String[] tempItems = new String[items.length + 1]; // temporary storage for updated inventory
        for(int i = 0; i < items.length; i++) {
            tempItems[i] = items[i]; // copies old inventory over to temp array
        }
        tempItems[items.length] = item; // adds new item to new inventory array
        items = tempItems; // overwrites old array

        // same for count array
        int[] tempCount = new int[count.length + 1];
        for(int i = 0; i < count.length; i++) {
            tempCount[i] = count[i];
        }
        tempCount[count.length] = num;
        count = tempCount;
        // save inventory
        saveInventory(items, count);
    } 

    public void updateInventoryItem(String item, int num, String func) {
        // first open the save file and update inventory
        openInventory();
        // then update inventory item
        int findItem = findItem(item);
        // then update the count accordingly
        if(func.contains("add")==true) {
            count[findItem ] = count[findItem] + num;
        } else {
            count[findItem] = count[findItem] - num;
        }
        // then save file to update inventory
        saveInventory(items, count);
    }

    public void removeItem(String item) {
        // first open inventory
        openInventory();
        // then look for item you want to remove
        int findItem = findItem(item);
        // split items into two arrays
        String[] tempItemStart = Arrays.copyOfRange(items, 0, findItem); // first half of the array
        // check if there is anything after this item
        boolean hasError = false;
        String[] tempItemEnd = {};
        try {
            tempItemEnd = Arrays.copyOfRange(items, (findItem + 1), items.length);
        }
        catch(ArrayIndexOutOfBoundsException e) {
            hasError = true;
        }
        if(hasError == false) tempItemEnd = Arrays.copyOfRange(items, (findItem + 1), items.length);
        // create new array to hold items
        String[] updatedItems = new String[items.length - 1];
        // copy in first half of array
        for(int i = 0; i < tempItemStart.length; i++) {
            updatedItems[i] = tempItemStart[i];
        }
        // check if there is a second half of the array
        if(hasError == false) {
            // copy it if there is
            for(int i = 0; i < tempItemEnd.length; i++) {
                updatedItems[findItem] = tempItemEnd[i];
            }
        }
        // do the same for count list
        // split items into two arrays
        int[] tempCountStart = Arrays.copyOfRange(count, 0, findItem); // first half of the array
        // check if there is anything after this item
        hasError = false;
        int[] tempCountEnd = {};
        try {
            tempCountEnd = Arrays.copyOfRange(count, (findItem + 1), count.length);
        }
        catch(ArrayIndexOutOfBoundsException e) {
            hasError = true;
        }
        if(hasError == false) tempCountEnd = Arrays.copyOfRange(count, (findItem + 1), count.length);
        // create new array to hold items
        int[] updatedCount = new int[count.length - 1];
        // copy in first half of array
        for(int i = 0; i < tempCountStart.length; i++) {
            updatedCount[i] = tempCountStart[i];
        }
        // check if there is a second half of the array
        if(hasError == false) {
            // copy it if there is
            for(int i = 0; i < tempCountEnd.length; i++) {
                updatedCount[findItem] = tempCountEnd[i];
            }
        }
        // commit to memory
        items = updatedItems;
        count = updatedCount;
        saveInventory(items, count);
    }

    private int findItem(String item) {
        int findItem = 0;
        while(items[findItem].contains(item)==false) {
            findItem++;
        }
        return findItem;
    }

    public void returnInventory() {        
        openInventory(); // open inventory
        for(int i = 0; i < items.length; i++) {
            System.out.println(items[i] + ": " + count[i]);
        }
    }

    private void saveInventory(String[] inventory, int[] nums) {
        try {
            FileOutputStream saveFile = new FileOutputStream("inventory.sav"); // creates save file
            ObjectOutputStream save = new ObjectOutputStream(saveFile); // objects to put into save file
            save.writeObject(inventory); // saving objects
            save.writeObject(nums);
            save.close();
        } catch(Exception exc) {
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }

    private void openInventory() {      
        try {
            FileInputStream saveFile = new FileInputStream("inventory.sav"); // open save file
            ObjectInputStream save = new ObjectInputStream(saveFile); // objects to pull from save file
            items = (String[]) save.readObject(); // restoring objects
            count = (int[]) save.readObject();
            save.close();
        } catch(Exception exc){
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }
}
