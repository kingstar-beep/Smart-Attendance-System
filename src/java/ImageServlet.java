/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

 /*
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ImageServlet")
public class ImageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getParameter("path");

        File file = new File(path);

        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("image/png");

        FileInputStream fis =
                new FileInputStream(file);

        OutputStream os =
                response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }

        fis.close();
        os.close();
    }
}*/
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ImageServlet")
public class ImageServlet extends HttpServlet {

    private static final String IMAGE_DIRECTORY
            = "C:/attendance_images";

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String pathParameter
                = request.getParameter("path");

        if (pathParameter == null
                || pathParameter.trim().isEmpty()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST);

            return;
        }

        try {

            Path baseDirectory
                    = Paths.get(IMAGE_DIRECTORY)
                            .toAbsolutePath()
                            .normalize();

            Path requestedFile
                    = Paths.get(pathParameter)
                            .toAbsolutePath()
                            .normalize();

            /*
             * Security check:
             * The requested file must remain
             * inside the attendance image directory.
             */
            if (!requestedFile.startsWith(baseDirectory)) {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN);

                return;
            }

            File file
                    = requestedFile.toFile();

            if (!file.exists()
                    || !file.isFile()) {

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND);

                return;
            }

            /*
             * Attendance images are stored as PNG files.
             */
            if (!file.getName()
                    .toLowerCase()
                    .endsWith(".png")) {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN);

                return;
            }

            response.setContentType(
                    "image/png");

            try (FileInputStream fis
                    = new FileInputStream(file); OutputStream os
                    = response.getOutputStream()) {

                byte[] buffer
                        = new byte[4096];

                int bytesRead;

                while ((bytesRead
                        = fis.read(buffer)) != -1) {

                    os.write(
                            buffer,
                            0,
                            bytesRead);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
