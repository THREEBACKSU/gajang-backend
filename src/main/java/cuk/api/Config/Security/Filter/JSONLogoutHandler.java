package cuk.api.Config.Security.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import cuk.api.ResponseEntities.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JSONLogoutHandler extends SimpleUrlLogoutSuccessHandler {
    private final ObjectMapper objectMapper;

    public JSONLogoutHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        ResponseMessage resp = new ResponseMessage();
        resp.setMessage("Success");
        resp.setStatus(HttpStatus.OK);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(objectMapper.writeValueAsString(resp));
        response.getWriter().flush();
    }
}
