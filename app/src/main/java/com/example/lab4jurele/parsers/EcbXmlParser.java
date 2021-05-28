package com.example.lab4jurele.parsers;

import com.example.lab4jurele.utilities.Constants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class EcbXmlParser {
    public static List<String> getCurrencyRatesBaseUsd(InputStream stream) throws IOException {
        List<String> result = new ArrayList<>();
        try {
            DocumentBuilderFactory xmlDocFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder xmlDocBuilder = xmlDocFactory.newDocumentBuilder();
            Document doc = xmlDocBuilder.parse(stream);

            NodeList rateNodes = doc.getElementsByTagName(Constants.CUBE_NODE);
            for (int i = 2; i < rateNodes.getLength(); ++i) {
                Element cube = (Element) rateNodes.item(i);
                String currencyName = cube.getAttribute("currency");
                String rate = cube.getAttribute("rate");
                result.add(String.format("1 EUR = %s %s", rate, currencyName));
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return result;
    }
}
