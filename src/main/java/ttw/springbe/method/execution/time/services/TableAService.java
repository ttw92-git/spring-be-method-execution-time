package ttw.springbe.method.execution.time.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableAService {

    @Autowired
    private TableBService tableBService;

    @Autowired
    private TableCService tableCService;

    public void getAll() {
        this.doNothingA();
        tableBService.doNothingB();
        tableCService.doNothingC();
    }

    public int doNothingA() {
        doNothingA2();
        return 1;
    }

    public int doNothingA2() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        return 1;
    }
}
