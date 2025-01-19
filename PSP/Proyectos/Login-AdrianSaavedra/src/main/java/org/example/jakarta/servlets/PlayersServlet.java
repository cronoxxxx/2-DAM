package org.example.jakarta.servlets;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.example.common.Constantes;
import org.example.domain.model.Player;
import org.example.domain.service.GestionJugadores;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@Log4j2
@WebServlet(name = Constantes.PLAYERS_SERVLET, value = Constantes.URL_PLAYERS_SERVLET)
public class PlayersServlet extends HttpServlet {
    private final transient GestionJugadores gestionJugadores;

    @Inject
    public PlayersServlet(GestionJugadores gestionJugadores) {
        this.gestionJugadores = gestionJugadores;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(Constantes.TEMPLATE_ENGINE_ATTR);
            WebContext context = new WebContext(JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp));

            String errorMessage = (String) req.getSession().getAttribute(Constantes.ERROR_ADD_PLAYER);
            if (errorMessage != null) {
                context.setVariable(Constantes.ERROR_ADD_PLAYER, errorMessage);
                req.getSession().removeAttribute(Constantes.ERROR_ADD_PLAYER);
            }

            String action = req.getParameter(Constantes.ACTION);

            if (Constantes.EDIT.equals(action)) {
                String id = req.getParameter(Constantes.ID);
                Player player = gestionJugadores.getPlayerById(id);
                if (player != null) {
                    context.setVariable(Constantes.PLAYER, player);
                    templateEngine.process(Constantes.UPDATE, context, resp.getWriter());
                    return;
                }
            }
            context.setVariable(Constantes.PLAYERS, gestionJugadores.getAllPlayers());
            templateEngine.process(Constantes.PLAYERS, context, resp.getWriter());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter(Constantes.ACTION);
        String id = req.getParameter(Constantes.ID);
        String name = req.getParameter(Constantes.NAME);
        String team = req.getParameter(Constantes.TEAM);
        String country = req.getParameter(Constantes.COUNTRY);
        try {
            switch (action) {
                case Constantes.ADD -> {
                    boolean added = gestionJugadores.addPlayer(Player.builder().name(name).team(team).country(country).build());
                    if (!added) {
                        req.getSession().setAttribute(Constantes.ERROR_ADD_PLAYER, Constantes.MESSAGE_ERROR_ADD_PLAYER);
                    }
                }
                case Constantes.UPDATE ->
                        gestionJugadores.updatePlayer(new Player(Integer.parseInt(id), name, team, country));
                default -> gestionJugadores.deletePlayer(id);

            }
            resp.sendRedirect(req.getContextPath() + Constantes.URL_PLAYERS_SERVLET);
        } catch (NumberFormatException | IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}


