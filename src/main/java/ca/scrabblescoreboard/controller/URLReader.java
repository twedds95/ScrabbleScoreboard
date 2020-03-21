package ca.scrabblescoreboard.controller;

import java.io.*;
import java.net.*;


public class URLReader {

    public static boolean checkValidWord(String wordToCheck) throws Exception {


        String key =  "7.304453775081076e29";
        String scrabbleAPI ="https://www.wordgamedictionary.com/api/v1/references/scrabble/";
        URL scrabbleDictionary = new URL(scrabbleAPI + wordToCheck + "?key=" + key);

        try {
            HttpURLConnection sDConnection = (HttpURLConnection) scrabbleDictionary.openConnection();

            sDConnection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(sDConnection.getInputStream()));
            String inputLine = "";
            while (in.readLine() != null) {
                inputLine += in.readLine();
            }
            in.close();
            if (inputLine.contains("<scrabble>1</scrabble>")){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
