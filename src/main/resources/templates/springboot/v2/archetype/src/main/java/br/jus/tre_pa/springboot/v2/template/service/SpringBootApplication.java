package br.jus.tre_pa.springboot.v2.template.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import br.jus.tre_pa.springboot.v2.template.service.groovy.qy.sql.QySqlContext;

@SpringBootApplication
public class SpringbootV2TemplateApplication implements CommandLineRunner {
	
	@Autowired
	private QySqlContext qyContext;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootV2TemplateApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
//		Pageable pageable = PageRequest.of(1, 2, Sort.by("numero").ascending());
//		
//		Page<List<Map<String, Object>>> result = qyContext.selectFrom("select * from cr_unidade")
//			.orderBy(pageable.getSort())
//			.limit(pageable)
//			.fetchMaps();
//		
//		
//		System.out.println(result.getTotalElements());
//		System.out.println(result.getTotalPages());
//		System.out.println(result.getContent());
	}
}
