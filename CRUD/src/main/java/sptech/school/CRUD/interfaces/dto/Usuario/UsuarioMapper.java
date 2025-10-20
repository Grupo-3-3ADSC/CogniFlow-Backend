package sptech.school.CRUD.interfaces.dto.Usuario;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sptech.school.CRUD.domain.entity.CargoModel;
import sptech.school.CRUD.domain.entity.UsuarioModel;
import sptech.school.CRUD.interfaces.dto.Cargo.CargoListagemDto;

import java.util.List;

public class UsuarioMapper {

    public static UsuarioListagemDto toListagemDto(UsuarioModel entity){
        UsuarioListagemDto dto = new UsuarioListagemDto();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setAtivo(entity.getAtivo());
        if (entity.getCargo() != null) {
            CargoListagemDto cargoDto = new CargoListagemDto();
            cargoDto.setId(entity.getCargo().getId());
            cargoDto.setNome(entity.getCargo().getNome());
            dto.setCargo(cargoDto);
        }
        return dto;
    }

    public static UsuarioListagemDto toEmailDto(UsuarioModel entity){
         UsuarioListagemDto dto = new UsuarioListagemDto();
         dto.setEmail(entity.getEmail());

         return dto;
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
    public static UsuarioDeleteDto toDeleteDto(UsuarioModel model) {
        UsuarioDeleteDto dto = new UsuarioDeleteDto();
        dto.setId(model.getId());
        dto.setNome(model.getNome());
        dto.setEmail(model.getEmail());
        return dto;
    }
}
