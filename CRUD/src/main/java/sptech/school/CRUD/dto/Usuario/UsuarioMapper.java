package sptech.school.CRUD.dto.Usuario;

import sptech.school.CRUD.Model.CargoModel;
import sptech.school.CRUD.Model.UsuarioModel;

import java.util.List;

public class UsuarioMapper {

    public static UsuarioListagemDto toListagemDto(UsuarioModel entity){

        if (entity == null){
            return null;
        }
        return new UsuarioListagemDto(

                entity.getNome(),
                entity.getEmail(),
                entity.getPassword()
        );
    }

    public static UsuarioTokenDto of(UsuarioModel usuario, String token){
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();

        if (usuario == null){
            return null;
        }

        usuarioTokenDto.setUserId(usuario.getId().longValue());
        usuarioTokenDto.setEmail(usuario.getEmail());
        usuarioTokenDto.setNome(usuario.getNome());
        usuarioTokenDto.setToken(token);
        usuarioTokenDto.setCargo(usuario.getCargo());

        return usuarioTokenDto;
    }

    public static UsuarioModel of(UsuarioCadastroDto usuarioParaCadastro) {
        UsuarioModel usuario = new UsuarioModel();

        usuario.setEmail(usuarioParaCadastro.getEmail());
        usuario.setNome(usuarioParaCadastro.getNome());
        usuario.setPassword(usuarioParaCadastro.getPassword());
        usuario.setCargo(usuarioParaCadastro.getCargo());
        return usuario;
    }

    public static UsuarioModel of(UsuarioLoginDto usuarioLoginDto) {
        UsuarioModel usuario = new UsuarioModel();

        usuario.setEmail(usuarioLoginDto.getEmail());
        usuario.setPassword(usuarioLoginDto.getPassword());

        return usuario;
    }

    public static List<UsuarioListagemDto> toListagemDtos(List<UsuarioModel> entities){
        if (entities == null){
            return null;
        }
        return entities.stream().map(UsuarioMapper::toListagemDto).toList();
    }

    public static UsuarioModel toEntity(UsuarioCadastroDto dto) {
        if (dto == null) {
            return null;
        }
        return new UsuarioModel(dto.getNome(), dto.getEmail(), dto.getPassword());
    }

    public static UsuarioModel toEntity(UsuarioAtualizadoDto dto, Integer id) {
        if (dto == null) {
            return null;
        }
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(id);
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());

        if (dto.getCargo() == null || dto.getCargo().getId() == null){
            CargoModel cargoPadrao = new CargoModel();
            cargoPadrao.setId(1); // Define o cargo padrão com id = 1
            usuario.setCargo(cargoPadrao);
        } else {
            usuario.setCargo(dto.getCargo());
        }

        return usuario;
    }

}
