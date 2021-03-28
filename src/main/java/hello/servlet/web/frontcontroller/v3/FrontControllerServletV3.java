package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    /**
     * controllerMap에 진입 uri별 생성할 객체를 저장
     */
    public FrontControllerServletV3() {
        System.out.println("FrontControllerServletV3.FrontControllerServletV3");

        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service");

        /**
         * 요청 들어온 uri를 받아오고
         */
        String requestURI = request.getRequestURI();

        /**
         * controllerMap 에서 요청들어온 uri에 해당하는 객체주소값을 받아온다.
         */
        ControllerV3 controller = controllerMap.get(requestURI);

        /**
         * uri에 해당하는 객체가 존재하는지 확인
         */
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        /**
         * 파라미터값을 추출하기 위한 메서드
         */
        Map<String, String> paramMap = createParamMap(request);

        /**
         * 파라미터 값을 uri에 해당하는 객체에 인자값으로 넘긴다.
         */
        ModelView mv = controller.process(paramMap);

        /**
         * view를 위해서 model에서 저장한 viewname을 가져온다.
         */
        String viewName = mv.getViewName();

        /**
         * viewResolver 메서드에 viewName을 인자값으로 전달해 논리적 이름을 물리적인 이름으로 변경
         */
        MyView view = viewResolver(viewName);

        /**
         * view에 정보를 보여주기위함
         * modelview의 model도 같이 넘겨줘야 한다.
         */
        view.render(mv.getModel(), request, response);
    }

    /**
     * 논리적인 이름을 물리적인 이름으로 변경하여 리
     *
     * @param viewName
     * @return MyView Object
     */
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    /**
     * Map<String, String> 형태로 파라미터 값을 저장한다.
     * 대표적으로 username, age가 파라미터 값으로 들어온다.
     *
     * @param request
     * @return paramMap
     */
    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();

        //request.getParameterNames() -> username & age
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}