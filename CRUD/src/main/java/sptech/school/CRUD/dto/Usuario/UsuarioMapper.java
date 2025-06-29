package sptech.school.CRUD.dto.Usuario;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
                entity.getAtivo()
        );
    }

    public static UsuarioAtivoDto toActiveDto(UsuarioModel entity){

        if(entity == null){
            throw new UsernameNotFoundException("Usuario não encontrado");
        }

        return new UsuarioAtivoDto(
                entity.getAtivo()
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

    public static UsuarioFullDto toListagemFullDto(UsuarioModel usuario) {

        if(usuario == null){
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return UsuarioFullDto.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .ativo(usuario.getAtivo())
                .foto(usuario.getFoto())
                .createdAt(usuario.getCreatedAt())
                .updatedAt(usuario.getUpdatedAt())
                .cargo(usuario.getCargo())
                .build();
    }


    public static List<UsuarioFullDto> toListagemFullDto(List<UsuarioModel> entities){
        if(entities == null){
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return entities.stream()
                .map(UsuarioMapper::toListagemFullDto)
                .toList();
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
        UsuarioModel model = new UsuarioModel(dto.getNome(), dto.getEmail(), dto.getPassword());
        model.setId(id);
        return model;
    }

}
