package com.resonance.printers.connector;

import javax.print.*;

public class ConnectorWindows implements Connector {

    private PrintService printService;

    public ConnectorWindows (PrintService printService) {
        this.printService = printService;
    }

    public void setPrintService(PrintService printService) {
        this.printService = printService;
    }

    @Override
    public void setPathway(String pathway) {
    }

    @Override
    public String getPathway() {
        return printService.getName();
    }

    public PrintService getPrintService() {
        return printService;
    }

    @Override
    public void printBuffer(byte[] buffer) throws PrintException {
        DocFlavor df = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc d = new SimpleDoc(buffer, df, null);
        DocPrintJob job = printService.createPrintJob();
        job.print(d, null);
    }
}
