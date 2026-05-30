package com.ecommerce.amazon.dto.auth;

public record TokenResponseDTO(String token, String tipo, String email, String role) {

    public static TokenResponseDTO of(String token, String email, String role) {
        return new TokenResponseDTO(token, "Bearer", email, role);
    }
}
