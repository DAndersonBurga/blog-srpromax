package com.anderson.blogsrpromax.app.repository;


import com.anderson.blogsrpromax.app.register.token.VerificationToken;

import java.util.Optional;

public interface IVerificationTokenRepository {
    VerificationToken save(VerificationToken token);
    Optional<VerificationToken> findByToken(String token);
}
