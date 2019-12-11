package telephoneStation.manage;

import telephoneStation.entity.Address;
import telephoneStation.entity.Station;
import telephoneStation.entity.Subscriber;
import telephoneStation.entity.phoneCodes.PhoneNumberConstants;
import telephoneStation.exceptions.DaoGetException;
import telephoneStation.exceptions.DaoSaveException;
import telephoneStation.exceptions.WrongInputException;
import telephoneStation.extendsEntity.StationProcessing;
import telephoneStation.stationDAO.MyDAO;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static telephoneStation.constants.ProgramConstants.*;

public final class Controller {
    public static List<Subscriber> getSubList(int choice) throws DaoGetException {
        return MyDAO.daoGetSubList(choice);
    }
}
