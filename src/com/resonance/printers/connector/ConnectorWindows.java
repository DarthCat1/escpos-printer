package com.resonance.printers.connector;

import javax.print.*;
import java.awt.print.PrinterJob;

public class ConnectorWindows implements Connector {

    private PrintService printService;

    public ConnectorWindows (PrintService printService) {
        this.printService = printService;
    }

    public ConnectorWindows (String printServiceName) throws IllegalPrintServiceNameException {
        setPathway(printServiceName);
    }

    public void setPrintService(PrintService printService) {
        this.printService = printService;
    }

    @Override
    public void setPathway(String printServiceName) throws IllegalPrintServiceNameException {
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        PrintService foundService = null;

        for (PrintService service : printServices) {
            if (service.getName().equals(printServiceName)) {
                foundService = service;
                break;
            }
        }
        if (foundService != null) {
            printService = foundService;
            return;
        }

        for (PrintService service : printServices) {
            if (service.getName().compareToIgnoreCase(printServiceName) == 0) {
                foundService = service;
                break;
            }
        }
        if (foundService != null) {
            printService = foundService;
        }

        else {
            throw new IllegalPrintServiceNameException("printServiceName is not found");
        }
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

    public static String[] getPrintServicesNames() {
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        String[] printServicesNames = new String[printServices.length];
        for (int i = 0; i < printServices.length; ++i) {
            printServicesNames[i] = printServices[i].getName();
        }
        return printServicesNames;
    }

}
