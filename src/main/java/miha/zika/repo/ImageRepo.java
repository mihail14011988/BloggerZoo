/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package miha.zika.repo;

import java.util.Optional;
import miha.zika.entity.ImageComm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepo extends JpaRepository<ImageComm, Long>{
    Optional<ImageComm> findByUserId(Long id);
    Optional<ImageComm> findByPostId(Long id);
}
