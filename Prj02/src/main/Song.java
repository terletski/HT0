import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Song implements Comparable<Song> {
    private String fileLocation;
    private String title;
    private String artist;
    private String album;
    private double duration;
    private int minutesDuration;
    private int secondsDuration;
    private String checkSum;

    public Song(String fileLocation) {
        this.fileLocation = fileLocation;
        InputStream input;
        ContentHandler handler;
        Metadata metadata = null;
        Parser parser;
        ParseContext parseCtx;
        try {
            input = new FileInputStream(new File(fileLocation));
            handler = new DefaultHandler();
            metadata = new Metadata();
            parser = new Mp3Parser();
            parseCtx = new ParseContext();
            parser.parse(input, handler, metadata, parseCtx);
            input.close();
        } catch (IOException | SAXException | TikaException e) {
            e.printStackTrace();
        }

        title = metadata.get("title");
        artist = metadata.get("xmpDM:artist");
        album = metadata.get("xmpDM:album");
        duration = Double.parseDouble(metadata.get("xmpDM:duration"));
        minutesDuration = (int) (duration / 60000);
        secondsDuration = (int) (duration % 60000) / 1000;
        this.checkSum = checkSum(fileLocation);
        if (title.equals("")) {
            title = "Untitled";
        }
        if (artist.equals("")) {
            artist = "Неизвестный артист";
        
        }
        if (album.equals("")) {
            album = "Неизвестный альбом";
        }
    }

    public String getTitle() {
        return title;
    }
     
    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public double getDuration() {
        return duration;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public int getMinutesDuration() {
        return minutesDuration;
    }

    public int getSecondsDuration() {
        return secondsDuration;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public static String checkSum(String fileName) {
        String algorithm = "SHA-1";
        try {
            // Получаем контрольную сумму для файла в виде массива байт
            MessageDigest md = MessageDigest.getInstance(algorithm);
            FileInputStream fis = new FileInputStream(fileName);
            byte[] dataBytes = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(dataBytes)) > 0) {
                md.update(dataBytes, 0, bytesRead);
            }
            byte[] mdBytes = md.digest();

            // Переводим контрольную сумму в виде массива байт в шестнадцатеричное представление
            StringBuilder sb = new StringBuilder();
            for (byte mdByte : mdBytes) {
                sb.append(Integer.toString((mdByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public int compareTo(Song o) {
        return artist.compareTo(o.getArtist());
    }
}
