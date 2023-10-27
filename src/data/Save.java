package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import app.Player;
import lang.Lang;
import lang.Language.LanguageCode;

public class Save {

  public static void state(Player[] players, Scanner scan, LanguageCode lang) {
    File file = new File("data/" + scan.nextLine() + ".state");
    String  out = "";
    for (int i = 0; i < players.length; i++) {
      out += players[i].toHash(lang);
      out += i + 1 < players.length ? "\n" : "";
    }
    while(true){
      try {
        file.createNewFile();
        FileWriter stateWriter = new FileWriter(file);
        stateWriter.write(out);;
        stateWriter.close();
        break;
      } catch (Exception e) {
        Lang.error(e);
        System.out.print("please choose different file name:");
        file = new File("data/" + scan.nextLine() + ".state");
        System.out.println();
      }
    }
  }
  public static Player[] load(LanguageCode lang, File file){
    String in = "";
    try (Scanner scan = new Scanner(file)) {
      while (scan.hasNextLine()) {
        in += scan.nextLine() + " ";
      }
    } catch (FileNotFoundException e) {
      Lang.error(e);
    }
    String[] longs = in.split(" ");
    long[] hash = new long[longs.length];
    for (int i = 0; i < longs.length; i++) {
      hash[i] = Long.parseLong(longs[i],16);
    }
    return Player.fromHash(hash, lang);
  }

}
