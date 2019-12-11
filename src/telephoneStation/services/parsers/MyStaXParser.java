package telephoneStation.services.parsers;

import telephoneStation.entity.Address;
import telephoneStation.entity.Subscriber;
import telephoneStation.entity.phoneCodes.PhoneNumber;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class MyStaXParser {

    private boolean firstName;
    private boolean patronymic;
    private boolean lastName;
    private boolean mobileOperator = false;
    private boolean countryCode = false;
    private boolean number = false;
    private boolean fullPhoneNumber = false;
    private boolean country = false;
    private boolean city = false;
    private boolean homeNumber = false;
    private boolean street = false;
    private boolean flatNumber = false;
    private boolean isFlat = false;

    public  List<Subscriber> parseXMLfile(String fileName) throws XMLStreamException, FileNotFoundException {
        List<Subscriber> subList = new ArrayList<Subscriber>();
        Subscriber sub = new Subscriber();
        PhoneNumber phoneNumber = new PhoneNumber();
        Address address = new Address();
        XMLInputFactory factory = XMLInputFactory.newInstance();

        XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(fileName));

        int event = reader.getEventType();
            while (true) {
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        if (reader.getLocalName().equals("subscriber")) {
                            sub = new Subscriber();
                            phoneNumber = new PhoneNumber();
                            address = new Address();
                        }
                        if (reader.getLocalName().equals("firstName")) {
                            firstName = true;
                        }
                        if (reader.getLocalName().equals("patronymic")) {
                            patronymic = true;
                        }
                        if (reader.getLocalName().equals("lastName")) {
                            lastName = true;
                        }
                        if (reader.getLocalName().equals("mobileOperator")) {
                            mobileOperator = true;
                        }

                        if (reader.getLocalName().equals("countryCode")) {
                            countryCode = true;
                        }

                        if (reader.getLocalName().equals("number")) {
                            number = true;
                        }

                        if (reader.getLocalName().equals("fullPhoneNumber")) {
                            fullPhoneNumber = true;
                        }

                        if (reader.getLocalName().equals("country")) {
                            country = true;
                        }

                        if (reader.getLocalName().equals("city")) {
                            city = true;
                        }

                        if (reader.getLocalName().equals("homeNumber")) {
                            homeNumber = true;
                        }

                        if (reader.getLocalName().equals("street")) {
                            street = true;
                        }

                        if (reader.getLocalName().equals("flatNumber")) {
                            flatNumber = true;
                        }

                        if (reader.getLocalName().equalsIgnoreCase("isFlat")) {
                            isFlat = true;
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        if (firstName) {
                            sub.setFirstName(reader.getText());
                            firstName = false;
                        }

                        if (patronymic) {
                            sub.setPatronymic(reader.getText());
                            patronymic = false;
                        }

                        if (lastName) {
                            sub.setLastName(reader.getText());
                            lastName = false;
                        }

                        if (mobileOperator) {
                            phoneNumber.setMobileOperator(reader.getText());
                            mobileOperator = false;
                        }

                        if (countryCode) {
                            phoneNumber.setCountryCode(reader.getText());
                            countryCode = false;
                        }

                        if (number) {
                            phoneNumber.setNumber(reader.getText());
                            number = false;
                        }

                        if (fullPhoneNumber) {
                            phoneNumber.setFullPhoneNumber(reader.getText());
                            sub.setPhoneNumber(phoneNumber);
                            fullPhoneNumber = false;
                        }

                        if (country) {
                            address.setCountry(reader.getText());
                            country = false;
                        }
                        if (city) {
                            address.setCity(reader.getText());
                            city = false;
                        }

                        if (homeNumber) {
                            String buff = reader.getText();
                            if(!buff.equals("true") && !buff.equals("false"))
                                address.setHomeNumber(reader.getText());
                            else
                                address.setHomeNumber("");
                            homeNumber = false;
                        }

                        if (street) {
                            address.setStreet(reader.getText());
                            street = false;
                        }

                        if (flatNumber) {
                            String buff = reader.getText();
                            if(!buff.equals("true") && !buff.equals("false"))
                                address.setFlatNumber(reader.getText());
                            else
                                address.setFlatNumber("");

                            flatNumber = false;
                        }

                        if (isFlat) {
                            String buff = reader.getText();
                            if (buff.equals("true"))
                                address.setFlat(true);
                            else
                                address.setFlat(false);

                            sub.setAdr(address);
                            isFlat = false;
                        }
                        break;


                    case XMLStreamConstants.END_ELEMENT:
                        if (reader.getLocalName().equals("subscriber")) {
                            subList.add(sub);
                        }
                        break;
                }

                if (!reader.hasNext())
                    break;

                event = reader.next();
            }
        return subList;
    }
}