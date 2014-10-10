package com.julina.agri;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.julina.agri.common.AgriException;
import com.julina.agri.common.ErrorMessages;
import com.julina.agri.common.ResponseJson;
import com.julina.agri.dao.InfoClientDao;
import com.julina.agri.dao.InfoDao;
import com.julina.agri.pojo.InfoPojo;
import com.tektak.iloop.rmodel.RmodelException;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by julina on 10/6/14.
 */

//@WebServlet("/a/GCMNotification")
public class GCMNotification extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Put your Google API Server Key here
    private static final String GOOGLE_SERVER_KEY = "AIzaSyAPxdy3kTeYeQQAo82saLCA1_k6WJriILc";

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
        //String regId = "";
        if (share != null && !share.isEmpty()) {
          //  regId = request.getParameter("shareRegId");
            /*PrintWriter writer = new PrintWriter("GCMRegId.txt");
            writer.println(regId);
            writer.close();*/
            request.setAttribute("pushStatus", "GCM RegId Received.");
            request.getRequestDispatcher("index.jsp")
                    .forward(request, response);
        } else {
            try {
                // nitu's regId = " APA91bER77dK6t1oS7bXSLnuaGEKjqMCHMUqiOUPSJW1tBC_MYgWzyezGCZj2HE6UgTyGn2S__Ev12dK2f6JLnLiqX1NDJCz-zd0Fbg4Mb_KTBI4e8F2z1sne6WX0Wn35DUMvrC1uMEepDbf70NrHdMr_h-fLp-vvQ";
                //String jsonString = request.getParameter("payload");
                String jsonString = "{\"infoId\":10,\n" +
                        "\"infoTitle\":\"Potato\",\n" +
                        "\"infoFrom\":\"testingggg\",\n" +
                        "\"infoData\":\"this is about sugarcane\",\n" +
                        "\"timestamp\": 0,\n" +
                        "\"tag\":\"2-1\"\n" +
                        "\n" +
                        "}";
                JSONObject requestJson = new JSONObject(jsonString);
                InfoPojo info = new InfoPojo();

                info.setInfoId(requestJson.getInt("infoId"));

                info.setInfoTitle(requestJson.getString("infoTitle"));
                info.setInfoFrom(requestJson.getString("infoFrom"));
                info.setInfoData(requestJson.getString("infoData"));
                info.setTimestamp(requestJson.getLong("timestamp"));
                String tag = requestJson.getString("tag");
                InfoDao infoDao = new InfoDao();
                int result1 = infoDao.insert(info);
                if (result1 != 1){
                }
                InfoClientDao infoClientDao = new InfoClientDao();
                int result2= infoClientDao.insert(requestJson.getInt("infoId"), tag);
                if(result2 != 1){
                }
                infoDao = new InfoDao();
                String[] regIds = infoDao.getRegId(tag);

               for(String regId : regIds) {
                    System.out.println(regId);
                }

                    String regId = " APA91bGDXZuPw-kafXcE1QwDQPrVhd6bGk1wIaM60zUsC9pS1lLcZCh_VV9B-OeBFdYeWfeGFJ0R5MYZ7fvfTVURymiaJ4A0khMZ2ARMsPraRWSBrt9eWoHmTTRTAUUyyf0V60kSeGVP";
                    System.out.println("======================> "+regId);
                    String userMessage = request.getParameter("message");
                    Sender sender = new Sender(GOOGLE_SERVER_KEY);
                    Message message = new Message.Builder().timeToLive(30)
                            .delayWhileIdle(true).addData("m", userMessage).build();
                    System.out.println("regId: " + regId);
                    result = sender.send(message, regId, 1);

                    request.setAttribute("pushStatus", result.toString());

                    String error = result.getErrorCodeName();
                    System.out.println("GCM Push Nofication Failure: " + error);
               // }

            } catch (IOException ioe) {
                ioe.printStackTrace();
                request.setAttribute("pushStatus",
                        "RegId required: " + ioe.toString());
            } catch (RmodelException.CommonException e) {
                e.printStackTrace();
                request.setAttribute("pushStatus", e.toString());
            } catch (AgriException.DataModelException e) {
                e.printStackTrace();
                request.setAttribute("pushStatus", e.toString());
            } catch (AgriException.NullPointerException e) {
                e.printStackTrace();
                request.setAttribute("pushStatus", e.toString());
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("pushStatus", e.toString());
            } catch (RmodelException.SqlException e) {
                e.printStackTrace();
                request.setAttribute("pushStatus", e.toString());
            }
            catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("pushStatus", e.toString());
            }
            request.getRequestDispatcher("index.jsp")
                    .forward(request, response);
        }
    }
}
