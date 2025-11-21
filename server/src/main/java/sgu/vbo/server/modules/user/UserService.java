package sgu.vbo.server.modules.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sgu.vbo.server.modules.rol.Rol;
import sgu.vbo.server.modules.student.Student;
import sgu.vbo.server.modules.student.StudentRepository;
import sgu.vbo.server.modules.user.dto.UpdateUserDTO;
import sgu.vbo.server.modules.user.dto.UserStudentDTO;
import sgu.vbo.server.utils.ApiResponse;
import sgu.vbo.server.utils.PasswordUtils;

import java.sql.SQLException;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findAll(){
        ApiResponse body = new ApiResponse(
                "Operación exitosa",
                userRepository.findAll(),
                HttpStatus.OK
        );
        return new ResponseEntity<>(body, body.getStatus());
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findById(Long id){
        ApiResponse body = null;
        User found = userRepository.findById(id).orElse(null);
        if(found != null){
            body = new ApiResponse(
                    "Operación exitosa",
                    found,
                    HttpStatus.OK
            );
        } else {
            body = new ApiResponse(
                    "Usuario no existe",
                    true,
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(body, body.getStatus());
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseEntity<ApiResponse> saveUser(UserStudentDTO dto){
        ApiResponse body = null;

        try{
            // Crear el usuario
            User u = new User();
            u.setRol(Rol.STUDENT.getName());
            u.setUsername(dto.getUsername());
            u.setEmail(dto.getEmail());
            u.setPassword(PasswordUtils.generateEncodedpassword(dto.getUsername(), dto.getFullname()));

            u = userRepository.saveAndFlush(u);

            // Crear el student
            Student s = new Student();
            s.setFullname(dto.getFullname());
            s.setMatricula(generateMatricula());
            s.setUser(u);

            studentRepository.saveAndFlush(s);

            body = new ApiResponse(
                    "Usuario guardado exitosamente",
                    HttpStatus.CREATED
            );

        }catch (Exception e){
            body = new ApiResponse(
                    "Error al guardar el usuario: " + e.getMessage(),
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
            return new ResponseEntity<>(body, body.getStatus());
        }

        return new ResponseEntity<>(body, body.getStatus());
    }

    private String generateMatricula(){
        LocalDateTime now = LocalDateTime.now();
        // Año + gmu + mes en numero + día del mes + - + tiempo en hh:mm:ss
        return now.getYear() +
                "gmu" +
                now.getMonthValue() + "" +
                now.getDayOfMonth() +
                "-" +
                String.format("%02d", now.getHour()) +
                String.format("%02d", now.getMinute()) +
                String.format("%02d", now.getSecond());
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseEntity<ApiResponse> updateUser(UpdateUserDTO dto){
        ApiResponse body = null;
        User u = userRepository.findById(dto.getId()).orElse(null);
        try{
            if(u != null){
                u.setUsername(dto.getUsername() != null ? dto.getUsername() : u.getUsername());
                u.setEmail(dto.getEmail() != null ? dto.getEmail() : u.getEmail());

                userRepository.saveAndFlush(u);

                body = new ApiResponse(
                        "Usuario actualizado exitosamente",
                        HttpStatus.OK
                );
            } else {
                body = new ApiResponse(
                        "Usuario no existe",
                        true,
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception e){
            body = new ApiResponse(
                    "Error al actualizar el usuario: " + e.getMessage(),
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
            return new ResponseEntity<>(body, body.getStatus());
        }
        return new ResponseEntity<>(body, body.getStatus());
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseEntity<ApiResponse> deleteteUser(UpdateUserDTO dto){
        ApiResponse body = null;
        User u = userRepository.findById(dto.getId()).orElse(null);
        try{
            if(u != null){
                userRepository.deleteById(u.getId());

                body = new ApiResponse(
                        "Usuario eliminado exitosamente",
                        HttpStatus.OK
                );
            } else {
                body = new ApiResponse(
                        "Usuario no existe",
                        true,
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception e){
            body = new ApiResponse(
                    "Error al eliminar el usuario: " + e.getMessage(),
                    true,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
            return new ResponseEntity<>(body, body.getStatus());
        }
        return new ResponseEntity<>(body, body.getStatus());
    }
}
