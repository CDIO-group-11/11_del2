package data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import app.Player;

public class Save {

  public static void state(Player[] players, File file) {
    long p0 = players[0].toHash();
    long p1 = players[1].toHash();
    try {
      FileWriter stateWriter = new FileWriter(file);
      file.createNewFile();
      stateWriter.write(""+ p0 + "\n" + p1);;
      stateWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
