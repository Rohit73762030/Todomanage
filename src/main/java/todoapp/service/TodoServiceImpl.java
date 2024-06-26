package todoapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import todoapp.dto.TodoDto;
import todoapp.exception.ResourceNotFoundException;
import todoapp.model.Todo;
import todoapp.repository.TodoRepository;

@Service
public class TodoServiceImpl implements TodoService {
    
	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public TodoDto addTodo(TodoDto todoDto) {
		//convert todoDto entity

		Todo  todo = modelMapper.map(todoDto, Todo.class);
		//Todo jpa 
		 Todo  saveTodo = todoRepository.save(todo);
		 
		 TodoDto  saveTodoDto = modelMapper.map(saveTodo, TodoDto.class);
		 
		return saveTodoDto;
	}

	@Override
	public TodoDto getTodo(Long id) {
		
		Todo todo = todoRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Todo not found with id "+id));	
		return modelMapper.map(todo, TodoDto.class);
	}

	@Override
	public List<TodoDto> getAllTodos() {
	 List<Todo> todos =todoRepository.findAll();
		return todos.stream().map((todo)->modelMapper.map(todo, TodoDto.class))
				.collect((Collectors.toList()));
	}

	@Override
	public TodoDto updateTodo(TodoDto todoDto, Long id) {
	  Todo todo =  todoRepository.findById(id)
	    .orElseThrow(()-> new ResourceNotFoundException("Todo not found with id : "+id));
	  todo.setTitle(todoDto.getTitle());
	  todo.setDescription(todoDto.getDescription());
	  todo.setCompleted(todoDto.isCompleted());
	  
	  Todo updatedTodo = todoRepository.save(todo);
		return modelMapper.map(updatedTodo,TodoDto.class);
	}

	@Override
	public void delete(Long id) {
	  todoRepository.findById(id)
	  .orElseThrow(()->new ResourceNotFoundException("Todo not found with id :"+id));
	    todoRepository.deleteById(id);
	}

	@Override
	public TodoDto completeTodo(Long id) {
	  Todo  todo = todoRepository.findById(id).
	      orElseThrow(()-> new ResourceNotFoundException("Todo not found with id : "+id));
		todo.setCompleted(Boolean.TRUE);
	  Todo  updateTodo =  todoRepository.save(todo);
	  return modelMapper.map(updateTodo, TodoDto.class);
	}

	@Override
	public TodoDto incompleteTodo(Long id) {
	 Todo  todo	=  todoRepository.findById(id)
		   .orElseThrow(()->new ResourceNotFoundException("Todo not found with id : "+id));
	  todo.setCompleted(Boolean.FALSE);
	Todo updatedTodo =  todoRepository.save(todo);
	 return modelMapper.map(updatedTodo, TodoDto.class);
	}

}
