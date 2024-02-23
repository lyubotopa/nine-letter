package com.ioncannon.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class FileReader {

    public ArrayList<String> readRemoteFile(String urlString)  {

        ArrayList<String> result = new ArrayList<String>();
        try {
            // Create a URL object
            URL url = new URL(urlString);

            // Open a connection to the URL
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            // Read each line from the file until the end is reached
            while ((line = reader.readLine()) != null) {
                // Process each line (in this example, we'll just print it)
                result.add(line);
            }

            // Close the reader
            reader.close();
        } catch (IOException e) {
            System.err.println("Failed to read dictionary file!\n"+e.getMessage());
        }
        return result;
    }
}
