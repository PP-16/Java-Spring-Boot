package monstersevice.repository;

import jakarta.transaction.Transactional;
import monstersevice.model.Monster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonsterRepository
        extends JpaRepository<Monster, Integer> {

    @Transactional
    @Modifying
    @Query(" update Monster m set m.health = :health where m.id =:id ")
    int attackMonster(@Param("id") Integer id,
                      @Param("health") Integer health);

    public List<Monster> findAllByOrderByIdAsc();
}
