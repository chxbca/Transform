package com.company;

import jxl.write.WriteException;
import org.dom4j.DocumentException;

import java.io.IOException;

public class Main {
    public static void main(String args[]) throws DocumentException, IOException, WriteException {
        XML2Prop xml2Prop = new XML2Prop("scratch.xml");
        xml2Prop.writeFile("scratch.properties");
        xml2Prop.setFile("homework.xml");
        xml2Prop.writeFile("homework.properties");
        XML2Excel xml2Excel = new XML2Excel(xml2Prop, "homework.xls");
        xml2Excel.writeFile();
    }
}

