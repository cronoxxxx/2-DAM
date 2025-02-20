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
        Long count = entityManager.createQuery("SELECT COUNT(c) FROM CredentialEntity c WHERE c.username = :username OR c.email = :email", Long.class)
                .setParameter("username", credentialEntity.getUsername())
                .setParameter("email", credentialEntity.getEmail())
                .getSingleResult();
        if (count > 0) {
            throw new UserAlreadyExistsException(Constantes.USER_ALREADY_EXISTS + credentialEntity.getUsername());
        }
        entityManager.persist(credentialEntity);
    }

    public void updateUserVerification(CredentialEntity credentialEntity) {
        CredentialEntity existingCredential = entityManager.find(CredentialEntity.class, credentialEntity.getId());
        if (existingCredential != null) {
            existingCredential.setVerified(credentialEntity.isVerified());
            existingCredential.setVerificationCode(credentialEntity.getVerificationCode());
            entityManager.merge(existingCredential);
        }
    }

    public CredentialEntity findByVerificationCode(String verificationCode) {
        try {
            return entityManager.createQuery("SELECT c FROM CredentialEntity c WHERE c.verificationCode = :code", CredentialEntity.class)
                    .setParameter("code", verificationCode)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new UserNotFoundException(Constantes.INVALID_VERIFICATION_CODE);
        }
    }

    public RoleEntity findRoleByName(String roleName) {
        try {
            return entityManager.createQuery("SELECT r FROM RoleEntity r WHERE r.rol = :roleName", RoleEntity.class)
                    .setParameter("roleName", roleName)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new RoleNotFoundException("Role not found: " + roleName);
        }
    }
    public Optional<CredentialEntity> findByUsername(String username) {
        try {
            CredentialEntity credential = entityManager.createQuery(
                            "SELECT c FROM CredentialEntity c WHERE c.username = :username", CredentialEntity.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.of(credential);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


}