package se.umu.cs.ads.fildil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by c12ton on 12/13/16.
 */
public class Split implements Serializable {
    private String id;
    private int part;
    private ByteArrayInputStream chunk;

    public Split(String id, int part, ByteArrayInputStream chunk) {
        this.part = part;
        this.id = id;
        this.chunk = chunk;
    }

}
