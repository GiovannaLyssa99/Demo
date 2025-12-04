package com.giovanna.turismo.mongo;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resposta {
    private Long usuarioId;
    private String texto;
    private LocalDateTime data;
}