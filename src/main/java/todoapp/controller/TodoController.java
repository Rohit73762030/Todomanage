package todoapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import todoapp.dto.TodoDto;
import todoapp.service.TodoService;

@RestController
@RequestMapping("api/todos")
public class TodoController {
	
	  @Autowired
	  private TodoService todoService;
	  
	  // Build Add Todo REST API
	  @PreAuthorize("hasRole('ADMIN')")
	  @PostMapping
	  public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto){
		  TodoDto   savedDto =todoService.addTodo(todoDto);
		return new ResponseEntity<>(savedDto,HttpStatus.CREATED);
		  
	  }
	  
	  // Build Get Todo REST API
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	  @GetMapping("/{id}")
	  public ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long todoId){
		TodoDto  todoDto =  todoService.getTodo(todoId);
		  return new ResponseEntity<>(todoDto ,HttpStatus.OK) ;
		  
	  }
	  // Build GetAll REST API
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	  @GetMapping
	  public ResponseEntity<List<TodoDto>> getAllTodos(){
		List<TodoDto> todos = todoService.getAllTodos();
	  //	return new ResponseEntity<>(todos ,HttpStatus.OK);
		return ResponseEntity.ok(todos);
		  
	  }
	  // Build update REST API
	  @PreAuthorize("hasRole('ADMIN')")
	  @PutMapping("{id}")
	 public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto , @PathVariable("id") Long todoId){
		   TodoDto updateTodo = todoService.updateTodo(todoDto, todoId);
		 return ResponseEntity.ok(updateTodo); 
	 }
	 // Build delete Rest API
	  @PreAuthorize("hasRole('ADMIN')")
	  @DeleteMapping("{id}")
	  public ResponseEntity<String> deleteTodo(@PathVariable("id") Long todoId){
		      todoService.delete(todoId);
		  return ResponseEntity.ok("Todo delete successfully..");
		  
	  }
	  // Build Compelete Todo Rest Api
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	  @PatchMapping("{id}/complete")
	  public ResponseEntity<TodoDto> completedTodo(@PathVariable("id") Long todoId){
		TodoDto  upadetedTodo =todoService.completeTodo(todoId);
		  return ResponseEntity.ok(upadetedTodo); 
	  }
	  // Build incompleteTodo
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	  @PatchMapping("{id}/incomplete")
	  public ResponseEntity<TodoDto>incompletedTodo(@PathVariable("id") Long todoId){
		  TodoDto updatedTodo =  todoService.incompleteTodo(todoId);
		  return ResponseEntity.ok(updatedTodo);
		  
		  
	  }
	  
	  

}
