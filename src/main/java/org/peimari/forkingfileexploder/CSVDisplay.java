package org.peimari.forkingfileexploder;

import org.vaadin.maddon.label.RichText;

/**
 *
 * @author mattitahvonenitmill
 */
public class CSVDisplay extends RichText {

    public CSVDisplay(String str) {
        withSafeHtml("<pre>" + str + "</pre>");
    }

}
