package com.example.epamproj.command;

import com.example.epamproj.exceptions.AlertException;
import com.example.epamproj.exceptions.AppException;
import com.example.epamproj.exceptions.DBException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogoutCommand implements Command {

    private static Logger log = LogManager.getLogger(LogoutCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException, AlertException {
        {
            if (request.getSession().getAttribute("user") != null) {
                request.getSession().removeAttribute("user");
                log.info("Attribute \"user\" removed from session");
                return "/login.jsp";
            } else {
                log.warn("user isn't logged in");
                throw new AlertException("You can't exit your account if you're not signed in", "/index.jsp");
            }
        }

    }
}
