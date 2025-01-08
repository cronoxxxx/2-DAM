package org.example;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

@Log4j2
@WebServlet(name = Constantes.NAME_SERVER, value = Constantes.VALUE_SERVER)
public class PrimerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType(Constantes.INIT);
        try {
            PrintWriter result = resp.getWriter();
            initializeSession(req);

            String guess = req.getParameter(Constantes.GUESS);
            String message = processGuess(req, guess);

            result.print(generateHtml(message, (int) req.getSession().getAttribute(Constantes.CONTADOR)));

            if (isGameOver(message)) {
                resetGame(req);
            }
        } catch (IOException | NumberFormatException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void initializeSession(HttpServletRequest req) {
        if (req.getSession().getAttribute(Constantes.CONTADOR) == null) {
            req.getSession().setAttribute(Constantes.CONTADOR, 0);
            req.getSession().setAttribute(Constantes.NUMBER_TO_GUESS, new Random().nextInt(10) + 1);
            req.getSession().setAttribute(Constantes.LAST_GUESS, null);
        }
    }

    private String processGuess(HttpServletRequest req, String guess) {
        if (guess == null || guess.isEmpty()) {
            return Constantes.INTRODUZCA_UN_NUMERO;
        }
            int currentGuess = Integer.parseInt(guess);
            Integer lastGuess = (Integer) req.getSession().getAttribute(Constantes.LAST_GUESS);
            if (lastGuess != null && lastGuess == currentGuess) {
                return Constantes.HAS_REPETIDO_EL_NUMERO;
            }
            return evaluateGuess(req, currentGuess);

    }


    private String evaluateGuess(HttpServletRequest req, int userGuess) {
        int contador = (int) req.getSession().getAttribute(Constantes.CONTADOR);
        int numberToGuess = (int) req.getSession().getAttribute(Constantes.NUMBER_TO_GUESS);

        if (contador >= 4) {
            return Constantes.RETRY_MESSAGE_LOOSE;
        }

        req.getSession().setAttribute(Constantes.CONTADOR, contador + 1);
        req.getSession().setAttribute(Constantes.LAST_GUESS, userGuess);

        if (userGuess == numberToGuess) {
            return Constantes.WIN_MESSAGE + " " + numberToGuess + Constantes.RETRY_MESSAGE;
        }
        return userGuess < numberToGuess ? Constantes.GUESS_HIGHER : Constantes.GUESS_LOWER;

    }


    private boolean isGameOver(String message) {
        return message.startsWith(Constantes.FELICIDADES) || message.startsWith(Constantes.HAS_PERDIDO);
    }

    private void resetGame(HttpServletRequest req) {
        req.getSession().setAttribute(Constantes.CONTADOR, 0);
        req.getSession().setAttribute(Constantes.NUMBER_TO_GUESS, new Random().nextInt(10) + 1);
        req.getSession().setAttribute(Constantes.LAST_GUESS, null);
    }


    private String generateHtml(String message, int contador) {
        StringBuilder html = new StringBuilder();
        html.append("<h1>Juego de Adivinar el Numero</h1>");
        html.append("<p>Intenta adivinar el número entre 1 y 10.</p>");
        html.append("<form method='get'><input type='number' name='guess' min='1' max='10' required><button type='submit'>Enviar</button></form>");

        if (!message.isEmpty()) html.append("<p style='color: blue;'>").append(message).append("</p>");

        html.append("<p>Intentos: ").append(contador).append("/5</p>");
        if (contador >= 5) html.append("<p>Fin del juego.</p>");

        return html.toString();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        doGet(req, resp);
    }
}
