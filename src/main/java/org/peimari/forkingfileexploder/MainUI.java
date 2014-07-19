package org.peimari.forkingfileexploder;

import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.output.StringBuilderWriter;
import org.vaadin.maddon.layouts.MVerticalLayout;

/**
 *
 * @author mattitahvonenitmill
 */
@CDIUI
public class MainUI extends UI {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    Upload upload = new Upload("Upload csv file",
            (String filename, String mimeType) -> baos);

    @Override
    protected void init(VaadinRequest request) {

        upload.addSucceededListener(new Upload.SucceededListener() {

            @Override
            public void uploadSucceeded(Upload.SucceededEvent event) {
                try {

                    StringBuilder sb = new StringBuilder();
                    String str = baos.toString("UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(
                            new StringReader(str));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        line = line.trim();
                        if (line.isEmpty()) {
                            continue;
                        }
                        // No;Sarja;Rata-1;Rata-2;Rata-3
                        String[] split = line.split(";");
                        for (int i = 2; i < split.length; i++) {
                            sb.append(split[0]);
                            sb.append(";");
                            sb.append(split[1]);
                            sb.append(";");
                            sb.append(i - 2 + 1);
                            sb.append(". osuus");
                            sb.append(";");
                            sb.append(split[i]);
                            sb.append("\n");
                        }
                    }
                    setContent(new CSVDisplay(sb.toString()));
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(MainUI.class.getName()).
                            log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MainUI.class.getName()).
                            log(Level.SEVERE, null, ex);
                }

            }
        });

        setContent(new MVerticalLayout(upload));

    }

}
