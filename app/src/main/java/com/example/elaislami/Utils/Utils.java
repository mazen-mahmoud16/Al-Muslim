package com.example.elaislami.Utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/*
 * Here is the class to manage JSON files
 */
public class Utils {

  /*
   * Here is a static function that returns a json string from a file
   */
  public static String getJsonFromAssets(Context context, String fileName) {
    String jsonString;
    try {
      InputStream inputStream = context.getAssets().open(fileName);

      int size = inputStream.available();
      byte[] buffer = new byte[size];
      inputStream.read(buffer);
      inputStream.close();

      jsonString = new String(buffer, StandardCharsets.UTF_8);
    }
    // If exception is thrown
    catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    // Return the json string
    return jsonString;
  }
}