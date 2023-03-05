package monstersevice.service;

import monstersevice.handleExceptionError.HandleExceptionError;
import monstersevice.model.Monster;
import monstersevice.repository.MonsterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MonsterServiceTest {

    @InjectMocks
    private MonsterService monsterService;
    @Mock
    private MonsterRepository monsterRepository;

    private Monster mockMonster() {
        Monster mockMonster = new Monster();
        mockMonster.setId(1);
        mockMonster.setName("darke");
        mockMonster.setHealth(400);
        return mockMonster;
    }

    @Test
    void postCreateMonsterTest() {
        doReturn(mockMonster()).when(monsterRepository).save(any(Monster.class));
        Monster response = monsterService.postCreateMonsterService(new Monster());
        assertEquals(mockMonster().getId(), response.getId());
    }

    @Test
    void getAllMonterServiceTest() {
        List<Monster> monsterList = new ArrayList<>();
        monsterList.add(mockMonster());

        doReturn(monsterList).when(monsterRepository).findAllByOrderByIdAsc();
        List<Monster> respons = monsterService.getAllMonsterService();
        assertEquals(monsterList, respons);
        assertEquals(monsterList.get(0).getId(), respons.get(0).getId());
    }

    @Test
    void upDateMonsterServiceTestSuccess() throws HandleExceptionError {
        doReturn(Optional.of(mockMonster())).when(monsterRepository).findById(any(Integer.class));
        doReturn(mockMonster()).when(monsterRepository).save(any(Monster.class));

        Monster requesMonster = new Monster();
        requesMonster.setId(1);

        Monster response = monsterService.updateMonsterByIdService(requesMonster);
        assertEquals(mockMonster().getId(), response.getId());

    }

    @Test
    void updateMonsterServiceFailDataNotFound() throws HandleExceptionError {
        HandleExceptionError handleExceptionError
                = assertThrows(HandleExceptionError.class, () -> monsterService.updateMonsterByIdService(new Monster()));
        assertEquals("Data not found", handleExceptionError.getMessage());
    }

    @Test
    void updateMonsterServiceFailCanNotConnectDatabase() {

        doThrow(new RuntimeException()).when(monsterRepository).findAllById(any());

        HandleExceptionError handleExceptionError
                = assertThrows(HandleExceptionError.class, () -> monsterService.updateMonsterByIdService(new Monster()));

        assertEquals("Can't connect database", handleExceptionError.getMessage());

    }
}
