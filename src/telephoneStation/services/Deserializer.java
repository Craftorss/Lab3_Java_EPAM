package telephoneStation.services;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.helpers.DefaultHandler;
import telephoneStation.entity.Address;
import telephoneStation.entity.Subscriber;
import telephoneStation.entity.phoneCodes.PhoneNumber;
import telephoneStation.exceptions.CantLoadException;
import telephoneStation.services.parsers.MySAXHandler;
import telephoneStation.services.parsers.MyStaXParser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Deserializer {
    private static ArrayList<Subscriber> subs = new ArrayList<Subscriber>();

    public static Object getData(String fullPath) throws CantLoadException {
        try(XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(fullPath))){
            return xmlDecoder.readObject();
        }
        catch(IndexOutOfBoundsException | IOException ex){
            throw new CantLoadException();
        }
    }

    public static List<Subscriber> getSubListDOM(String fullPath) throws CantLoadException {
        try{
            File file = new File(fullPath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document xmlDoc = builder.parse(file);
            List<Subscriber> subList = new ArrayList<Subscriber>();
            Node root = xmlDoc.getFirstChild();
            NodeList nlist = root.getChildNodes();
            for (int i = 0 ; i < nlist.getLength() ; i++) {
                Node child = nlist.item(i);//sub
                ArrayList<String> columns = new ArrayList<String>();
                NodeList nlist2 = child.getChildNodes();
                for(int j = 0; j < nlist2.getLength(); j++)
                {
                    Node child2 = nlist2.item(j);
                    if(child2.getNodeName().equals("phoneNumber") || child2.getNodeName().equals("address")) {
                        NodeList nList3 = child2.getChildNodes();
                        for (int m = 0; m < nList3.getLength(); m++){
                            Node child3 = nList3.item(m);
                            columns.add(child3.getTextContent());
                        }
                    }
                    else
                        columns.add(child2.getTextContent());
                }
                Subscriber sub = new Subscriber();
                sub.setFirstName(columns.get(0));
                sub.setPatronymic(columns.get(1));
                sub.setLastName(columns.get(2));
                PhoneNumber phoneNumber = new PhoneNumber();
                phoneNumber.setMobileOperator(columns.get(3));
                phoneNumber.setCountryCode(columns.get(4));
                phoneNumber.setNumber(columns.get(5));
                phoneNumber.setFullPhoneNumber(columns.get(6));
                sub.setPhoneNumber(phoneNumber);
                Address addr = new Address();
                addr.setCountry(columns.get(7));
                addr.setCity(columns.get(8));
                addr.setHomeNumber(columns.get(9));
                addr.setStreet(columns.get(10));
                addr.setFlatNumber(columns.get(11));
                if (columns.get(12).equals("true"))
                    addr.setFlat(true);
                else
                    addr.setFlat(false);
                sub.setAdr(addr);
                subList.add(sub);
            }
            return subList;

        } catch (Exception ex) {
            throw new CantLoadException();
        }
    }

    public static List<Subscriber> getSubListSAX(String FullPath) throws CantLoadException {
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            MySAXHandler handler = new MySAXHandler();

            File file = new File(FullPath);
            saxParser.parse(file, handler);
            return handler.getSubs();
        } catch (Exception ex) {
            throw new CantLoadException();
        }
    }

    public static List<Subscriber> getSubListStaX(String FullPath) throws CantLoadException {
        try{
            MyStaXParser staxParser = new MyStaXParser();
            return staxParser.parseXMLfile(FullPath);
        } catch (Exception ex) {
            throw new CantLoadException();
        }
    }
}
