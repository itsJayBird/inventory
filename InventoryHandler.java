package homeInventory;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class InventoryHandler {

    private String[] items;
    private int[] count;

    public void setInventoryItem(String item, int num) {
        
        // first open the save file and update inventory
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
    
    public void updateInventoryItem(String item, int num) {
        // first open the save file and update inventory
        openInventory();
        // then update inventory item
        // this will find the index of the item you want to update
        int findItem = findItem(item);
        // then update the count accordingly
        count[findItem + 1] = num;
        // then save file to update inventory
        saveInventory(items, count);
    }
    
    public void removeItem(String item) {
        // first open inventory
        openInventory();
        // then look for item you want to remove
        int findItem = findItem(item);
        // split the item array into two arrays
        String[] firstHalf = new String[findItem]; // array large enough to fit all items up to the one you want to remove
        String[] secondHalf = new String[items.length - (findItem + 1)]; // array large enough to fit the remaining items
        // this adds the first half of the array to firstHalf
        for(int i = 0; i < (findItem + 1); i++) {
            firstHalf[i] = items[i];
        }
        // now add second half of the array to the secondHalf
        for(int i = 0; i < secondHalf.length; i++) {
            secondHalf[i] = items[i + (findItem + 2)];
        }
        // now create a new array that will hold the new information
        String[] updatedItems = new String[items.length - 1];
        // add first half to the new array
        for(int i = 0; i < firstHalf.length; i++) {
            updatedItems[i] = firstHalf[i];
        }
        // add second half to the new array
        for(int i = 0; i < secondHalf.length; i++) {
            updatedItems[i + (firstHalf.length + 1)] = secondHalf[i];
        }
        // do the same for count
        int[] firstInts = new int[findItem];
        int[] secondInts = new int[count.length - (findItem + 1)];
        for(int i = 0; i < (findItem + 1); i++) {
            firstInts[i] = count[i];
        }
        for(int i = 0; i < secondHalf.length; i++) {
            secondInts[i] = count[i + (findItem + 2)];
        }
        int[] updatedCount = new int[count.length - 1];
        for(int i = 0; i < firstInts.length; i++) { 
            updatedCount[i] = firstInts[i];
        }
        for(int i = 0; i < secondInts.length; i++) {
            updatedCount[i + (firstInts.length + 1)] = secondInts[i];
        }
        // commit to memory
        items = updatedItems;
        count = updatedCount;
        // save to file
        saveInventory(items, count);
    }
    
    private int findItem(String item) {
        int findItem = 0;
        while(items[findItem] != item) {
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
