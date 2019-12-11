package telephoneStation.stationDAO;

import telephoneStation.entity.Station;
import telephoneStation.entity.Subscriber;
import telephoneStation.exceptions.CantLoadException;
import telephoneStation.exceptions.DaoGetException;
import telephoneStation.exceptions.DaoSaveException;
import telephoneStation.services.Deserializer;
import telephoneStation.services.Serializer;

import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.List;

public class MyDAO {
    private static final String path = "./src/telephoneStation/resources/";
    private static final String fileName = "station";
    private static final String ext = ".xml";
    private static final String fileNameForExp = "stationExp";
    private static final String fullPath = "E:/JavaLabs_Epam/src/telephoneStation/resources/stationExp.xml";

    public static Station getData() throws DaoGetException {
        try {
            return (Station) Deserializer.getData(path + fileName + ext);
        }
        catch (CantLoadException ex){
            throw new DaoGetException();
        }
    }

    public static void pullData(Object obj) throws DaoSaveException {
        try(XMLEncoder xmlEncoder = new XMLEncoder(new FileOutputStream(path + fileName + ext))){
            xmlEncoder.writeObject(obj);
            xmlEncoder.flush();
        }
        catch(Exception ex){
            throw new DaoSaveException();
        }
    }

    public static void pullDataForExport(Station station) throws DaoSaveException {
        try {
            Serializer.pullSubscribers(station, path + fileNameForExp + ext);
        } catch (Exception ex) {
            throw new DaoSaveException();
        }
    }

    public static List<Subscriber> daoGetSubList(int choice) throws DaoGetException {
        try{
            switch (choice){
                case 1:
                    return Deserializer.getSubListDOM(fullPath);
                case 2:
                    return Deserializer.getSubListSAX(fullPath);
                case 3:
                default:
                    return Deserializer.getSubListStaX(fullPath);
            }
        }catch (CantLoadException ex) {
            throw new DaoGetException();
        }
    }
}
