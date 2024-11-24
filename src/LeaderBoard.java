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
    private final String path="data/leaderboard.txt";
    private final String delimiter=";";

    LeaderBoard() throws IOException {
        File f=new File(path);
        f.getParentFile().mkdirs();
        f.createNewFile();
    }
    public void newEntry(String name, Difficulty difficulty, int maxScore, int currentScore) throws IOException {
        Path curPath = Paths.get(path);
        List<String> lines = Files.readAllLines(curPath);
        String regex = "^" + Pattern.quote(name) + Pattern.quote(delimiter)
                + Pattern.quote(difficulty.toString()) + Pattern.quote(delimiter)
                + "(\\d+)" + Pattern.quote(delimiter) + "(\\d+)$";
        Pattern pattern=Pattern.compile(regex);
        boolean match=false;
        String line = name + delimiter + difficulty + delimiter + maxScore + delimiter + currentScore + "\n";
        for(String i:lines){
            Matcher matcher=pattern.matcher(i);
            if (matcher.matches()) {
                match=true;
                int maxOScore = Integer.parseInt(matcher.group(1));
                int currentOScore = Integer.parseInt(matcher.group(2));
                if(maxOScore>maxScore){
                    i=name+delimiter+difficulty+delimiter+maxOScore+delimiter+currentScore+"\n";
                }else{
                    i= line;
                }
                Files.write(curPath, lines, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
                break;
            }
        }
        if(!match){
            File f=new File("data/leaderboard.txt");
            FileWriter fw = new FileWriter(f,true);
            fw.write(line);
            fw.close();
        }


    }

    public void readEntry(String name, Frame f) throws IOException {
        Path curPath = Paths.get(path);
        List<String> lines = Files.readAllLines(curPath);
        String regex = "^"+name + Pattern.quote(delimiter) + "(.*)" + Pattern.quote(delimiter) + "(\\d+)" + Pattern.quote(delimiter) + "(\\d+)$";
        // Capture curScore (numeric)
        Pattern pattern=Pattern.compile(regex);
        for(String i:lines){
            Matcher matcher=pattern.matcher(i);
            if (matcher.matches()) {
                int maxScore = Integer.parseInt(matcher.group(2));
                int currentScore = Integer.parseInt(matcher.group(3));
                State.setDifficulty(Difficulty.valueOf(matcher.group(1)));
                f.setCurrentScore(currentScore);
                f.setMaxScore(maxScore);
                f.startGame();
                return;

            }
        }
        JOptionPane.showMessageDialog(
                null,                    // Parent component (null means no parent)
                "Not found",                 // The message to display
                "Error",                   // The title of the dialog box
                JOptionPane.INFORMATION_MESSAGE // Type of message (information icon)
        );
    }
}
