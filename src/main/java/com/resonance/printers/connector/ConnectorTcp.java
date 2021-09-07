package com.resonance.printers.connector;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectorTcp implements Connector{
    private String pathway;


    public ConnectorTcp(String ip, int port) {
        pathway = ip + ":" + port;
    }

    @Override
    public String getPathway() {
        return pathway;
    }

    @Override
    public void setPathway(String pathway) {
        this.pathway = pathway;
    }

    @Override
    public void printBuffer(byte[] buffer) throws IOException {
        Socket socket = new Socket(pathway.substring(0, pathway.indexOf(':')),
                Integer.parseInt(pathway.substring(pathway.indexOf(':') + 1)));
        DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
        oos.write(buffer);
        oos.close();
    }
}
