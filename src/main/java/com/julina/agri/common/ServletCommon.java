package com.julina.agri.common;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by julina on 8/26/14.
 */
public class ServletCommon {
    public static String generateToken(HttpSession httpSession){
        String token = httpSession.getAttribute("token").toString();
        if(token == null){
            token = String.valueOf(new Date().getTime());
            httpSession.setAttribute("token",token);
        }
        return token;
    }
}
