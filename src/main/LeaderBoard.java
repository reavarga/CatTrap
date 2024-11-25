package main;

import javax.swing.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeaderBoard {
    private final String path = "data/leaderboard.txt"; // path of the txt
    private final String delimiter = ";"; // separator of the data

    /**
     * creates a new file
     * 
     * @throws IOException
     */
    public LeaderBoard() throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();
        f.createNewFile();
    }


    /**
     * Writes the new entry into the file,
     * if it matches with an entry from the file just updates
     * 
     * @param name         player name
     * @param difficulty   game difficulty
     * @param algorithm    penguin algorithm
     * @param maxScore     maximum rounds the player won
     * @param currentScore currently how much it won
     * @throws IOException
     */
    public void newEntry(String name, Difficulty difficulty, Algorithm algorithm, int maxScore, int currentScore)
            throws IOException {
        Path curPath = Paths.get(path);
        List<String> lines = Files.readAllLines(curPath);
        String regex = "^" + Pattern.quote(name) + Pattern.quote(delimiter)
                + Pattern.quote(difficulty.toString()) + Pattern.quote(delimiter) +
                Pattern.quote(algorithm.toString()) + Pattern.quote(delimiter)
                + "(\\d+)" + Pattern.quote(delimiter) + "(\\d+)$";
        Pattern pattern = Pattern.compile(regex);
        boolean match = false;
        String line = name + delimiter + difficulty + delimiter + algorithm + delimiter + maxScore + delimiter
                + currentScore;
        for (int i = 0; i < lines.size(); i++) {
            Matcher matcher = pattern.matcher(lines.get(i));
            if (matcher.matches()) {
                match = true;
                int maxOScore = Integer.parseInt(matcher.group(1));
                // int currentOScore = Integer.parseInt(matcher.group(2));
                if (maxOScore > maxScore) {
                    lines.set(i, name + delimiter + difficulty + delimiter + algorithm + delimiter + maxOScore
                            + delimiter + currentScore);
                } else {
                    lines.set(i, line);
                }
                Files.write(curPath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING,
                        StandardOpenOption.WRITE);
                break;
            }
        }
        if (!match) {
            FileWriter fw = new FileWriter(path, true);
            fw.write("\n"+line);
            fw.close();
        }

    }

    /**
     * Loads an enrty and then makes a game basod on the parameters for the player
     * all of the parameters come from the loadButton from the frame
     * if the entered values do not have a match shows a MessageDialog
     * 
     * @param name      the player name
     * @param diff      the difficulty
     * @param algorithm the algo
     * @param f         the frame it all comes
     * @throws IOException
     */
    public void readEntry(String name, Difficulty diff, String algorithm, Frame f) throws IOException {
        Path curPath = Paths.get(path);
        List<String> lines = Files.readAllLines(curPath);
        String regex = "^" + name + Pattern.quote(delimiter) + diff + Pattern.quote(delimiter) + algorithm
                + Pattern.quote(delimiter) + "(\\d+)" + Pattern.quote(delimiter) + "(\\d+)$";
        // Capture curScore (numeric)
        Pattern pattern = Pattern.compile(regex);
        for (String i : lines) {
            Matcher matcher = pattern.matcher(i);
            if (matcher.matches()) {
                int maxScore = Integer.parseInt(matcher.group(1));
                int currentScore = Integer.parseInt(matcher.group(2));
                State.setDifficulty(diff);
                f.setCurrentScore(currentScore);
                f.setMaxScore(maxScore);
                f.startGame(f.algorithms.get(algorithm));
                return;

            }
        }
        JOptionPane.showMessageDialog(
                null,
                "Not found",
                "Error",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
