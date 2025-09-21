package sptech.school.CRUD.application.service.usuario;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.CRUD.domain.entity.UsuarioModel;
import sptech.school.CRUD.infrastructure.persistence.UsuarioRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FotoUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Boolean uploadFoto(Integer id, MultipartFile file){
        if(file == null || file.isEmpty()){
            return false;
        }

        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent()){
            UsuarioModel usuario = usuarioOpt.get();
            try {
                usuario.setFoto(file.getBytes());
            }catch (Exception e){
                return false;
            }
            usuarioRepository.save(usuario);
            return true;
        }

        return false;
    }

    public Optional<byte[]> buscarFoto(Integer id){
        return usuarioRepository.findById(id)
                .map(UsuarioModel::getFoto);
    }
}
