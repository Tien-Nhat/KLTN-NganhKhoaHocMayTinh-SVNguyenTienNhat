package khoaluan.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import khoaluan.BE.model.Category;

@Repository
public interface CategoryReponsitory extends JpaRepository<Category, Long> {
}
