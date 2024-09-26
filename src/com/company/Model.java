package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Model {

    private int score;
    private int scoreMax;
    private int mistakes;
    private HashMap<String, String> fileAndPath;
    private String currentAnswerKey;
    private String currentPathValue;
    private final String formatPNG;
    private final String formatJPG;
    private HashMap<String, String> imagePath;

    public Model(){
        score = 0;
        scoreMax = 0;
        mistakes = 0;
        formatPNG = ".png";
        formatJPG = ".jpg";
        currentAnswerKey = "";
        currentPathValue = "";
        imagePath = new HashMap<String,String>();
        fileAndPath = readFileAndPath("txtFileAndPath.txt");
        readFileSelected();
        scoreMax = imagePath.size();
    }

    public HashMap<String, String> readFileAndPath(String path){
        System.out.println("Start reading file "+path);
        HashMap<String, String> file = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File(path));
            while(scanner.hasNextLine())
            {
                String[] couple = scanner.nextLine().split(";");
                try{
                    if(!couple[0].startsWith("$$")){
                        file.put(couple[0], couple[1]);
                        System.out.println(couple[0]+" "+couple[1]);
                    }
                }catch (Exception ignored){}
            }
        } catch (Exception ignored) {}
        System.out.println("End reading file.");
        return file;
    }

    public void readFileSelected()
    {
        List<String> lines = new ArrayList<String>();
        for (String file : fileAndPath.keySet()) {
            System.out.println("Start reading file "+file);
            System.out.println("File "+Paths.get(file)+" find : " + Files.exists(Paths.get(file)));
            try{
                lines = Files.readAllLines(Paths.get(file));
            } catch (IOException ignored){}

            for (String line : lines) {
                if (Files.exists(Paths.get(fileAndPath.get(file) + line + formatPNG))){
                    imagePath.put(line, fileAndPath.get(file) + line + formatPNG);
                } else {
                    imagePath.put(line, fileAndPath.get(file) + line + formatJPG);
                }
                System.out.println(line + " " + imagePath.get(line));
            }
            System.out.println("End reading file.");
        }
    }

    public String randomImagePath(){
        try {
            Object[] tabKeys = imagePath.keySet().toArray();
            int index = (int) (Math.random() * tabKeys.length);
            currentAnswerKey = (String) tabKeys[index];
            currentPathValue =imagePath.get(currentAnswerKey);
            System.out.println("Answer generated : "+ currentAnswerKey);
            System.out.println("Path generated : "+currentPathValue);
        }catch (Exception exception){
            return "Game Over";
        }
        return currentPathValue;
    }

    public int returnScoreMax(){ return scoreMax; }

    public int getScore(){ return score; }

    public void incrementScore(){ score++; }

    public void decrementScore() {
        score--;
    }

    public void incrementMistakes() { mistakes++; }

    public int getMistakes() { return mistakes; }

    public boolean validateEntry(String textWrite) {
        if (textWrite.equalsIgnoreCase(currentAnswerKey)){
            System.out.println("Answer correct");
            incrementScore();
            imagePath.remove(currentAnswerKey);
            return true;
        }else if(textWrite.equalsIgnoreCase("exit")){
            System.exit(1);
        }
        decrementScore();
        incrementMistakes();
        return false;
    }
}
