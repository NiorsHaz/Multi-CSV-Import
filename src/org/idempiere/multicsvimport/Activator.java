package org.idempiere.multicsvimport;

import org.compiere.util.DB;
import org.compiere.util.Env;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("✅ MultiCSVImport plugin started");
        try {
        	if (Class.forName("org.idempiere.multicsvimport.WMultiCSVImport") == null) {
        	    throw new ClassNotFoundException("Form class WMultiCSVImportForm not found.");
        	}
            createFormAndMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("🛑 MultiCSVImport plugin stopped");
    }

    private void createFormAndMenu() throws SQLException {

        Properties ctx = Env.getCtx();
        
    	int clientId = Env.getAD_Client_ID(ctx);
    	int orgId = Env.getAD_Org_ID(ctx);
    	int userId = Env.getAD_User_ID(ctx);

        // Step 1: Create AD_Form if it doesn't exist
        int formId = getFormIdIfExists(ctx, "Multi CSV Import");
        if (formId == -1) {
            formId = DB.getNextID(ctx, "AD_Form", null);
            String formUU = UUID.randomUUID().toString();
            String formSql = "INSERT INTO AD_Form (AD_Form_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, " +
                    "Updated, UpdatedBy, Name, Description, AccessLevel, Classname, EntityType, AD_Form_UU) " +
                    "VALUES (?, ?, ?, 'Y', now(), ?, now(), ?, ?, ?, '3', ?, 'D', ?)";

            try (PreparedStatement ps = DB.prepareStatement(formSql, null)) {
                ps.setInt(1, formId);
                ps.setInt(2, clientId);
                ps.setInt(3, orgId);
                ps.setInt(4, userId);
                ps.setInt(5, userId);
                ps.setString(6, "Multi CSV Import");
                ps.setString(7, "Import multiple CSV files");
                ps.setString(8, "org.idempiere.multicsvimport.WMultiCSVImportForm");
                ps.setString(9, formUU);
                ps.executeUpdate();
                System.out.println("✅ AD_Form created (ID: " + formId + ")");
            }
        } else {
            System.out.println("⚠️ AD_Form already exists (ID: " + formId + ")");
        }

        // Step 2: Create AD_Menu if it doesn't exist
        int menuId = getMenuIdIfExists(ctx, "Multi CSV Import");
        if (menuId == -1) {
            menuId = DB.getNextID(ctx, "AD_Menu", null);
            String menuUU = UUID.randomUUID().toString();
            String menuSql = "INSERT INTO AD_Menu (AD_Menu_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, " +
                    "Name, Description, IsSummary, IsSOTrx, IsReadOnly, Action, AD_Form_ID, EntityType, IsCentrallyMaintained, AD_Menu_UU) " +
                    "VALUES (?, ?, ?, 'Y', now(), ?, now(), ?, ?, ?, 'N', 'N', 'N', 'X', ?, 'D', 'Y', ?)";

            try (PreparedStatement ps = DB.prepareStatement(menuSql, null)) {
                ps.setInt(1, menuId);
                ps.setInt(2, clientId);
                ps.setInt(3, orgId);
                ps.setInt(4, userId);
                ps.setInt(5, userId);
                ps.setString(6, "Multi CSV Import");
                ps.setString(7, "Import multiple CSV files");
                ps.setInt(8, formId);
                ps.setString(9, menuUU);
                ps.executeUpdate();
                System.out.println("✅ AD_Menu created (ID: " + menuId + ")");
            }
        } else {
            System.out.println("⚠️ AD_Menu already exists (ID: " + menuId + ")");
        }
    }

    private int getFormIdIfExists(Properties ctx, String name) throws SQLException {
        String sql = "SELECT AD_Form_ID FROM AD_Form WHERE Name = ?";
        try (PreparedStatement ps = DB.prepareStatement(sql, null)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    private int getMenuIdIfExists(Properties ctx, String name) throws SQLException {
        String sql = "SELECT AD_Menu_ID FROM AD_Menu WHERE Name = ?";
        try (PreparedStatement ps = DB.prepareStatement(sql, null)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }
}
