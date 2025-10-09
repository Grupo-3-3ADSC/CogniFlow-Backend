package sptech.school.CRUD.infrastructure.persistence;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sptech.school.CRUD.domain.entity.UsuarioModel;
import sptech.school.CRUD.domain.repository.UsuarioRepository;
import sptech.school.CRUD.infrastructure.adapter.persistence.DataUsuarioRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@AllArgsConstructor
public class UsuarioJpaRepository implements UsuarioRepository {

    private final DataUsuarioRepository springRepo;

    @Override
    public List<UsuarioModel> findByAtivoTrue(){
        return springRepo.findByAtivoTrue();
    }

    @Override
    public List<UsuarioModel> findByInativoTrue(){
        return springRepo.findByInativoTrue();
    }

    @Override
    public List<UsuarioModel> findAll(){
        return springRepo.findAll();
    }

    @Override
    public Optional<UsuarioModel> findById(Integer id){
        return springRepo.findById(id);
    }

    @Override
    public void delete(UsuarioModel usuario){
        springRepo.delete(usuario);
    }

    @Override
    public boolean existsByEmail(String email){
      return springRepo.existsByEmail(email);
    }

    @Override
    public Optional<UsuarioModel> findByEmail(String email){
        return springRepo.findByEmail(email);
    }
}

    