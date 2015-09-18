package com.labeltel.site.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.labeltel.site.domain.User;
import com.labeltel.site.repository.UserRepository;
import com.labeltel.site.security.xauth.Token;
import com.labeltel.site.security.xauth.TokenProvider;
import com.labeltel.site.service.UserService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api")
public class UserXAuthTokenController {

    private String MOODLE_BASE_URL = "http://localhost/moodle";

    @Inject
    private TokenProvider tokenProvider;

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @RequestMapping(value = "/authenticate",
            method = RequestMethod.POST)
    @Timed
    public Token authorize(@RequestParam String username, @RequestParam String password) throws Exception {

        if(username.equals("admin")) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = this.authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails details = this.userDetailsService.loadUserByUsername(username);
            return tokenProvider.createToken(details);
        }

        try {
            HttpResponse<JsonNode> tokenResponse = Unirest.get(MOODLE_BASE_URL + "/login/token.php?username={username}&password={password}&service={service_name}")
                .routeParam("username", username)
                .routeParam("password", password)
                .routeParam("service_name", "labtel_site")
                .asJson();

            JsonNode rootNode = tokenResponse.getBody();
            JSONObject rootObj = rootNode.getObject();

            if(rootObj.has("error")) {
                String error = rootObj.getString("error");
                throw new BadCredentialsException("Access Denied");
            }
            else if (rootObj.has("token")) {
                String tokenStr = rootObj.getString("token");

                HttpResponse<JsonNode> userResponse = Unirest.post(MOODLE_BASE_URL + "/webservice/rest/server.php")
                    .queryString("wsfunction", "core_user_get_users_by_field")
                    .queryString("moodlewsrestformat", "json")
                    .field("wstoken", tokenStr)
                    .field("field", "username")
                    .field("values[0]", username)
                    .asJson();

                JsonNode node = userResponse.getBody();
                JSONObject userObj = node.getArray().getJSONObject(0);
                if(userObj != null)
                {
                    long id = userObj.getLong("id");
                    User _user = userRepository.findOneByMoodleId(id);

                    if(_user == null) {
                        String login = userObj.getString("username");
                        String firstname = userObj.getString("firstname");
                        String email = userObj.getString("email");
                        _user = userService.createUser(login, login, firstname, firstname, email, "pt-br", id);
                    }

                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(_user.getLogin(), _user.getLogin());
                    Authentication authentication = this.authenticationManager.authenticate(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    UserDetails details = this.userDetailsService.loadUserByUsername(_user.getLogin());
                    return tokenProvider.createToken(details);

                }
                else {
                    throw new BadCredentialsException("Access Denied");
                }
            }

        } catch (UnirestException e) {
            throw new BadCredentialsException("Access Denied");
        } catch (JSONException e) {
            throw new BadCredentialsException("Access Denied");
        } catch (BadCredentialsException e)
        {
            throw e;
        }

        return null;
    }

    @RequestMapping(value = "/teste",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Token teste(@RequestParam String user, @RequestParam String service) throws Exception {




        try {
            HttpResponse<JsonNode> tokenResponse = Unirest.get("http://localhost/moodle/login/token.php?username={username}&password={password}&service={service_name}")
                .routeParam("username", user)
                .routeParam("password", "051190*Aa1")
                .routeParam("service_name", service)
                .asJson();

            JsonNode rootNode = tokenResponse.getBody();
            JSONObject rootObj = rootNode.getObject();

            if(rootObj.has("error")) {
                String error = rootObj.getString("error");
                throw new BadCredentialsException("Access Denied");
            }
            else if (rootObj.has("token")) {
                String tokenStr = rootObj.getString("token");

                HttpResponse<JsonNode> userResponse = Unirest.post("http://localhost/moodle/webservice/rest/server.php")
                    .queryString("wsfunction", "core_user_get_users_by_field")
                    .queryString("moodlewsrestformat", "json")
                    .field("wstoken", tokenStr)
                    .field("field", "username")
                    .field("values[0]", user)
                    .asJson();

                JsonNode node = userResponse.getBody();
                JSONObject userObj = node.getArray().getJSONObject(0);
                if(userObj != null)
                {
                    long id = userObj.getLong("id");
                    User _user = userRepository.findOneByMoodleId(id);

                    if(_user == null) {
                        String login = userObj.getString("username");
                        String firstname = userObj.getString("firstname");
                        String email = userObj.getString("email");
                        _user = userService.createUser(login, login, firstname, firstname, email, "pt-br", id);
                    }

                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(_user.getLogin(), _user.getLogin());
                    Authentication authentication = this.authenticationManager.authenticate(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    UserDetails details = this.userDetailsService.loadUserByUsername(_user.getLogin());
                    return tokenProvider.createToken(details);

                }
                else {
                    throw new BadCredentialsException("Access Denied");
                }
            }

        } catch (UnirestException e) {
            throw new BadCredentialsException("Access Denied");
        } catch (JSONException e) {
            throw new BadCredentialsException("Access Denied");
        } catch (BadCredentialsException e)
        {
            throw e;
        }

        return null;
    }
}
