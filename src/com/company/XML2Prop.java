package com.company;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

class XML2Prop {
    private File file;
    private Element root;
    private List<String> value = new ArrayList<>();

    XML2Prop(File file) throws DocumentException {
        setFile(file);
    }

    XML2Prop(String filePath) throws DocumentException {
        this(new File(filePath));
    }

    void setFile(File file) throws DocumentException {
        this.file = file;
        createXML();
    }

    void setFile(String filePath) throws DocumentException {
        setFile(new File(filePath));
    }

    private void createXML() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        root = document.getRootElement();
        updateAllValue();
    }

    void updateAllValue() {
        traversalElement(root);
    }

//      对Document对象调用getRootElement()方法可以返回代表根节点的Element对象。
//      拥有了一个Element对象后，
//      可以对该对象调用elementIterator()方法获得它的子节点的Element对象们的一个迭代器。
//      使用(Element)iterator.next()方法遍历一个iterator并把每个取出的元素转化为Element类型。

    private void traversalElement(Element element) {

        if (element == null) {
            return;
        }

        if (element == root) {
            value.clear();
        }

        traversalAttribute(element);

        for (Iterator it = element.elementIterator(); it.hasNext(); ) {
            Element next = (Element) it.next();
            String s = next.getUniquePath();
            s = s.substring(1).replace('/', '.');
            if (next.isTextOnly()) {
                s += " = " + next.getTextTrim();
                s = s.substring(s.indexOf('.') + 1);
                value.add(s);
            }
            traversalElement(next);
        }
    }

    private void traversalAttribute(Element element) {
        for (Iterator it = element.attributeIterator(); it.hasNext(); ) {
            Attribute attribute = (Attribute) it.next();
            String s = attribute.getPath();
            s = s.substring(1).replace('/', '.');
            int index = s.lastIndexOf('@');
            s = s.substring(0, index) + s.substring(index + 1, s.length());
            s += " = " + attribute.getText();
            s = s.substring(s.indexOf('.') + 1);
            value.add(s);
        }
    }

    void writeFile(File outFile) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(outFile)) {
            out.println("#" + new Date());
            value.forEach(out::println);
        }
    }

    void writeFile(String outFilePath) throws FileNotFoundException {
        writeFile(new File(outFilePath));
    }
}
