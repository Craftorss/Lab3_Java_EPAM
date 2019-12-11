package telephoneStation.services.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import telephoneStation.entity.Address;
import telephoneStation.entity.Subscriber;
import telephoneStation.entity.phoneCodes.PhoneNumber;

import java.util.ArrayList;
import java.util.List;

public class MySAXHandler extends DefaultHandler {

    private ArrayList<Subscriber> subList = new ArrayList<Subscriber>();
    private Subscriber sub;
    private PhoneNumber phoneNumber;
    private Address address;
    private boolean firstName = false;
    private boolean patronymic = false;
    private boolean lastName = false;
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

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        if (qName.equals("firstName")) {
            sub = new Subscriber();
            firstName = true;
        }

        if (qName.equals("patronymic")) {
            patronymic = true;
        }

        if (qName.equals("lastName")) {
            lastName = true;
        }

        if (qName.equals("mobileOperator")) {
            phoneNumber = new PhoneNumber();
            mobileOperator = true;
        }

        if (qName.equals("countryCode")) {
            countryCode = true;
        }

        if (qName.equals("number")) {
            number = true;
        }

        if (qName.equals("fullPhoneNumber")) {
            fullPhoneNumber = true;
        }

        if (qName.equals("country")) {
            address = new Address();
            country = true;
        }

        if (qName.equals("city")) {
            city = true;
        }

        if (qName.equals("homeNumber")) {
            homeNumber = true;
        }

        if (qName.equals("street")) {
            street = true;
        }

        if (qName.equals("flatNumber")) {
            flatNumber = true;
        }

        if (qName.equalsIgnoreCase("isFlat")) {
            isFlat = true;
        }
    }

    public List<Subscriber> getSubs() {
        return this.subList;
    }

    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException {

        if (firstName) {
            sub.setFirstName(new String(ch, start, length));
            firstName = false;
        }

        if (patronymic) {
            sub.setPatronymic(new String(ch, start, length));
            patronymic = false;
        }

        if (lastName) {
            sub.setLastName(new String(ch, start, length));
            lastName = false;
        }

        if (mobileOperator) {
            phoneNumber.setMobileOperator(new String(ch, start, length));
            mobileOperator = false;
        }

        if (countryCode) {
            phoneNumber.setCountryCode(new String(ch, start, length));
            countryCode = false;
        }

        if (number) {
            phoneNumber.setNumber(new String(ch, start, length));
            number = false;
        }

        if (fullPhoneNumber) {
            phoneNumber.setFullPhoneNumber(new String(ch, start, length));
            sub.setPhoneNumber(phoneNumber);
            fullPhoneNumber = false;
        }

        if (country) {
            address.setCountry(new String(ch, start, length));
            country = false;
        }
        if (city) {
            address.setCity(new String(ch, start, length));
            city = false;
        }

        if (homeNumber) {
            String buff = new String(ch, start, length);
            if(!buff.equals("true") && !buff.equals("false"))
                address.setHomeNumber(new String(ch, start, length));
            else
                address.setHomeNumber("");
            homeNumber = false;
        }

        if (street) {
            address.setStreet(new String(ch, start, length));
            street = false;
        }

        if (flatNumber) {
            String buff = new String(ch, start, length);
            if(!buff.equals("true") && !buff.equals("false"))
                address.setFlatNumber(new String(ch, start, length));
            else
                address.setFlatNumber("");

            flatNumber = false;
        }

        if (isFlat) {
            String buff = new String(ch, start, length);
            if (buff.equals("true"))
                address.setFlat(true);
            else
                address.setFlat(false);

            sub.setAdr(address);
            subList.add(sub);
            isFlat = false;
        }
    }

}
