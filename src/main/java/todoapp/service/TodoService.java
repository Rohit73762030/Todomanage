package todoapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import todoapp.dto.TodoDto;

@Service
public interface TodoService {
	
	TodoDto addTodo(TodoDto todoDto);
	
	TodoDto getTodo(Long id);
	
	List<TodoDto> getAllTodos();
	
	TodoDto updateTodo(TodoDto todoDto, Long id);
	
	void delete(Long id);
	
	TodoDto completeTodo(Long id);
	TodoDto incompleteTodo(Long id);
	
	

}
