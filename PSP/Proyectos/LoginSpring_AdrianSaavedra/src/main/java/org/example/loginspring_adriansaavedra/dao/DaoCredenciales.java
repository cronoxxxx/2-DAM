package org.example.loginspring_adriansaavedra.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.common.errors.RoleNotFoundException;
import org.example.loginspring_adriansaavedra.common.errors.UserAlreadyExistsException;
import org.example.loginspring_adriansaavedra.common.errors.UserNotFoundException;
import org.example.loginspring_adriansaavedra.domain.model.CredentialEntity;
import org.example.loginspring_adriansaavedra.domain.model.RoleEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class DaoCredenciales {
    @PersistenceContext
    private EntityManager entityManager;

    public void verifyUserExists(CredentialEntity credentialEntity) {
        Long count = entityManager.createQuery(Constantes.SQL_VERIFY_USER_EXISTS, Long.class)
                .setParameter(Constantes.USERNAME_PARAM, credentialEntity.getUsername())
                .setParameter(Constantes.EMAIL_PARAM, credentialEntity.getEmail())
                .getSingleResult();
        if (count > 0) {
            throw new UserAlreadyExistsException(Constantes.USER_ALREADY_EXISTS + Constantes.CHECK_IF_EMAIL_OR_USERNAME_IS_ALREADY_REGISTERED);
        }
        entityManager.persist(credentialEntity);
    }

    public void verifyAndUpdateUser(String verificationCode) {
        try {
            CredentialEntity credential = entityManager.createQuery(Constantes.SQL_FIND_BY_VERIFICATION_CODE, CredentialEntity.class)
                    .setParameter(Constantes.CODE_PARAM, verificationCode)
                    .getSingleResult();

            if (!credential.isVerified()) {
                credential.setVerified(true);
                credential.setVerificationCode(null);
                entityManager.merge(credential);
            }
        } catch (NoResultException e) {
            throw new UserNotFoundException(Constantes.USER_NOT_FOUND);
        }
    }


    public RoleEntity findRoleByName(String roleName) {
        try {
            return entityManager.createQuery(Constantes.SQL_FIND_ROLE_BY_NAME, RoleEntity.class)
                    .setParameter(Constantes.ROLENAME_PARAM, roleName)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new RoleNotFoundException(Constantes.ROLE_NOT_FOUND + roleName);
        }
    }

    public Optional<CredentialEntity> findByUsername(String username) {
        try {
            CredentialEntity credential = entityManager.createQuery(
                            Constantes.SQL_FIND_BY_USERNAME, CredentialEntity.class)
                    .setParameter(Constantes.USERNAME_PARAM, username)
                    .getSingleResult();
            return Optional.of(credential);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    public void authenticateUser(String username) {
        try {
            CredentialEntity credential = entityManager.createQuery(
                            Constantes.SQL_FIND_BY_USERNAME, CredentialEntity.class)
                    .setParameter(Constantes.USERNAME_PARAM, username)
                    .getSingleResult();
            if (!credential.isVerified()) {
                throw new UserAlreadyExistsException(Constantes.THIS_ACCOUNT_HAS_NOT_BEEN_VERIFIED);
            }
        } catch (NoResultException e) {
            throw new UserNotFoundException(Constantes.THE_ACCOUNT_DOES_NOT_EXIST);
        }
    }



}