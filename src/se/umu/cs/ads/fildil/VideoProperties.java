package se.umu.cs.ads.fildil;

import com.google.protobuf.ByteString;
import se.umu.cs.ads.fildil.messages.Chunk;

import java.io.IOException;
import java.io.InputStream;
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
    public static ArrayList<Chunk> toChunks(String src, int size)
            throws Exception {

        ArrayList<Chunk> chunks = new ArrayList<Chunk>();
        List<String> command = new ArrayList<String>();
        command.add("ffmpeg");
        command.add("-i");
        command.add(src);
        command.add("-f");
        command.add("asf");

//        command.add("-vcodec");
//        command.add("mpeg2video");
//        command.add("-acodec");
//        command.add("mp2");
//        command.add("-b:v");
//        command.add("3M");
//        command.add("-b:a");
//        command.add("192k");
//        command.add("-muxrate");
//        command.add("10M");
//        command.add("-f");
//        command.add("asf");  //- video format (TEMP!)
        command.add("-");
//-segment_format mpegts -segment_list_flags +live
        Process  p = new ProcessBuilder(command).start();

        InputStream in = p.getInputStream();
        int n;
        byte[] buf = new byte[size];
        try {
            for(int cnt = 0; (n=in.read(buf)) > -1;cnt++) {
                buf = Arrays.copyOfRange(buf,0,n);

                ByteString bytes = ByteString.copyFrom(buf);
                Chunk chunk = Chunk.newBuilder()
                                    .setBuf(bytes)
                                    .setId(cnt).build();
                chunks.add(chunk);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        in = p.getErrorStream();
        while((n=in.read(buf)) > -1) {
            buf = Arrays.copyOfRange(buf,0,n);
            String str = new String(buf, "UTF-8");
            System.out.println(str);
        }


        return chunks;
    }

}
