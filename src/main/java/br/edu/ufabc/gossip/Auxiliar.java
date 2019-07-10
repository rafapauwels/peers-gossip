package br.edu.ufabc.gossip;

import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Auxiliar {
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  private static final DateTimeFormatter DT_FORMAT = DateTimeFormatter.ofPattern("ddMMyyyy HH:mm:ss.SSSS");

  public static String filetimeToString(FileTime filetime) {
    return filetime
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
            .format(DT_FORMAT);
  }

  public static FileTime stringTofiletime(String string) {
    return FileTime
            .from(LocalDateTime
                  .parse(string, DT_FORMAT)
                  .atZone(ZoneId.systemDefault())
                  .toInstant());
  }
}