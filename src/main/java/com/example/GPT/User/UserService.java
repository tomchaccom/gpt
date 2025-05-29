package com.example.GPT.User;

import com.example.GPT.Detail.DetailRepository;
import com.example.GPT.Detail.DetailSupport;
import com.example.GPT.Detail.DetailSupportDto;
import com.example.GPT.JWT.JwtUtil;
import com.example.GPT.Support.SupportProgram;
import com.example.GPT.Support.SupportProgramDto;
import com.example.GPT.Support.SupportRepository;
import com.example.GPT.Support.UserSupportDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final SupportRepository supportRepository;
    private final DetailRepository detailRepository;
    private final PasswordEncoder passwordEncoder;

    // 이미 가입한 이메일이 존재하면 나가리
    public void checkUserEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException();
        }
    }
    // 이메일 비밀번호 쌍을 matches로 검사 , 실제로 디비에 어떤 비밀번호가 저장되는지 알 수 없음
    public String login(loginRequestDto dto) {
        Optional<User> entity =
                userRepository.findByEmail(dto.getEmail());

        if (entity.isPresent()) {
            boolean matches = passwordEncoder.matches(dto.getPassword(), entity.get().getPassword());
            if (matches) {
                return jwtUtil.generateToken(dto.getEmail());
            }
        }   return "이메일 비밀번호가 올바르지 않습니다";

    }
    // 암호화해서 엔티티를 저장, 그 후 아이디와 성공 메시지를 출력
    public SignResponseDto signUser(signRequestDto Dto){

        checkUserEmail(Dto.getEmail());
        User entity = new User(Dto.getEmail(), passwordEncoder.encode(Dto.getPassword()));
        userRepository.save(entity);
        Long id = userRepository.findByEmail(Dto.getEmail()).
                orElseThrow(NoSuchElementException::new).getId();
        return new SignResponseDto(id, "User signed successfully");

    }
    // 본인에게 맞는 지원 제도(왼 오 화면 모두)를 저장하는 용도 -- 저장할 로직을 다 정리하기
    public String saveUserSupport(UserSupportDto dto){

        User entity = userRepository.findById(dto.getId())
                .orElseThrow(NoSuchElementException::new);

        for (Long sid : dto.getSupportIdList()){
            SupportProgram sp = supportRepository.findById(sid)
                    .orElseThrow(NoSuchElementException::new);
            DetailSupport ds = detailRepository.findById(sid)
                    .orElseThrow(NoSuchElementException::new);

            sp.setUser(entity);
            ds.setUser(entity);

            entity.getSupportProgramList().add(sp);
            entity.getDetailSupportList().add(ds);
        }
        return "저장 완료";
    }

    // 본인 맞춤형 지원 제도를 조회하는 메소드
    public List<SupportProgramDto> getUserSupportList(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        return user.getSupportProgramList().stream()
                .map(sp -> new SupportProgramDto(
                        sp.getId(),
                        sp.getCategory(),
                        sp.getProgramName(),
                        sp.getDescription(),
                        sp.getContact()
                ))
                .toList();
    }

    public List<DetailSupportDto> getUserSupportDetailList(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        return user.getDetailSupportList().stream()
                .map(ds -> new DetailSupportDto(
                        ds.getId(),
                        ds.getTarget(),
                        ds.getContent(),
                        ds.getProcedureInfo()
                ))
                .toList();
    }

}
