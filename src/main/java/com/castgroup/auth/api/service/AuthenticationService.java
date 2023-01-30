package com.castgroup.auth.api.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.castgroup.auth.api.config.JwtConfig;
import com.castgroup.auth.api.domain.User;
import com.castgroup.auth.api.model.UserAuthReqDTO;
import com.castgroup.auth.api.model.UserAuthRespDTO;
import com.castgroup.auth.api.model.UserRespDTO;
import com.castgroup.auth.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService{
    
    private final JwtConfig jwtConfig;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public UserRespDTO saveUser(User newUser){
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return modelMapper.map(userRepository.save(newUser), UserRespDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return modelMapper.map(user, UserAuthReqDTO.class);
    }

    public UserAuthRespDTO authenticationToken(UserAuthReqDTO userReq) {
        User user = userRepository.findByEmail(userReq.getEmail());
        String accessToken = jwtConfig.generateToken(user.getEmail());
        return new UserAuthRespDTO(user.getName(), user.getEmail(), accessToken);
    }
}
