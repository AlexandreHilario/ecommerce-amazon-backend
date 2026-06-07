package com.ecommerce.amazon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarEmailResetSenha(String destinatario, String token) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinatario);
        mensagem.setSubject("Redefinição de senha");
        mensagem.setText("Use o token abaixo para redefinir sua senha. Ele expira em 30 minutos.\n\nToken: " + token);
        mailSender.send(mensagem);
    }
}
