package hello.servlet.basic.request;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 클라이언트
 * http://localhost:8080/request-body-string
 *
 * header
 * content-type: text/plain
 *
 * body
 * hello! I send Postman!!!
 */
@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Postman 에서 text로 전송
        ServletInputStream inputStream = request.getInputStream(); //바이트 코드로 얻고
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);//바이트 코드를 String으로 변경해준다. (뒤에는 인코딩 티입)

        System.out.println("messageBody = " + messageBody);

        response.getWriter().write("Ok!!!");
    }
}