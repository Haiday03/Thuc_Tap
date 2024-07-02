package com.vanhai.TestAngular.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.vanhai.TestAngular.DTO.SearchRequest;
import com.vanhai.TestAngular.Entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class StudentRepositoryEntityManager {

    @Autowired
    private EntityManager entityManager;
    
    public Page<Student> findByCriteria(SearchRequest searchRequest, Pageable pageable){
        
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> map = new HashMap<>();
        jpql.append("SELECT s FROM Student s WHERE 1 = 1 ");
        
        if (searchRequest.getId() != null) {
            jpql.append("AND s.id = :id ");
            map.put("id", searchRequest.getId());
        }
        
        if (searchRequest.getName() != null) {
            jpql.append("AND s.name LIKE :name ");
            map.put("name", "%" + searchRequest.getName() + "%");
        }
        
        if (searchRequest.getClassName() != null) {
            jpql.append("AND s.className LIKE :className ");
            map.put("className", "%" + searchRequest.getClassName() + "%");
        }
        
        if (searchRequest.getEmail() != null) {
            jpql.append("AND s.email LIKE :email ");
            map.put("email", "%" + searchRequest.getEmail() + "%");
        }
        
        if (searchRequest.getAge() != 0) {
            jpql.append("AND s.age = :age ");
            map.put("age", searchRequest.getAge());
        }
        
        if (searchRequest.getAddress() != null) {
            jpql.append("AND s.address LIKE :address ");
            map.put("address", "%" + searchRequest.getAddress() + "%");
        }
        
        if (searchRequest.getDateStart() != null) {
            jpql.append("AND s.dateJoin BETWEEN :dateStart AND :dateEnd  ");
            map.put("dateStart", searchRequest.getDateStart());
            map.put("dateEnd", searchRequest.getDateEnd() != null ? searchRequest.getDateEnd() : Date.valueOf(LocalDate.now()));
        }
        
        jpql.append("ORDER BY s.dateJoin DESC");
        TypedQuery<Student> query = entityManager.createQuery(jpql.toString(), Student.class);
        
        map.forEach(query::setParameter);
        
        // Get the total number of results before pagination
        int totalElements = query.getResultList().size();
        
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        
        List<Student> li = query.getResultList();
        
        return new PageImpl<>(li, pageable, totalElements);
    }
}
