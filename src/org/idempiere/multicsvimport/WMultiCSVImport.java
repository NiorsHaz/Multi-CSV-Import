package org.idempiere.multicsvimport;

import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Vbox;
import org.adempiere.webui.window.CustomForm;
import org.adempiere.webui.window.FDialog;
import org.adempiere.webui.form.IFormController;
import org.adempiere.webui.event.WEvents;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Fileupload;

import java.io.InputStream;

public class WMultiCSVImport implements IFormController {

    private CustomForm form = new CustomForm();
    private Panel panel = new Panel();

    @Override
    public void initForm(org.compiere.model.GridField[] gridFields, org.compiere.model.GridTab gridTab) {
        form.setTitle("Multi CSV Import");
        form.appendChild(panel);

        Vbox vbox = new Vbox();
        vbox.setSpacing("10px");

        Label label = new Label("Upload multiple CSV files to import");
        Button uploadButton = new Button("Upload CSVs");

        uploadButton.addEventListener(Events.ON_CLICK, event -> {
            Media[] medias = Fileupload.get("Select CSV Files", "Import", 10, false, false, true);
            if (medias != null) {
                MultiCSVImportHandler handler = new MultiCSVImportHandler();
                for (Media media : medias) {
                    try (InputStream is = media.getStreamData()) {
                        handler.processSingleCSV(media.getName(), is);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                FDialog.info(0, form, "Import finished.");
            }
        });

        vbox.appendChild(label);
        vbox.appendChild(uploadButton);
        panel.appendChild(vbox);
    }

    @Override
    public CustomForm getForm() {
        return form;
    }
}
