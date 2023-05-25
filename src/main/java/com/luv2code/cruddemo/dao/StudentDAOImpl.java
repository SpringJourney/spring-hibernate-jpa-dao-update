package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentDAOImpl implements StudentDAO{

    // define field for entity manager
    private final EntityManager entityManager;

    // inject entity manager using constructor injection
    @Autowired
    public StudentDAOImpl(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // implement save method
    @Override
    @Transactional
    public void save(Student theStudent) {
        entityManager.persist(theStudent);
    }

    /*
    * Generally, @Transactional is used with methods that modify the state of the database, like insert, update, and delete operations. This is because these operations need to maintain the atomicity property of transactions, i.e., either all changes are committed to the database, or none are.

In contrast, read operations (like findById() in your case) are usually idempotent and don't necessarily need to be transactional, because they do not change the state of the database. They can be a part of a larger transaction though, so sometimes it makes sense to add @Transactional for isolation purposes.

That said, the code you posted will work correctly. By marking the findById() method as @Transactional, you ensure that the method has a consistent view of the database.
    *
    *
    * */
    @Override
    @Transactional
    public Optional<Student> findById(Integer id) {
        // Entity class + Primary key
        Student student = entityManager.find(Student.class, id);
        return Optional.ofNullable(student);
    }

    @Override
    public List<Student> findAll() {
        // createQuery (name of JPA Entity, name of the Entity) for all students in ascending order for last name
        TypedQuery<Student> theQuery = entityManager.createQuery("FROM Student ORDER BY lastName", Student.class);
        return theQuery.getResultList();
    }

    @Override
    public List<Student> findByLastName(String theLastName) {
        // create query
        TypedQuery<Student> theQuery = entityManager.createQuery(
                // JPQL Named Parameters are prefixed with a colon
                "FROM Student WHERE lastName=:theData", Student.class);
        // set query parameters
        theQuery.setParameter("theData", theLastName);
        // return query results
        return theQuery.getResultList();
    }

    @Override
    @Transactional
    public void update(Student theStudent) {
        entityManager.merge(theStudent);
    }
}




