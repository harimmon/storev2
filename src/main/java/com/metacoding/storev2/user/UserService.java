package com.metacoding.storev2.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {
        // 1. 아이디 중복체크
        User user = userRepository.findByUsername(joinDTO.getUsername());


        // 2. 있으면
        if (user != null) throw new RuntimeException("중복된 아이디 입니다.");

        // 3. 없으면 회원가입하기
        userRepository.save(joinDTO.getUsername(), joinDTO.getPassword(), joinDTO.getFullname());
    }

    public User 로그인(UserRequest.LoginDTO loginDTO) {
        // 1. 해당 유저네임이 있나?
        User user = userRepository.findByUsername(loginDTO.getUsername());

        // 2. 필터링 (유저네임, 패스워드가 불일치하는 것들을)
        if (user == null) {
            throw new RuntimeException("해당 username이 없습니다");
        }

        if (!(user.getPassword().equals(loginDTO.getPassword()))) {
            throw new RuntimeException("해당 password가 틀렸습니다");
        }

        // 3. 인증
        return user;
    }
}
