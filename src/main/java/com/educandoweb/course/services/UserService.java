package com.educandoweb.course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import com.educandoweb.course.services.exceptions.DatabaseException;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(Long id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id)); // tenta dar um get, caso não dê certo, lança a
																			// exceção personalizada
	}

	public User insert(User obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) { // exceção quando insere um id não existente
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) { // exceção quando tenta apagar um dado associado a outros dados
			throw new DatabaseException(e.getMessage());
		}
	}

	public User update(Long id, User obj) {
		User entity = repository.getOne(id); // vai instanciar obj monitorado para trabalhar com ele antes de usá-lo
												// diretamente do banco
		updateData(entity, obj);
		return repository.save(entity);
	}

	private void updateData(User entity, User obj) { // método para atualizar dados do entity com base no obj
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
	}

}
