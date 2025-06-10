package com.eagle.feature.user.service;

import com.eagle.feature.config.JwtConfig;
import com.eagle.feature.exception.IdentityException;
import com.eagle.feature.user.repository.IdentityRepository;
import com.eagle.feature.user.web.model.Identity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IdentityService {
    private final IdentityRepository identityRepository;
    private final JwtConfig jwtConfig;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public IdentityService(IdentityRepository identityRepository,
                           JwtConfig jwtConfig,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.identityRepository = identityRepository;
        this.jwtConfig = jwtConfig;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void createIdentity(UUID userId, Identity identity) {
        identityRepository.createIdentity(userId, identity.getEmail(), bCryptPasswordEncoder.encode(identity.getPassword()));
    }

    public String login(String email, String password) {
        List<Identity> identities = identityRepository.getIdentityByEmail(email);
        if (identities.isEmpty()) {
            throw new IdentityException("Invalid email or password");
        }

        Identity identity = identities.get(0);
        if (!bCryptPasswordEncoder.matches(password, identity.getPassword())) {
            throw new IdentityException("Invalid email or password");
        }

        return jwtConfig.generateToken(identity.getUserId());
    }
}
