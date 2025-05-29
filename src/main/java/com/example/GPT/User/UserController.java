package com.example.GPT.User;

import com.example.GPT.Detail.DetailSupport;
import com.example.GPT.Detail.DetailSupportDto;
import com.example.GPT.Support.SupportProgram;
import com.example.GPT.Support.SupportProgramDto;
import com.example.GPT.Support.UserSupportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign")
    public SignResponseDto signUp(@RequestBody signRequestDto dto){
        SignResponseDto response= userService.signUser(dto);

        return response;
    }
    @PostMapping("/login")
    public String loginAndResponseToken(@RequestBody loginRequestDto dto){
        return userService.login(dto);
    }

    // 본인에게 해당하는 지원 제도를 저장하는 로직
    @PostMapping("/users/supports")
    public String saveUserSupport(@RequestBody UserSupportDto dto){
        return userService.saveUserSupport(dto);
    }
    @GetMapping("/users/supports/{id}")
    public List<SupportProgramDto> getUserSupport(@PathVariable long id){
        return userService.getUserSupportList(id);
    }
    @GetMapping("users/details/{id}")
    public List<DetailSupportDto> getUserSupportDetails(@PathVariable long id){
        return userService.getUserSupportDetailList(id);
    }

}
