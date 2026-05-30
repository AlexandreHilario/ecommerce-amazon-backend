package com.ecommerce.amazon.service;

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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                       JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

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

    public void redefinirSenha(ResetSenhaDTO dto) {
        // 1. Buscar o usuário no banco pelo e-mail
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com este e-mail!"));

        // 2. Criptografar a nova senha para não salvar em texto puro
        String senhaCriptografada = passwordEncoder.encode(dto.novaSenha());

        // 3. Atualizar a senha do usuário
        usuario.setSenha(senhaCriptografada);

        // 4. Salvar as alterações no banco de dados
        usuarioRepository.save(usuario);
    }

}
