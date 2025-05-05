package org.idempiere.multicsvimport;

import java.io.InputStream;

public class MultiCSVImportHandler {

    public void processSingleCSV(String filename, InputStream inputStream) {
        filename = filename.toLowerCase();

        try {
            if (filename.contains("product")) {
                CSVReaderUtil.importProducts(inputStream);
            } else if (filename.contains("bpartner")) {
                CSVReaderUtil.importBPartners(inputStream);
            } else if (filename.contains("order")) {
                CSVReaderUtil.importOrders(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
