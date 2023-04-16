package pl.javastart.jjdind49_katibar_zad25_1;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findAllByEndTimeIsNotNull();
    List<Task> findAll();
    List<Task> findAllByEndTimeIsNull();
}
