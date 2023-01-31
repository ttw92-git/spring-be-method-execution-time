package ttw.springbe.method.execution.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ttw.springbe.method.execution.time.services.TableAService;

@RestController
@RequestMapping("/api")
public class Controller {
    
    @Autowired
    private TableAService tableAService;

	@GetMapping(value="/get-data-a")
	public ResponseEntity<Object> resetToDefaultAndGetDataA() {
		tableAService.getAll();
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
}
