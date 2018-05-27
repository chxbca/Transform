package com.company;

import org.dom4j.DocumentException;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws DocumentException, FileNotFoundException {
        XML2Prop xml2Prop = new XML2Prop("scratch.xml");
        xml2Prop.writeFile("scratch.properties");
        xml2Prop.setFile("homework.xml");
        xml2Prop.writeFile("homework.properties");
    }


}


