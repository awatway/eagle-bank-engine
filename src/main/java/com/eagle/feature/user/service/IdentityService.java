package com.eagle.feature.user.service;

import com.eagle.feature.auth.JwtProvider;
import com.eagle.feature.common.exception.IdentityException;
import com.eagle.feature.user.repository.IdentityRepository;
import com.eagle.feature.user.repository.domain.Identity;
import com.eagle.feature.user.web.model.LoginRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IdentityService {
    private final IdentityRepository identityRepository;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public IdentityService(IdentityRepository identityRepository,
                           JwtProvider jwtProvider,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.identityRepository = identityRepository;
        this.jwtProvider = jwtProvider;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void createIdentity(UUID userId, Identity identity) {
        identityRepository.createIdentity(userId, identity.getEmail(), bCryptPasswordEncoder.encode(identity.getPassword()));
    }

    public String login(final LoginRequest loginRequest) {
        List<Identity> identities = identityRepository.getIdentityByEmail(loginRequest.getEmail());
        if (identities.isEmpty()) {
            throw new IdentityException("Invalid email or password");
        }

        Identity identity = identities.getFirst();
        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), identity.getPassword())) {
            throw new IdentityException("Invalid email or password");
        }

        return jwtProvider.generateToken(identity.getUserId());
    }
}
