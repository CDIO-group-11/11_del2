package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ValueReader_for_ekstra_turn{
    private static ArrayList<Boolean> extraTurn = new ArrayList<Boolean>();

    public static void loadValues(){
        try (Scanner scan = new Scanner(new File("Values/Extra_turn_tiles.csv"))) {
            while(scan.hasNextLine()){
                String[] extraturntiles = scan.nextLine().replace(" ", "").split(",");
                for (int i = 0; i < extraturntiles.length; i++) {
                    extraTurn.add(Boolean.parseBoolean(extraturntiles[i]));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
    }
    public static Boolean hasExtraTurn(int TileNumber){
        return extraTurn.get(TileNumber - 1);
    }
}