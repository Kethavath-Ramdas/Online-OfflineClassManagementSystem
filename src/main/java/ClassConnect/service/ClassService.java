package ClassConnect.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ClassConnect.entity.ClassSession;
import ClassConnect.exception.ClassCreationException;
import ClassConnect.repository.ClassSessionRepository;

@Service
public class ClassService {

    @Autowired
    private ClassSessionRepository classSessionRepository;

    public ClassSession createClass(ClassSession session) {

        //  BASIC VALIDATION
        if (session.getClassType() == null || session.getClassType().isEmpty()) {
            throw new ClassCreationException("Class type is required");
        }

        if (session.getClassDate() == null) {
            throw new ClassCreationException("Class date is required");
        }

        if (session.getTime() == null || session.getTime().isEmpty()) {
            throw new ClassCreationException("Class time is required");
        }

        return classSessionRepository.save(session);
    }
}
