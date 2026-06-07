package com.ecommerce.amazon.service;

import com.ecommerce.amazon.dto.auth.EsqueciSenhaDTO;
import com.ecommerce.amazon.dto.auth.LoginRequestDTO;
import com.ecommerce.amazon.dto.auth.RegisterRequestDTO;
import com.ecommerce.amazon.dto.auth.ResetSenhaDTO;
import com.ecommerce.amazon.dto.auth.TokenResponseDTO;
import com.ecommerce.amazon.entity.Usuario;
import com.ecommerce.amazon.entity.enums.TipoUsuario;
import com.ecommerce.amazon.exception.BusinessException;
import com.ecommerce.amazon.repository.UsuarioRepository;
import com.ecommerce.amazon.security.JwtService;
import com.ecommerce.amazon.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public TokenResponseDTO login(LoginRequestDTO dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.senha())
        );
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        String role = userDetails.getUsuario().getTipoUsuario().name();
        return TokenResponseDTO.of(token, userDetails.getUsername(), role);
    }

    public TokenResponseDTO cadastrar(RegisterRequestDTO dto) {
        if (usuarioRepository.findByEmail(dto.email()).isPresent()) {
            throw new BusinessException("E-mail já cadastrado.");
        }
        Usuario usuario = Usuario.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(passwordEncoder.encode(dto.senha()))
                .tipoUsuario(TipoUsuario.CLIENTE)
                .build();
        usuarioRepository.save(usuario);
        UserDetailsImpl userDetails = new UserDetailsImpl(usuario);
        String token = jwtService.generateToken(userDetails);
        return TokenResponseDTO.of(token, usuario.getEmail(), TipoUsuario.CLIENTE.name());
    }

    public void esqueceuSenha(EsqueciSenhaDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new BusinessException("E-mail não cadastrado."));

        String token = UUID.randomUUID().toString();
        usuario.setTokenResetSenha(token);
        usuario.setTokenExpiracao(LocalDateTime.now().plusMinutes(30));
        usuarioRepository.save(usuario);

        emailService.enviarEmailResetSenha(usuario.getEmail(), token);
    }

    public void redefinirSenha(ResetSenhaDTO dto) {
        Usuario usuario = usuarioRepository.findByTokenResetSenha(dto.token())
                .orElseThrow(() -> new BusinessException("Token inválido."));

        if (usuario.getTokenExpiracao() == null || usuario.getTokenExpiracao().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Token expirado. Solicite uma nova redefinição de senha.");
        }

        usuario.setSenha(passwordEncoder.encode(dto.novaSenha()));
        usuario.setTokenResetSenha(null);
        usuario.setTokenExpiracao(null);
        usuarioRepository.save(usuario);
    }
}
