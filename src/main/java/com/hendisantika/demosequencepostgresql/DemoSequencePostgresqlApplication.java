package com.hendisantika.demosequencepostgresql;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

interface CustomerRepository extends JpaRepository<Customer, Long> {
}

@SpringBootApplication
public class DemoSequencePostgresqlApplication {

	public static void main(String[] args) {

		CustomerRepository customerRepository = SpringApplication.run(DemoSequencePostgresqlApplication.class, args)
				.getBean(CustomerRepository.class);

		Customer customer = new Customer();
		customer.setName("customer" + System.currentTimeMillis());
		customerRepository.save(customer);
	}
}

@Entity
@Table(name = "customer")
@Data
class Customer {

	@Id
	@GeneratedValue(generator = "pooled")
	@GenericGenerator(name = "pooled", strategy = "enhanced-table", parameters = {
			@Parameter(name = "value_column_name", value = "sequence_next_hi_value"),
			@Parameter(name = "prefer_entity_table_as_segment_value", value = "true"),
			@Parameter(name = "optimizer", value = "pooled-lo"), @Parameter(name = "increment_size", value = "100")})
	Long id;

	String name;
}
