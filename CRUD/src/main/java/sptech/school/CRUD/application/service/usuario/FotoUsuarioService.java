package sptech.school.CRUD.application.service.usuario;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.CRUD.domain.entity.UsuarioModel;
import sptech.school.CRUD.infrastructure.persistence.UsuarioJpaRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FotoUsuarioService {

    private final UsuarioJpaRepository usuarioJpaRepository;

    public Boolean uploadFoto(Integer id, MultipartFile file){
        if(file == null || file.isEmpty()){
            return false;
        }

        Optional<UsuarioModel> usuarioOpt = usuarioJpaRepository.findById(id);

        if (usuarioOpt.isPresent()){
            UsuarioModel usuario = usuarioOpt.get();
            try {
                usuario.setFoto(file.getBytes());
            }catch (Exception e){
                return false;
            }
            usuarioJpaRepository.save(usuario);
            return true;
        }

        return false;
    }

    public Optional<byte[]> buscarFoto(Integer id){
        return usuarioJpaRepository.findById(id)
                .map(UsuarioModel::getFoto);
    }
}
