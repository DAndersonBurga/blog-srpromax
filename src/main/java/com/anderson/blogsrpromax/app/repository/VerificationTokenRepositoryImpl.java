package com.anderson.blogsrpromax.app.repository;

import com.anderson.blogsrpromax.app.register.token.VerificationToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VerificationTokenRepositoryImpl implements IVerificationTokenRepository {

    private final EntityManager entityManager;

    @Transactional
    @Override
    public VerificationToken save(VerificationToken token) {
        entityManager.persist(token);
        return token;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<VerificationToken> findByToken(String token) {
        Optional<VerificationToken> verificationTokenOptional = Optional.empty();

        try {
            return verificationTokenOptional = Optional.ofNullable(entityManager.createQuery("SELECT vt FROM VerificationToken vt WHERE vt.token = :token", VerificationToken.class)
                    .setParameter("token", token)
                    .getSingleResult());
        } catch (NoResultException e) {
            return verificationTokenOptional;
        }
    }
}
