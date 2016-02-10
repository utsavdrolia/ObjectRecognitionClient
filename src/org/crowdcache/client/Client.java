package org.crowdcache.client;

import org.zeromq.ZMQ;

/**
 * Created by utsav on 2/9/16.
 */
public abstract class Client
{
    ZMQ.Context context;
    private ZMQ.Socket socket;

    /**
     * Address to connect to
     * @param address
     */
    public Client(String address)
    {
        context = ZMQ.context(1);
        socket = context.socket(ZMQ.REQ);
        socket.connect("tcp://" + address);
    }

    /**
     * Send request to server and receive reply
     * @param data
     * @return
     */
    protected byte[] request(byte[] data)
    {
        socket.send(data);
        return socket.recv();
    }

    public void close()
    {
        context.term();
    }
}

