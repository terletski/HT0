import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Укажите корректную дирректорию!");
            return;
        }
        String extension = "mp3";
        LinkedList<String> locations = new LinkedList<>();

        TreeMap<Song, String> songs = new TreeMap<>();
        LinkedList<Artist> artists = new LinkedList<>();

        HashMap<String, Song> checkSums = new HashMap<>();
        HTMLWriter htmlWriter = new HTMLWriter();
        TracksLog tracksLog = new TracksLog();
        Collections.addAll(locations, args);
        for (String location : locations) {
            LinkedList<Song> tmp = filesSearch(location, extension);
            if (tmp.isEmpty()) {
                System.out.println("Не существует MP3 файла в " + location);
            }
            for (Song element : tmp) {
                songs.put(element, element.getArtist());
            }
        }

        if (songs.isEmpty()) {
            System.out.println("Список песен пуст.");
            return;
        }
        for (Song s : songs.keySet()) {
            if (checkSums.containsKey(s.getCheckSum())) {
                tracksLog.addCopy(s.getFileLocation());
                tracksLog.addCopy(checkSums.get(s.getCheckSum()).getFileLocation());
            }
            checkSums.put(s.getCheckSum(), s);
            if (artists.contains(s.getArtist())) {
                artists.get(artists.indexOf(s.getArtist())).addSong(s);
            } else {
                artists.add(new Artist(s.getArtist()));
                artists.getLast().addSong(s);
            }
        }
        
        for (Artist element : artists) {
            htmlWriter.addArtist(element);
        }
        
        tracksLog.getCopiesLog();
        if (htmlWriter.createHTML()) {
            System.out.println("Документ сохранён.");
        } else {
            System.out.println("Документ не сохранён.");
        }
    }
    
    public static LinkedList filesSearch(String root, String extension) {
        File rootDir = new File(root);
        LinkedList result = new LinkedList();
        Queue<File> fileTree = new PriorityQueue<>();

        Collections.addAll(fileTree, rootDir.listFiles());

        while (!fileTree.isEmpty()) {
            File currentFile = fileTree.remove();
            if (currentFile.isDirectory()) {
                Collections.addAll(fileTree, currentFile.listFiles());
            } else if (currentFile.getName().contains(extension)) {
                result.add(new Song(currentFile.getAbsolutePath()));
            }
        }
        return result;
    }
}

