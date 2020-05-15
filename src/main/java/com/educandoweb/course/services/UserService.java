package com.educandoweb.course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public List<User> findAll(){
		return repository.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> obj = repository.findById(id);
		return obj.get();
	}
	
	public User insert(User obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		repository.deleteById(id); 
	}
	
	public User update(Long id, User obj) {
		User entity = repository.getOne(id);  //vai instanciar obj monitorado para trabalhar com ele antes de usá-lo diretamente do banco
		updateData(entity, obj);
		return repository.save(entity);
	}

	private void updateData(User entity, User obj) { // método para atualizar dados do entity com base no obj
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
	}
	
	

}
