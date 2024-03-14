package controleurs;

import jakarta.servlet.http.HttpServletRequest;

public class AuthenUtil {
    public static boolean verifyToken(HttpServletRequest req){
        String token = req.getHeader("Authorization");
        if(token == null || !token.startsWith("Bearer")){
            return false;
        }
        token = token.substring("bearer".length()).trim();

        try{
            JwtManager.decodeJWT(token);
        } catch(Exception e){
            return false;
        }
        return true;
    }
}
