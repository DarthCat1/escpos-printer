package com.resonance.printers.connector;


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
}
