/**
 *
 */
package demo.facebook.login.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author avinash
 *
 */

@RestController
@RequestMapping("/v1/login")
@Api("Set of endpoints for Login using Facebook Login API.")
public class LoginAPI {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginAPI.class);
	@Autowired
	private RestTemplate restTemplate;

	@Value("${facebook.app.client_id}")
	private String clientId;

	@GetMapping
	@RequestMapping(path = "/facebook", produces = "application/json")
	@ApiOperation("Login using facebook login.")
	public void facebookLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String requestUrl = request.getRequestURL().toString();
		String queryString = request.getQueryString();
		LOGGER.info("Request is:" + requestUrl + "?" + queryString);
		String code = request.getParameter("code");
		String token = request.getParameter("access_token");
		LOGGER.info("code={}", code);
		LOGGER.info("token={}", token);

		if (!StringUtils.hasText(code)) {
			String fbuk_redirection_endPoint = "https://www.facebook.com/v3.1/dialog/oauth?client_id=" + clientId
					+ "&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fapi%2Fv1%2Flogin%2Ffacebook&scope=email,user_friends,user_location,user_gender&auth_type=rerequest";
			response.sendRedirect(fbuk_redirection_endPoint);
		} else {
			response.sendRedirect("https://developers.facebook.com");
		}

	}
}
