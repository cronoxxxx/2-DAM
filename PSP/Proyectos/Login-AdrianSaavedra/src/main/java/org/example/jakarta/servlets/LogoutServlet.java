package org.example.jakarta.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.example.common.Constantes;

import java.io.IOException;

@Log4j2
@WebServlet(name = Constantes.LOGOUT_SERVLET, value = Constantes.URL_LOGOUT_SERVLET)
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            resp.sendRedirect(req.getContextPath() + Constantes.URL_LOGIN_SERVLET);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
