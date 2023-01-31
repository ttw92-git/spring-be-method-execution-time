package ttw.springbe.method.execution.time.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableAService {

    @Autowired
    private TableBService tableBService;

    public void getAll() {
        this.doNothingA();
        tableBService.doNothingB();
    }

    public int doNothingA() {
        doNothingA2();
        return 1;
    }

    public int doNothingA2() {
        return 1;
    }
}
