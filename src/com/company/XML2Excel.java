package com.company;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XML2Excel {
    private XML2Prop xml2Prop;
    private WritableWorkbook book;
    private List<String> value;

    public XML2Excel(XML2Prop xml2Prop, String outFileName) throws IOException {
        this(xml2Prop);
        setBook(outFileName);
    }

    public XML2Excel(XML2Prop xml2Prop) {
        this.xml2Prop = xml2Prop;
        value = this.xml2Prop.getValue();

    }

    public XML2Excel(File file) throws DocumentException {
        this(new XML2Prop(file));
    }

    public XML2Excel(String fileName) throws DocumentException {
        this(new File(fileName));
    }

    public void setBook(File file) throws IOException {
        book = Workbook.createWorkbook(file);
    }

    public void setBook(String fileName) throws IOException {
        book = Workbook.createWorkbook(new File(fileName));
    }

    public void writeFile(String out2Excel) throws IOException, WriteException {
        setBook(out2Excel);
        writeFile();
    }

    public void writeFile() throws WriteException, IOException {
        if (book == null) {
            throw new IOException();
        }
        WritableSheet sheet = book.createSheet("sheet1", 0);
        for (int i = 0; i < value.size(); i++) {
            String[] s1 = value.get(i).split("=");
            Label[] labels = new Label[s1.length];
            for (int j = 0; j < labels.length; j++) {
                labels[j] = new Label(j, i, s1[j].trim());
                sheet.addCell(labels[j]);
            }
        }
        book.write();
        book.close();
    }
}
