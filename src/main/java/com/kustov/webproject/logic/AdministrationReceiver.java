package com.kustov.webproject.logic;

import com.kustov.webproject.dao.AdministrationDAO;
import com.kustov.webproject.entity.Administration;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;

public class AdministrationReceiver {
    public Administration findAdministrationByUser(User user) throws ServiceException {
        AdministrationDAO dao = new AdministrationDAO();
        try{
            Administration administration = dao.findByUserId(user.getId());
             administration.setUser(user);
            return administration;
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }
}
