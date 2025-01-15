package org.example.jakarta.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.example.common.Constantes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@Log4j2
@WebServlet(name = Constantes.LOGIN_SERVLET, value = Constantes.URL_LOGIN_SERVLET)
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession(false);
            if (session != null && session.getAttribute(Constantes.USER_ATTRIBUTE) != null) {
                resp.sendRedirect(req.getContextPath() + Constantes.URL_PLAYERS_SERVLET);
                return;
            }
            TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(Constantes.TEMPLATE_ENGINE_ATTR);
            WebContext context = new WebContext(JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp));
            templateEngine.process(Constantes.LOGIN, context, resp.getWriter());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String username = req.getParameter(Constantes.USERNAME);
            String password = req.getParameter(Constantes.PASSWORD);

            if (Constantes.ADMIN.equals(username.strip()) && Constantes.ADMIN.equals(password.strip())) {
                HttpSession session = req.getSession();
                session.setAttribute(Constantes.USER_ATTRIBUTE, username);
                resp.sendRedirect(req.getContextPath() + Constantes.URL_PLAYERS_SERVLET);
            } else {
                resp.sendRedirect(req.getContextPath() + Constantes.URL_LOGIN_ERROR);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}