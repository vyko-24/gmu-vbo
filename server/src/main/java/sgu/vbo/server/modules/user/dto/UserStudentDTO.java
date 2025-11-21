package sgu.vbo.server.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserStudentDTO {
    private String username;
    private String email;
    private String fullname;

}
