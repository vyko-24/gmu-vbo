package sgu.vbo.server.modules.rol;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Rol {
    ADMIN("ROLE_ADMIN"),
    STUDENT("ROLE_STUDENT");

    @Getter
    private final String name;
}
