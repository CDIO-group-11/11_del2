package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import lang.Lang;

public class ValueReader{
    private static ArrayList<Integer> nums = new ArrayList<Integer>();

    public static void loadValues(){
        try (Scanner scan = new Scanner(new File("Values/TileValues.csv"))) {
            while(scan.hasNextLine()){
                String[] numbers = scan.nextLine().replace(" ", "").split(",");
                for (int i = 0; i < numbers.length; i++) {
                    nums.add(Integer.parseInt(numbers[i]));
                }
            }
        } catch (FileNotFoundException e) {
            Lang.error(e);
        } catch (NumberFormatException e){
            Lang.error(e);
        }
    }
    public static int getTileValue(int TileNumber){
        return nums.get(TileNumber - 1);
    }
}