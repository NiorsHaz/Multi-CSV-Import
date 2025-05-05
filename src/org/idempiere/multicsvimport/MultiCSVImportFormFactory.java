package org.idempiere.multicsvimport;

import org.adempiere.webui.factory.IFormFactoryExtension;
import org.adempiere.webui.form.IFormController;

public class MultiCSVImportFormFactory implements IFormFactoryExtension {

    @Override
    public IFormController newFormInstance(String formName) {
        if ("org.idempiere.multicsvimport.WMultiCSVImport".equals(formName)) {
            return new WMultiCSVImport();
        }
        return null;
    }
}
