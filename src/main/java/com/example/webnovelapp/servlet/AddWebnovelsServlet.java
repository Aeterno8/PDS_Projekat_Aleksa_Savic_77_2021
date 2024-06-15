package com.example.webnovelapp.servlet;

import com.example.webnovelapp.service.WebnovelService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(name = "AddWebnovelsServlet", value = "/addWebnovels")
@MultipartConfig
public class AddWebnovelsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final WebnovelService webnovelService = new WebnovelService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Part filePart = request.getPart("zipFile");
            if (filePart != null && filePart.getSize() > 0) {
                Path tempFilePath = Files.createTempFile("uploaded", ".zip");
                try (InputStream fileContent = filePart.getInputStream();
                     FileOutputStream fos = new FileOutputStream(tempFilePath.toFile())) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fileContent.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                }

                webnovelService.addWebnovelsFromZipFile(tempFilePath.toString());

                Files.delete(tempFilePath);

                response.sendRedirect("admin.jsp?status=success&message=" + URLEncoder.encode("Webnovels added successfully", StandardCharsets.UTF_8.toString()));
            } else {
                response.sendRedirect("admin.jsp?status=error&message=" + URLEncoder.encode("No file uploaded or file is empty", StandardCharsets.UTF_8.toString()));
            }
        } catch (Exception e) {
            handleException(e, request, response);
        }
    }

    @Override
    public void destroy() {
        webnovelService.close();
    }

    private void handleException(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        e.printStackTrace();
        String errorMessage = URLEncoder.encode("Exception occurred: " + e.getMessage(), StandardCharsets.UTF_8.toString());
        response.sendRedirect("admin.jsp?status=error&message=" + errorMessage);
    }
}