package webapp;

import java.io.File;
import java.io.IOException;
public class FileDIr {

    public static void main(String[] args) throws IOException {
        File currentDir = new File("."); // current directory
        displayDirectoryContent(currentDir);
    }

    public static void displayDirectoryContent(File dir) {
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("directory:" + file.getCanonicalPath());
                    displayDirectoryContent(file);
                } else {
                    System.out.println("     file:" + file.getCanonicalPath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
