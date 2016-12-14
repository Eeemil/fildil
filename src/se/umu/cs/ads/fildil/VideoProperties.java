package se.umu.cs.ads.fildil;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by c12ton on 12/12/16.
 */
public class VideoProperties {

    /**
     * Split video into byteArray streams by given time
     * @param src
     * @return split parts of the video.
     * @throws Exception
     */
    public static ArrayList<byte[]> toChunks(String src, int size)
            throws Exception {

        ArrayList<byte[]> chunks = new ArrayList<byte[]>();
        List<String> command = new ArrayList<String>();
        command.add("ffmpeg");
        command.add("-i");
        command.add(src);
        command.add("-vcodec");
        command.add("mpeg2video");
        command.add("-acodec");
        command.add("mp2");
        command.add("-b:v");
        command.add("3M");
        command.add("-b:a");
        command.add("192k");
        command.add("-muxrate");
        command.add("10M");
        command.add("-f");
        command.add("asf");  //- video format (TEMP!)
        command.add("-");

        Process  p = new ProcessBuilder(command).start();
        InputStream in = p.getInputStream();

        int n;
        byte[] buf = new byte[size];
        try {
            while((n=in.read(buf)) > -1) {
                byte[] chunk = Arrays.copyOfRange(buf,0,n);
                chunks.add(chunk);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return chunks;
    }

//    /**
//     *
//     * @param input
//     * @return
//     * @throws IOException
//     */
//    private static ByteArrayInputStream toByteArrayInputStream(InputStream input)
//            throws IOException {
//
//        System.out.println("starting to write bytes");
//        int l;
//        byte[]buf = new byte[1024];
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        while((l=input.read(buf)) != -1) {
////            System.out.println("asdads");
//            out.write(buf,0,l);
//        }
//
//        System.out.println("Size " + out.size());
//        return new ByteArrayInputStream(out.toByteArray());
//    }


//    /**
//     * @param src is the address for the video
//     * @return time in seconds of a video
//     */
//    public static int getTime(String src) {
//        int time = 0;
//        Process p = null;
//        try {
//            p  = new ProcessBuilder("ffprobe","-v","error",
//                                            "-show_entries","format=duration",
//                                            "-of","default=noprint_wrappers=1:"
//                                            + "nokey=1",src).start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            String s = IOUtils.toString(p.getInputStream());
//            s = s.replace("\n","");
//            time = (int) Double.parseDouble(s);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return time;
//    }


}
