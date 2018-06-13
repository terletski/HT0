import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

public class Artist {
    private String name;

    private TreeMap<String, Song> albums;
    private TreeSet<String> albumsNames;

    public Artist(String name) {
        this.name = name;
        this.albums = new TreeMap<>();
        this.albumsNames = new TreeSet<>();
    }

    public Artist() {
        this.name = "Неизвестный артист.";
        this.albums = new TreeMap<>();
        this.albumsNames = new TreeSet<>();
    }

    public String getName() {
        return name;
    }

    public TreeSet<String> getAlbumsNames() {
        return albumsNames;
    }

    public void addSong(Song song) {
        albums.put(song.getAlbum(), song);
        albumsNames.add(song.getAlbum());
    }

    public Song getSong(String title) {
        for (String element : albums.keySet()) {
            if (element.equals(title)) {
                return albums.get(element);
            }
        }
        return null;
    }

    public LinkedList<Song> getSongsFromAlbum(String album) {
        LinkedList<Song> songs = new LinkedList<>();
        for (String element : albums.keySet()) {
            if (element.equals(album)) {
                songs.add(albums.get(element));
            }
        }
        return songs;
    }

    public TreeMap<String, Song> getAlbums() {
        return albums;
    }
}
