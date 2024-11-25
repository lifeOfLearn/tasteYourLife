package iot.tyl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iot.tyl.model.Hot;

@Repository
public interface HotJpa extends JpaRepository<Hot, Integer>{

}
