package org.idempiere.multicsvimport;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.compiere.model.MProduct;
import org.compiere.model.MBPartner;
import org.compiere.model.MOrder;
import org.compiere.util.Env;

public class CSVReaderUtil {

    public static void importProducts(InputStream inputStream) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            MProduct product = new MProduct(Env.getCtx(), 0, null);
            product.setValue(fields[0]);
            product.setName(fields[1]);
            product.saveEx();
        }
    }

    public static void importBPartners(InputStream inputStream) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            MBPartner bp = new MBPartner(Env.getCtx(), 0, null);
            bp.setValue(fields[0]);
            bp.setName(fields[1]);
            bp.saveEx();
        }
    }

    public static void importOrders(InputStream inputStream) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            MOrder order = new MOrder(Env.getCtx(), 0, null);
            order.setDocumentNo(fields[0]);
            order.saveEx();
        }
    }
}
