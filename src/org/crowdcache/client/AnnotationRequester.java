package org.crowdcache.client;

import org.zeromq.ZMQ;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/**
 * Created by utsav on 2/9/16.
 */
public class AnnotationRequester extends Client
{
    ByteBuffer buffer = ByteBuffer.allocate(80 * 1024);
    /**
     * Address to connect to
     *
     * @param address
     */
    public AnnotationRequester(String address)
    {
        super(address);
    }

    /**
     * Request annotation for this image
     * @param imagepath
     * @return
     * @throws IOException
     */
    public String requestAnnotation(String imagepath) throws IOException
    {
        buffer.clear();
        FileInputStream fis = new FileInputStream(imagepath);
        FileChannel fc = fis.getChannel();
        fc.read(buffer);
        buffer.flip();
        fc.close();
        byte[] data = buffer.array();
        System.out.println("Requesting");
        return new String(request(data), ZMQ.CHARSET);
    }

    public static void main(String args[]) throws IOException
    {
        if (args.length > 0)
        {
            String inputFile = args[0];
            AnnotationRequester ar = new AnnotationRequester("192.168.1.13:50505");
            System.out.println(ar.requestAnnotation(inputFile));
            ar.close();
        }
    }
}


