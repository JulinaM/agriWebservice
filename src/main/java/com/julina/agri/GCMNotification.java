package com.julina.agri;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

/**
 * Created by julina on 10/6/14.
 */

//@WebServlet("/a/GCMNotification")
public class GCMNotification extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Put your Google API Server Key here
    private static final String GOOGLE_SERVER_KEY = "AIzaSyAQV1G-C5ocOqlUTaiuDXI8rsbokFoxLVA";

    public GCMNotification() {
        super();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String address=request.getContextPath()+"/index.jsp";
        System.out.println("we are here :  "+address);
        RequestDispatcher dispatcher=request.getRequestDispatcher(address);
        dispatcher.forward(request, response);

    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        Result result = null;

        String share = request.getParameter("shareRegId");

        // GCM RedgId of Android device to send push notification
        String regId = "";
        if (share != null && !share.isEmpty()) {
            regId = request.getParameter("regId");
            PrintWriter writer = new PrintWriter("GCMRegId.txt");
            writer.println(regId);
            writer.close();
            request.setAttribute("pushStatus", "GCM RegId Received.");
            request.getRequestDispatcher("index.jsp")
                    .forward(request, response);
        } else {
            try {
                BufferedReader br = new BufferedReader(new FileReader(
                        "GCMRegId.txt"));
                regId = br.readLine();
                br.close();
                String userMessage = request.getParameter("message");
                Sender sender = new Sender(GOOGLE_SERVER_KEY);
                Message message = new Message.Builder().timeToLive(30)
                        .delayWhileIdle(true).addData("m", userMessage).build();
                System.out.println("regId: "+regId);
                result = sender.send(message, regId, 1);
                request.setAttribute("pushStatus", result.toString());

                String error = result.getErrorCodeName();
                System.out.println("GCM Push Nofication Failure: " + error);

            } catch (IOException ioe) {
                ioe.printStackTrace();
                request.setAttribute("pushStatus",
                        "RegId required: " + ioe.toString());
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("pushStatus", e.toString());
            }
            request.getRequestDispatcher("index.jsp")
                    .forward(request, response);
        }
    }
}
