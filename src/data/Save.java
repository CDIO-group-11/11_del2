package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import app.Player;
import lang.Language.LanguageCode;

public class Save {

  public static void state(Player[] players, File file, LanguageCode lang) {
    String  out = "";
    for (int i = 0; i < players.length; i++) {
      out += players[i].toHash(lang);
      out += i + 1 < players.length ? "\n" : "";
    }
    try {
      FileWriter stateWriter = new FileWriter(file);
      file.createNewFile();
      stateWriter.write(out);;
      stateWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public static Player[] load(LanguageCode lang, File file){
    String in = "";
    try (Scanner scan = new Scanner(file)) {
      while (scan.hasNextLine()) {
        in += scan.nextLine() + " ";
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    String[] longs = in.split(" ");
    long[] hash = new long[longs.length];
    for (int i = 0; i < longs.length; i++) {
      hash[i] = Long.parseLong(longs[i],16);
    }
    return Player.fromHash(hash, lang);
  }

}
