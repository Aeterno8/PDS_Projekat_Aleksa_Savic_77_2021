package com.example.webnovelapp.servlet;

import com.example.webnovelapp.model.Webnovel;
import com.example.webnovelapp.service.WebnovelService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "LoadMoreWebnovelsServlet", value = "/LoadMoreWebnovelsServlet")
public class LoadMoreWebnovelsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long lastWebnovelId = Long.parseLong(request.getParameter("lastWebnovelId"));
        WebnovelService webnovelService = new WebnovelService();
        List<Webnovel> nextNovels = webnovelService.getNextFiveWebnovels(lastWebnovelId);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        for (Webnovel novel : nextNovels) {
            out.println("<div class='novel-container' data-novel-id='" + novel.getId() + "'>");
            out.println("<a href='webnovel.jsp?id=" + novel.getId() + "'>");
            out.println("<img class='novel-image' src='" + novel.getCoverUrl() + "' alt='Cover Image'>");
            out.println("<div class='novel-title'>" + novel.getTitle() + "</div>");
            out.println("</a>");
            out.println("</div>");
        }

        webnovelService.close();
    }
}