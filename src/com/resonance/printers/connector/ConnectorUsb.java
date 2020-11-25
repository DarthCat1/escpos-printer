package com.resonance.printers.connector;


public class ConnectorUsb implements Connector {

    private String pathway;

    public ConnectorUsb (String pathway) {
        this.pathway = pathway;
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
