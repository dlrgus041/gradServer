package org.grad.project.log;

import org.grad.project.employee.EmployeeRepository;
import org.grad.project.model.Log;
import org.grad.project.system.Repository;
import org.grad.project.visitor.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    private final EmployeeRepository employeeRepository;
    private final VisitorRepository visitorRepository;
    private final LogRepository logRepository;

    @Autowired
    public LogService(EmployeeRepository employeeRepository, VisitorRepository visitorRepository, LogRepository logRepository) {
        this.employeeRepository = employeeRepository;
        this.visitorRepository = visitorRepository;
        this.logRepository = logRepository;
    }

    public void join(Log log) {
        logRepository.save(log);
    }

    public List<Log> read() throws Exception {
        return logRepository.findAll();
    }

    private int first(int id) {
        return id / 100000;
    }

    public boolean checkID(int id) {
        if (first(id) > 2) return false;
        Repository repo = first(id) == 1 ? employeeRepository : visitorRepository;
        return repo.findById(id).isPresent();
    }

    public boolean checkTemp(float temp) {
        return temp >= 35 && temp <= 38;
    }

    public boolean checkInterval(long timeNow, long timeQR) {
        return (timeNow - timeQR) / 1000 / 60 <= 10;
    }

//    public Log makeLog(LogForm form) {
//        return new Log(form, checkID(form.getId()), checkTemp(form.getTemp()));
//    }
}
