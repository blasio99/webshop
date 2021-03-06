package dev.blasio99.webshop.server.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;

import dev.blasio99.webshop.server.model.SecureToken;
import dev.blasio99.webshop.server.repo.SecureTokenRepository;

@Service
public class SecureTokenService {

    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(128);
    private static final Charset US_ASCII = Charset.forName("US-ASCII");

    @Autowired
    SecureTokenRepository secureTokenRepository;

	private int tokenValidityInDays = 30;

    public SecureToken createSecureToken(){
        String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII); // this is a sample, you can adapt as per your security need
        SecureToken secureToken = new SecureToken();
        secureToken.setToken(tokenValue);
        secureToken.setExpireAt(LocalDateTime.now().plusDays(getTokenValidityInDays()));
        this.saveSecureToken(secureToken);
        return secureToken;
    }

    public void saveSecureToken(SecureToken token) {
        secureTokenRepository.save(token);
    }

    public SecureToken findByToken(String token) {
        return secureTokenRepository.findByToken(token);
    }

    public void removeToken(SecureToken token) {
        secureTokenRepository.delete(token);
    }

    public void removeTokenByToken(String token) {
        secureTokenRepository.removeByToken(token);
    }

	public int getTokenValidityInDays() {
		return this.tokenValidityInDays;
	}

	public List<SecureToken> findAllTokens(){
		return secureTokenRepository.findAll();
	}
}
