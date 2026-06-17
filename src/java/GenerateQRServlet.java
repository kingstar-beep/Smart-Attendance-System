/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

/**
 *
 * @author KINGSTAR
 */


@WebServlet("/GenerateQRServlet")
public class GenerateQRServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            return; // nothing to generate
        }

        int width = 250;
        int height = 250;

        try {
            BitMatrix matrix = new MultiFormatWriter()
                    .encode(code, BarcodeFormat.QR_CODE, width, height);

            response.setContentType("image/png");

            MatrixToImageWriter.writeToStream(matrix, "PNG", response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace(); // check server logs
        }
    }
}