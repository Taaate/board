package hello.board.interceptor.member;

import hello.board.controller.member.session.SessionConst;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.entity.MemberRole;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class MemberDeleteInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Member loginMember = findLoginMember(request);
        Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long id = Long.parseLong((String)pathVariables.get("id"));

        if(isMe(loginMember, id) || isAdmin(loginMember)) {
            return true;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    private Member findLoginMember(HttpServletRequest request) {
        return (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
    }

    private boolean isMe(Member loginMember, Long id) {
        return loginMember.getId().equals(id);
    }

    private boolean isAdmin(Member loginMember) {
        return loginMember.getRole().equals(MemberRole.ADMIN);
    }
}
