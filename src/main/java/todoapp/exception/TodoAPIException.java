package todoapp.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoAPIException  extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private  HttpStatus status;
	private  String message;

}
