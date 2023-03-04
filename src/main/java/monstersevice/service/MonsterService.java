package monstersevice.service;

import monstersevice.handleExceptionError.HandleExceptionError;
import monstersevice.model.Monster;
import monstersevice.repository.MonsterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonsterService {
    @Autowired
    private MonsterRepository monsterRepository;

    public Monster postCreateMonsterService(Monster monster) {
        //Insert into monster (name,health) values("Test":100)
        return monsterRepository.save(monster);
    }

    public List<Monster> getAllMonsterService() {
        return monsterRepository.findAllByOrderByIdAsc();
    }

    public Optional<Monster> getInformation(Integer id) {
        return monsterRepository.findById(id);
    }

    public Monster updateMonsterByIdService(Monster monster) throws HandleExceptionError {
        try {
            Optional<Monster> data = getInformation(monster.getId());
            //data != null
            if (data.isPresent()) {
                return monsterRepository.save(monster);
            } else throw new HandleExceptionError("Data not found");
        } catch (HandleExceptionError ex) {
            throw new HandleExceptionError("Can't connect database");
        }
    }

    public boolean deleteMonsterService(Integer id) throws HandleExceptionError {
        try {
            Optional<Monster> data = getInformation(id);
            //data != null
            if (data.isPresent()) {
                monsterRepository.deleteById(id);
                return true;
            } else throw new HandleExceptionError("Data not found");
        } catch (HandleExceptionError ex) {
            throw new HandleExceptionError("Can't connect database");
        }
    }

    public String attackMonsterService(Integer id, Integer damage) throws HandleExceptionError {
        try {
            Optional<Monster> data = getInformation(id);
            if (data.isPresent()) {
                int healthNow = data.get().getHealth() - damage;
                if (healthNow < 0) healthNow = 0;
                int response = monsterRepository.attackMonster(id, healthNow);
                if (response != 0) {
                    return "Update success";
                } else return "Can't update";
            } else throw new HandleExceptionError("Data not found");
        } catch (HandleExceptionError ex) {
            throw new HandleExceptionError(ex.getMessage());
        }
    }
}
