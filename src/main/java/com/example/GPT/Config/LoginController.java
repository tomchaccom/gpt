package com.example.GPT.Config;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class LoginController {

    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
        System.out.println("로그인 성공");
        Map<String, Object> attributes = oauth2User.getAttributes();
        System.out.println(attributes);
        model.addAttribute("name", attributes.get("name"));
        model.addAttribute("email", attributes.get("email"));
        return "loginSuccess"; // loginSuccess.html 템플릿으로 이동
    }

    @GetMapping("/loginFailure")
    public String loginFailure(Model model) {
        model.addAttribute("errorMessage", "구글 로그인에 실패했습니다.");
        return "loginFailure"; // loginFailure.html 템플릿으로 이동
    }

}
