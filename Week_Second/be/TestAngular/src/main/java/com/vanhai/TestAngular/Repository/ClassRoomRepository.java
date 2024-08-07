package com.vanhai.TestAngular.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vanhai.TestAngular.Entity.ClassRoom;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, UUID>{

	boolean existsByEmail(String email);
}
