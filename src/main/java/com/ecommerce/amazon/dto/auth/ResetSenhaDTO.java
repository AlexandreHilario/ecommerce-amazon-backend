package com.ecommerce.amazon.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetSenhaDTO(
        @NotBlank String token,
        @NotBlank @Size(min = 6) String novaSenha
) {
}
