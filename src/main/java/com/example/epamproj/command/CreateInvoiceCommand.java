package com.example.epamproj.command;

import com.example.epamproj.exceptions.AlertException;
import com.example.epamproj.exceptions.DBException;
import com.example.epamproj.dao.InvoiceDAO;
import com.example.epamproj.dao.entities.Invoice;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.SQLException;

public class CreateInvoiceCommand implements Command {

    private static Logger log = LogManager.getLogger(CreateInvoiceCommand.class.getName());
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException, AlertException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));   //TODO Add parameters check
        Date date = null;
        try {
            date = Date.valueOf(request.getParameter("date"));  //TODO Add parameters check
        }catch (Exception e){                         //TODO add new type of exception here
            log.error("Failed to convert date");
        }
        String details = request.getParameter("details");
        Invoice invoice = new Invoice(orderId, date, details);
        try {
            InvoiceDAO.getInstance().add(invoice, orderId);

        } catch (SQLException e) {
            log.error("failed to add invoice to dao or to update status");
            throw new DBException(e.getMessage(), e);
        }
        return "/controller?command=showOrders";
    }
}
