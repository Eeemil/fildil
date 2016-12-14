package se.umu.cs.ads.fildil;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by c12ton on 12/12/16.
 */
public class VideoProperties {

    /**
     * Split video into byteArray streams by given time
     * @param src
     * @param seconds the duration for each part.
     * @return split parts of the video.
     * @throws Exception
     */
    public static ArrayList<ByteArrayInputStream> split(String src, int seconds)
            throws Exception {

        Process p = null;
        int movieLength = getTime(src);
        int parts = (movieLength/seconds);

        ArrayList<ByteArrayInputStream> splits = new ArrayList();

        //Try to split it at least once
        for(int i = 0; i < parts; i++) {
            int start =  (i*seconds);
            int end   = start + seconds;

            System.out.println("Start at: " + start + "s" + "End at: " + end + "s");
            p = new ProcessBuilder("ffmpeg","-i",src,"-ss",
                                    Integer.toString(start),"-t",
                                    Integer.toString(end),"-f","asf","-").start();

//            System.out.println("sadad");
            splits.add(toByteArrayInputStream(p.getInputStream()));
            p.waitFor();
        }


        return splits;
    }

    /**
     *
     * @param input
     * @return
     * @throws IOException
     */
    private static ByteArrayInputStream toByteArrayInputStream(InputStream input)
            throws IOException {

        System.out.println("starting to write bytes");
        int l;
        byte[]buf = new byte[1024];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while((l=input.read(buf)) != -1) {
//            System.out.println("asdads");
            out.write(buf,0,l);
        }

        System.out.println("Size " + out.size());
        return new ByteArrayInputStream(out.toByteArray());
    }


    /**
     * @param src is the address for the video
     * @return time in seconds of a video
     */
    public static int getTime(String src) {
        int time = 0;
        Process p = null;
        try {
            p  = new ProcessBuilder("ffprobe","-v","error",
                                            "-show_entries","format=duration",
                                            "-of","default=noprint_wrappers=1:"
                                            + "nokey=1",src).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String s = IOUtils.toString(p.getInputStream());
            s = s.replace("\n","");
            time = (int) Double.parseDouble(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return time;
    }


}
