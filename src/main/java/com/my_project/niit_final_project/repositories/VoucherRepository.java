package com.my_project.niit_final_project.repositories;

import com.my_project.niit_final_project.entities.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<Voucher,Long> {
}
